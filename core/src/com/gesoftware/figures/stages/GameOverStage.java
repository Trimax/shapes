package com.gesoftware.figures.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.gesoftware.figures.managers.*;
import com.gesoftware.figures.screens.MenuScreen;
import com.gesoftware.figures.screens.RubyScreen;
import com.gesoftware.figures.ui.ClickableButton;
import com.gesoftware.figures.utils.Utils;

public final class GameOverStage extends CommonDialogStage {
    private static final int TABLE_CELL_PADDING = 5;
    private final ClickableButton m_HomeButton;
    private final ClickableButton m_RestartButton;
    private Table m_Table;
    private TextButton m_GameOverLabel;

    public GameOverStage() {
        super();

        final int buttonWidth  = (int) (Gdx.graphics.getWidth() * .5f);
        final int buttonHeight = (int) (Gdx.graphics.getWidth() * .2f);

        m_GameOverLabel = createGameOverLabel(buttonWidth, buttonHeight);

        m_HomeButton = new ClickableButton(buttonWidth / 2 - TABLE_CELL_PADDING, buttonHeight,
                Utils.getRGBColor(237, 149, 74),
                Utils.getRGBColor(218, 127, 29),
                TexturesManager.getTexture("home.png"));
        m_RestartButton = new ClickableButton(buttonWidth / 2 - TABLE_CELL_PADDING, buttonHeight,
                Utils.getRGBColor(126, 142, 213),
                Utils.getRGBColor(104, 117, 176),
                TexturesManager.getTexture("refresh.png"));

        m_HomeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        m_Table.add(m_GameOverLabel).colspan(2).width(m_GameOverLabel.getWidth()).height(m_GameOverLabel.getHeight());
        m_Table.row();
        m_Table.add(m_HomeButton).pad(TABLE_CELL_PADDING);
        m_Table.add(m_RestartButton).pad(TABLE_CELL_PADDING);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        m_GameOverLabel.setText(getGameOverText());
    }

    private TextButton createGameOverLabel(int buttonWidth, int buttonHeight) {
        final TextButton textButton = new TextButton(getGameOverText(), createButtonStyle());
        textButton.setSize(buttonWidth, buttonHeight);
        return textButton;
    }

    private TextButton.TextButtonStyle createButtonStyle() {
        final NinePatch backgroundPath = TexturesManager.createButtonTexture();
        backgroundPath.setColor(Utils.getRGBColor(92, 190, 228));
        final NinePatchDrawable background = new NinePatchDrawable(backgroundPath);
        return new TextButton.TextButtonStyle(background, background, background, FontsManager.getLabelGameOverFont());
    }

    private String getGameOverText() {
        return "Game Over\n Score: " + ScoreManager.getScore();
    }
}
