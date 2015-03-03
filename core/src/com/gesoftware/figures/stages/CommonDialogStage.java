package com.gesoftware.figures.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class CommonDialogStage extends Stage {
    private ShapeRenderer renderer = new ShapeRenderer();

    public CommonDialogStage() {
        super(new ScreenViewport());
    }

    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void draw() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(new Color(238f / 225f, 228f / 225f, 218 / 225f, 0.73f));
        renderer.rect(0, 0, getWidth(), getHeight());
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        super.draw();
    }
}
