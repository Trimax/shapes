package com.gesoftware.figures.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.enums.Theme;

public final class ThemesManager {
    private static final Preferences c_Preferences = Gdx.app.getPreferences(Definitions.c_Title);
    private final static String c_Theme = "theme";

    public static Theme getCurrentTheme() {
        return Theme.getThemeByID(c_Preferences.getInteger(c_Theme, Theme.Light.getID()));
    }

    public static void setCurrentTheme(final Theme theme) {
        c_Preferences.putInteger(c_Theme, theme.getID());
        c_Preferences.flush();
    }
}
