package com.gesoftware.figures.ui;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.gesoftware.figures.managers.TexturesManager;

public class ClickableButton extends Button {
    protected static final float PRESS_ANIMATION_DURATION = .5f;
    protected static final float IMAGE_SIZE_FACTOR = .6f;

    private NinePatch m_Texture;
    private Color m_InitialColor;
    private Color m_PressedColor;

    public ClickableButton(final int x, final int y, final int width, final int height, final Color initialColor, final Color pressedColor) {
        super(x, y, width, height);
        setColor(initialColor);
        m_InitialColor = initialColor;
        m_PressedColor = pressedColor;

        m_Texture = TexturesManager.createButtonTexture();
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                addAction(Actions.color(m_PressedColor, PRESS_ANIMATION_DURATION));
                return false;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                addAction(Actions.color(m_InitialColor, PRESS_ANIMATION_DURATION));
            }
        });
    }

    public ClickableButton(final int width, final int height, final Color initialColor, final Color pressedColor) {
        this(0, 0, width, height, initialColor, pressedColor);
    }

    public ClickableButton(final int width, final int height, final Color initialColor, final Color pressedColor, final Texture image) {
        this(width, height, initialColor, pressedColor, image, IMAGE_SIZE_FACTOR);
    }

    public ClickableButton(final int width, final int height, final Color initialColor, final Color pressedColor, final Texture image, final float imageSizeFactor) {
        this(width, height, initialColor, pressedColor);
        m_Image     = image;
        m_ImageSize = (int) (imageSizeFactor * getHeight());
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        m_Texture.draw(batch, getX(), getY(), getWidth(), getHeight());
        m_Texture.setColor(getColor());
        super.draw(batch, parentAlpha);
    }

    public void setInitialColor(final Color initialColor) {
        m_InitialColor = initialColor;
    }
}
