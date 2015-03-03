package com.gesoftware.figures.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public final class Text extends Actor {
    private final BitmapFont m_Font;
    private String m_Value;

    public Text(final String text, final BitmapFont font) {
        this(text, font, 0, 0);
    }

    public Text(final String text, final BitmapFont font, final float x, final float y) {
        setBounds(x, y, font.getBounds(text).width, font.getBounds(text).height);
        setOrigin(getWidth() / 2, getHeight() / 2);

        m_Font = font;
        m_Value = text;
    }

    public void update() {
        setSize(m_Font.getBounds(m_Value).width, m_Font.getBounds(m_Value).height);
        setOrigin(getWidth() / 2, getHeight() / 2);

        setX((float) Math.floor(getX()));
        setY((float) Math.floor(getY()));
    }

    public final void setValue(final String value) {
        m_Value = value;
    }

    @Override
    public final void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        m_Font.draw(batch, m_Value, getX(), getY());
    }
}
