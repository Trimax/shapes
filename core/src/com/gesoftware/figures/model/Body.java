package com.gesoftware.figures.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.enums.Direction;
import com.gesoftware.figures.enums.GameState;
import com.gesoftware.figures.enums.RubyColor;
import com.gesoftware.figures.managers.*;
import com.gesoftware.figures.stages.RubyStage;

public final class Body extends Group {
    private final Field m_Field;

    private final ParallelAction m_Shifts;
    private final ParallelAction m_Reduces;
    private final ParallelAction m_Injections;
    private final ParallelAction m_ReducesAfterInjections;
    private final ParallelAction m_AdditionalInjections;

    private boolean m_IsBusy;

    private Direction m_DeferredAction;

    public Body() {
        m_Field = new Field();

        m_ReducesAfterInjections = new ParallelAction();
        m_AdditionalInjections   = new ParallelAction();
        m_Injections             = new ParallelAction();
        m_Reduces                = new ParallelAction();
        m_Shifts                 = new ParallelAction();

        m_IsBusy = false;

        createField();
        inject(RubyColor.getRandom());
        inject(RubyColor.getRandom());

        setSize(Definitions.c_FieldSize, Definitions.c_FieldSize);
    }

    private Ruby inject(final RubyColor rubyColor) {
        if (!m_Field.hasFreeCells())
            return null;

        final Vec2i cell = m_Field.getFreeCell();
        m_Field.set(cell, new Ruby(rubyColor, cell));
        addActor(m_Field.get(cell));

        StatesManager.colorNext = RubyColor.getNext();
        updateFooter();

        return m_Field.get(cell);
    }

    private void updateFooter() {
        final RubyStage stage = (RubyStage) getStage();
        if ((stage == null)||(stage.getFooter() == null))
            return;

        stage.getFooter().update();
    }

    private void createField() {
        for (int x = 0; x < Definitions.c_NumberCells; x++)
            for (int y = 0; y < Definitions.c_NumberCells; y++)
                addActor(new Cell(Definitions.c_SizeBorder + x * (Definitions.c_SizeCell + Definitions.c_SizeBorder), Definitions.c_SizeBorder + y * (Definitions.c_SizeCell + Definitions.c_SizeBorder)));
    }

    public final void shift(final Direction direction) {
        if (direction == null)
            return;

        m_DeferredAction = direction;
    }

    private boolean shift(final Vec2i direction) {
        boolean hasChanged = false;
        for (int line = 0; line < Definitions.c_NumberCells; line++)
            hasChanged |= shiftLine(line, direction);

        return hasChanged;
    }

    private boolean shiftLine(final int line, final Vec2i direction) {
        final int dx = Math.abs(direction.getX());
        final int dy = Math.abs(direction.getY());

        final int size = Definitions.c_NumberCells - 1;

        boolean hasChanged = false;
        int item = dx * ((direction.getX() * size + size) / 2) + dy * ((direction.getY() * size + size) / 2);
        for (int cell = 0; cell < Definitions.c_NumberCells; cell++) {
            hasChanged |= shiftCell(new Vec2i((1 - dx) * line + dx * item, (1 - dy) * line + dy * item), direction);
            item -= dx * direction.getX() + dy * direction.getY();
        }

        return hasChanged;
    }

    private boolean shiftCell(final Vec2i position, final Vec2i direction) {
        if (m_Field.isFree(position))
            return false;

        final Vec2i newPosition = getNewPosition(position, direction);
        if (newPosition.equals(position))
            return false;

        moveRuby(m_Field.extract(position), newPosition);
        return true;
    }

    private Vec2i getNewPosition(final Vec2i position, final Vec2i direction) {
        Vec2i currentPosition = position;
        while (m_Field.isAllowed(Vec2i.add(currentPosition, direction)))
            currentPosition = Vec2i.add(currentPosition, direction);

        return currentPosition;
    }

    private void moveRuby(final Ruby ruby, final Vec2i newPosition) {
        ruby.setPosition(newPosition);
        m_Field.set(newPosition, ruby);
        m_Shifts.addAction(ruby.getMovingAnimation(newPosition));
    }

    private boolean isBusy() {
        return m_IsBusy;
    }

    private void animate(final Direction direction) {
        m_IsBusy = true;
        m_DeferredAction = null;

        addAction(Actions.sequence(
                        /* Step 1. Perform shifts, change field state and defer shift animations */
                Actions.run(new Runnable() {
                    @Override
                    public final void run() {
                        StatesManager.hasFieldChanged = shift(direction.getVector());
                    }
                }),

                        /* Step 2. Play shift animations */
                m_Shifts,

                        /* Step 3. If field has been shifted during step 1, then perform reduce */
                Actions.run(new Runnable() {
                    @Override
                    public final void run() {
                        if (!StatesManager.hasFieldChanged)
                            return;

                        ReduceManager.reduce(Body.this, m_Reduces);
                    }
                }),

                        /* Step 4. Play reduce animations */
                m_Reduces,

                        /* Step 5. If field has been shifted during step 1, then perform inject */
                Actions.run(new Runnable() {
                    @Override
                    public final void run() {
                        if (!StatesManager.hasFieldChanged)
                            return;

                        final Ruby ruby = inject(StatesManager.colorNext);
                        if (ruby != null)
                            m_Injections.addAction(ruby.getInjectionAnimation());
                    }
                }),

                        /* Step 6. Play injection animations */
                m_Injections,

                        /* Step 7. Perform reduce after injection */
                Actions.run(new Runnable() {
                    @Override
                    public final void run() {
                        ReduceManager.reduce(Body.this, m_ReducesAfterInjections);
                    }
                }),

                        /* Step 8. Play reduce after injection animations */
                m_ReducesAfterInjections,

                        /* Step 9. Perform additional injection if field is empty */
                Actions.run(new Runnable() {
                    @Override
                    public final void run() {
                        AchievementsManager.tryUnlock(m_Field.isEmpty());

                        if (!m_Field.isEmpty())
                            return;

                        final Ruby ruby = inject(StatesManager.colorNext);
                        if (ruby != null)
                            m_AdditionalInjections.addAction(ruby.getInjectionAnimation());
                    }
                }),

                        /* Step 10. Play additional injection animations */
                m_AdditionalInjections,

                        /* Step 11. Check for game over */
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        if (getField().isGameOver()) {
                            StatesManager.setGameState(GameState.GameOver);
                            DataManager.getRubyScreen().getGameOverStage().show();
                            AchievementsManager.tryUnlock();
                        }
                    }
                }),

                        /* Step 12. Cleanup */
                Actions.run(new Runnable() {
                    @Override
                    public final void run() {
                        StatesManager.hasFieldChanged = false;
                        m_ReducesAfterInjections.reset();
                        m_AdditionalInjections.reset();
                        m_Injections.reset();
                        m_Reduces.reset();
                        m_Shifts.reset();
                        m_IsBusy = false;
                    }
                })));
    }

    public final Field getField() {
        return m_Field;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (m_DeferredAction == null || isBusy())
            return;

        animate(m_DeferredAction);
    }
}
