package com.gesoftware.figures.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public abstract class ToggleButton extends ClickableButton {
    private final Texture m_EnableButtonImage;
    private final Texture m_DisableButtonImage;
    private boolean m_IsEnabled;

    public ToggleButton(final int width, final int height, final Color initialColor, final Color pressedColor, final boolean isEnabled, final Texture enableButtonImage, final Texture disableButtonImage) {
        this(0, 0, width, height, initialColor, pressedColor, isEnabled, enableButtonImage, disableButtonImage);
    }

    public ToggleButton(final int x, final int y, final int width, final int height, final Color initialColor, final Color pressedColor, final boolean isEnabled, final Texture enableButtonImage, final Texture disableButtonImage) {
        super(x, y, width, height, initialColor, pressedColor);
        m_IsEnabled          = isEnabled;
        m_EnableButtonImage  = enableButtonImage;
        m_DisableButtonImage = disableButtonImage;

        m_ImageSize = (int) (getHeight() * IMAGE_SIZE_FACTOR);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                m_IsEnabled = !m_IsEnabled;
                onChange(m_IsEnabled);
            }
        });
    }

    @Override
    public final void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawButtonImage(batch);
    }

    private void drawButtonImage(final Batch batch) {
        if (m_IsEnabled)
            batch.draw(m_EnableButtonImage, (int) Math.floor(getX() + (getWidth() - m_ImageSize) / 2f),(int) (Math.floor(getY() + (getHeight() - m_ImageSize) / 2f)), m_ImageSize, m_ImageSize);
        else
            batch.draw(m_DisableButtonImage, (int) Math.floor(getX() + (getWidth() - m_ImageSize) / 2f), (int) Math.floor(getY() + (getHeight() - m_ImageSize) / 2f), m_ImageSize, m_ImageSize);
    }

    public abstract void onChange(final boolean enable);
}
