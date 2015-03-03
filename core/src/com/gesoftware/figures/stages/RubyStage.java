package com.gesoftware.figures.stages;

import com.badlogic.gdx.*;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.enums.Direction;
import com.gesoftware.figures.enums.GameState;
import com.gesoftware.figures.managers.DataManager;
import com.gesoftware.figures.managers.ScoreManager;
import com.gesoftware.figures.managers.StatesManager;
import com.gesoftware.figures.managers.TexturesManager;
import com.gesoftware.figures.model.Body;
import com.gesoftware.figures.model.Footer;
import com.gesoftware.figures.model.Header;
import com.gesoftware.figures.processors.GestureInputProcessor;
import com.gesoftware.figures.ui.Button;
import com.gesoftware.figures.utils.Utils;

public final class RubyStage extends Stage {
    private Table m_Table;
    private final Button m_Pause;
    private final Header m_Header;
    private final Footer m_Footer;
    private final Body m_Body;

    public RubyStage() {
        super(new ScreenViewport());

        m_Pause  = createPauseButton();
        m_Header = new Header();
        m_Body   = new Body();
        m_Footer = new Footer();
        ScoreManager.setScorePanel(m_Header);

        createTable();
        addActor(m_Table);

        show();
    }

    public void show() {
        show(true);
    }

    public void show(boolean hasAnimation) {
        initInputProcessor();
        if (hasAnimation)
            fadeInAnimation();
    }


    private Button createPauseButton() {
        final int buttonSize = (int) (Definitions.c_SizeCell * .4f);
        final Button pauseButton = new Button(Definitions.c_FieldSize - buttonSize - Definitions.c_SizeBorder,
                                              (int) Math.floor((Definitions.c_SizeCell - buttonSize) / 2),
                                              buttonSize,
                                              buttonSize,
                                              TexturesManager.getTexture("pause.png"));

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StatesManager.setGameState(GameState.Pause);
                DataManager.getRubyScreen().getPauseStage().show();
            }
        });

        return pauseButton;
    }

    private void fadeInAnimation() {
        final Action sequence = Actions.parallel(Utils.moveFrom(m_Header, m_Header.getX(), 2 * Definitions.c_HeaderSize, Definitions.c_DurationScoreFade),
                                                 Utils.moveFrom(m_Body, -m_Body.getWidth(), m_Body.getY(), Definitions.c_DurationFieldFade));

        addAction(sequence);

    }

    private void initInputProcessor() {
        final InputProcessor m_KeysInputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (StatesManager.getGameState() != GameState.Play)
                    return false;

                if (keycode == Input.Keys.BACK) {
                    StatesManager.setGameState(GameState.Pause);
                    DataManager.getRubyScreen().getPauseStage().show();
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return RubyStage.this.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return RubyStage.this.touchUp(screenX, screenY, pointer, button);
            }
        };

        InputProcessor m_InputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (StatesManager.getGameState() != GameState.Play)
                    return false;

                if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
                    StatesManager.setGameState(GameState.Pause);
                    DataManager.getRubyScreen().getPauseStage().show();
                }

                m_Body.shift(Direction.getByKeyCode(keycode));
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return RubyStage.this.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return RubyStage.this.touchUp(screenX, screenY, pointer, button);
            }
        };

        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {
            m_InputProcessor = new GestureDetector(new GestureInputProcessor(m_InputProcessor));
        }

        final InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(m_InputProcessor);
        multiplexer.addProcessor(m_KeysInputProcessor);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);
    }

    private void createTable() {
        m_Table = new Table();
        m_Table.setFillParent(true);
        m_Table.add(m_Pause).align(Align.right).padTop(m_Pause.getHeight() / 2);
        m_Table.row();
        m_Table.add(Utils.wrapToGroup(m_Header)).expandY();
        m_Table.row();
        m_Table.add(Utils.wrapToGroup(m_Body));
        m_Table.row();
        m_Table.add(m_Footer);
    }

    public final Footer getFooter() {
        return m_Footer;
    }
}
