package com.gesoftware.figures.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.enums.RubyColor;
import com.gesoftware.figures.managers.TexturesManager;

public final class Ruby extends Actor {
    private final RubyColor m_Color;
    private final Vec2i m_Position;

    public Ruby(final RubyColor color, final Vec2i position) {
        m_Position = position;
        m_Color    = color;

        setSize(Definitions.c_SizeCell, Definitions.c_SizeCell);
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(Definitions.c_SizeBorder + position.getX() * (Definitions.c_SizeCell + Definitions.c_SizeBorder),
                    Definitions.c_SizeBorder + position.getY() * (Definitions.c_SizeCell + Definitions.c_SizeBorder));
    }

    public final Action getInjectionAnimation() {
        setOrigin(getWidth() / 2, getHeight() / 2);
        setScale(0.6f, 0.6f);

        final Action injectAction = Actions.scaleTo(1, 1, Definitions.c_DurationInject);
        injectAction.setTarget(this);
        return injectAction;
    }

    public final Action getMovingAnimation(final Vec2i position) {
        final Action action = Actions.moveTo(Definitions.c_SizeBorder + position.getX() * (Definitions.c_SizeCell + Definitions.c_SizeBorder),
                                             Definitions.c_SizeBorder + position.getY() * (Definitions.c_SizeCell + Definitions.c_SizeBorder),
                                             Definitions.c_DurationMove);
        action.setTarget(this);
        return action;
    }

    public final Action getFadingAnimation(final Runnable onComplete) {
        final Action growAnimation = Actions.scaleTo(1.2f, 1.2f, Definitions.c_DurationFade * 0.5f);
        growAnimation.setTarget(this);
        final Action fadeAnimation = Actions.scaleTo(0.1f, 0.1f, Definitions.c_DurationFade);
        fadeAnimation.setTarget(this);
        final Action action = Actions.sequence(growAnimation, fadeAnimation);
        action.setTarget(this);

        final Action completeAction = Actions.run(onComplete);
        completeAction.setTarget(this);

        return Actions.sequence(action, completeAction);
    }

    @Override
    public final void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);

        final Texture texture = TexturesManager.getTexture(m_Color.getTextureName());
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public final Vec2i getPosition() {
        return m_Position;
    }

    public final void setPosition(final Vec2i position) {
        m_Position.set(position);
    }

    @Override
    public final boolean equals(final Object o) {
        return o instanceof Ruby && m_Color == ((Ruby) o).m_Color;
    }

    @Override
    public final String toString() {
        return m_Color + " " + m_Position;
    }
}
