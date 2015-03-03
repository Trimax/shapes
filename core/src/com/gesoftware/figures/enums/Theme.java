package com.gesoftware.figures.enums;

import com.badlogic.gdx.graphics.Color;
import com.gesoftware.figures.utils.Utils;

public enum Theme {
    Light(1, Color.WHITE),
    Dark(2, Utils.getRGBColor(33,33,33));

    private int m_ID;
    private Color m_Color;

    Theme(final int id, final Color color) {
        m_ID    = id;
        m_Color = color;
    }

    public static Theme getThemeByID(final int id) {
        for (final Theme theme : values())
            if (theme.getID() == id)
                return theme;
        return null;
    }

    public int getID() {
        return m_ID;
    }

    public Color getColor() {
        return m_Color;
    }

    public boolean isLight() {
        return m_Color == Color.WHITE;
    }
}
