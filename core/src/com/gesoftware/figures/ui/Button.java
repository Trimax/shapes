package com.gesoftware.figures.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button extends Actor {
    protected int m_ImageSize;
    protected Texture m_Image;

    protected Button(final int width, final int height) {
        setBounds(0, 0, width, height);
    }

    protected Button(final int x, final int y, final int width, final int height) {
        setBounds(x, y, width, height);
    }

    public Button(final int width, final int height, final Texture image, final float imageSizeFactor) {
        this(width, height);
        m_Image     = image;
        m_ImageSize = (int) (imageSizeFactor * getHeight());

    }

    public Button(final int x, final int y, final int width, final int height, final Texture image) {
        this(x, y, width, height);
        m_Image     = image;
        m_ImageSize = height;
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (m_Image == null)
            return;

        batch.draw(m_Image, (int) Math.floor(getX() + (getWidth() - m_ImageSize) / 2f), (int) Math.floor(getY() + (getHeight() - m_ImageSize) / 2f), m_ImageSize, m_ImageSize);
    }
}
