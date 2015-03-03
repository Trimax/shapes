package com.gesoftware.figures.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gesoftware.figures.enums.GameState;
import com.gesoftware.figures.managers.DataManager;
import com.gesoftware.figures.managers.ScoreManager;
import com.gesoftware.figures.managers.StatesManager;
import com.gesoftware.figures.managers.TexturesManager;
import com.gesoftware.figures.screens.MenuScreen;
import com.gesoftware.figures.screens.RubyScreen;
import com.gesoftware.figures.ui.ClickableButton;
import com.gesoftware.figures.utils.Utils;

public final class PauseStage extends CommonDialogStage {
    private static final int TABLE_CELL_PADDING = 5;
    private final ClickableButton m_PlayButton;
    private final ClickableButton m_HomeButton;
    private final ClickableButton m_RestartButton;
    private Table m_Table;

    public PauseStage() {
        super();

        final int buttonWidth  = (int) (Gdx.graphics.getWidth() * .5f);
        final int buttonHeight = (int) (Gdx.graphics.getWidth() * .2f);

        m_PlayButton = new ClickableButton(buttonWidth, buttonHeight,
                Utils.getRGBColor(152, 220, 85),
                Utils.getRGBColor(126, 181, 72),
                TexturesManager.getTexture("play.png"), 0.3f);
        m_HomeButton = new ClickableButton(buttonWidth / 2 - TABLE_CELL_PADDING, buttonHeight,
                Utils.getRGBColor(237, 149, 74),
                Utils.getRGBColor(218, 127, 29),
                TexturesManager.getTexture("home.png"));
        m_RestartButton = new ClickableButton(buttonWidth / 2 - TABLE_CELL_PADDING, buttonHeight,
                Utils.getRGBColor(126, 142, 213),
                Utils.getRGBColor(104, 117, 176),
                TexturesManager.getTexture("refresh.png"));

        m_PlayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StatesManager.setGameState(GameState.Play);
                DataManager.getRubyScreen().getRubyStage().show(false);
            }
        });


        m_HomeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScoreManager.resetScore();
                DataManager.m_RubyGame.setScreen(new MenuScreen());
            }
        });

        m_RestartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScoreManager.resetScore();
                DataManager.m_RubyGame.setScreen(new RubyScreen());
            }
        });

        createTable();
        addActor(m_Table);
    }

    private void createTable() {
        m_Table = new Table();
        m_Table.setFillParent(true);
        m_Table.add(m_PlayButton).colspan(2).pad(TABLE_CELL_PADDING);
        m_Table.row();
        m_Table.add(m_HomeButton).pad(TABLE_CELL_PADDING);
        m_Table.add(m_RestartButton).pad(TABLE_CELL_PADDING);
    }

}
