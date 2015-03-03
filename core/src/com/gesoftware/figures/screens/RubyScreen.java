package com.gesoftware.figures.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.gesoftware.figures.enums.GameState;
import com.gesoftware.figures.managers.DataManager;
import com.gesoftware.figures.managers.StatesManager;
import com.gesoftware.figures.managers.ThemesManager;
import com.gesoftware.figures.stages.GameOverStage;
import com.gesoftware.figures.stages.PauseStage;
import com.gesoftware.figures.stages.RubyStage;

public final class RubyScreen implements Screen {
    private RubyStage     m_RubyStage;
    private PauseStage    m_PauseStage;
    private GameOverStage m_GameOverStage;

    @Override
    public final void show() {
        m_RubyStage     = new RubyStage();
        m_PauseStage    = new PauseStage();
        m_GameOverStage = new GameOverStage();

        StatesManager.setGameState(GameState.Play);
        DataManager.setRubyStage(this);
    }

    @Override
    public final void render(final float delta) {
        Color background = ThemesManager.getCurrentTheme().getColor();
        Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        m_RubyStage.act(delta);
        m_RubyStage.draw();

        if (StatesManager.getGameState() == GameState.Pause) {
            m_PauseStage.act(delta);
            m_PauseStage.draw();
        }

        if (StatesManager.getGameState() == GameState.GameOver) {
            m_GameOverStage.act(delta);
            m_GameOverStage.draw();
        }
    }

    @Override
    public final void resize(final int width, final int height) {
        m_RubyStage.getViewport().update(width, height, true);
    }

    @Override
    public final void pause() {

    }

    @Override
    public final void resume() {

    }

    @Override
    public final void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public final void dispose() {
        m_RubyStage.dispose();
        m_GameOverStage.dispose();
        m_PauseStage.dispose();
    }

    public RubyStage getRubyStage() {
        return m_RubyStage;
    }

    public PauseStage getPauseStage() {
        return m_PauseStage;
    }

    public GameOverStage getGameOverStage() {
        return m_GameOverStage;
    }
}
