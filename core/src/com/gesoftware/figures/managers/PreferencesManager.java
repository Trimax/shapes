package com.gesoftware.figures.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gesoftware.figures.Definitions;

public final class PreferencesManager {
    private static final Preferences s_Preferences;

    static {
        s_Preferences = Gdx.app.getPreferences(Definitions.c_Title);
    }

    public static boolean has(final String key) {
        synchronized (s_Preferences) {
            return s_Preferences.contains(key);
        }
    }

    public static void putString(final String key, final String value) {
        synchronized (s_Preferences) {
            s_Preferences.putString(key, value);
            s_Preferences.flush();
        }
    }

    public static String getString(final String key) {
        synchronized (s_Preferences) {
            return s_Preferences.getString(key);
        }
    }

    public static String getString(final String key, final String nullValue) {
        synchronized (s_Preferences) {
            return s_Preferences.contains(key) ? getString(key) : nullValue;
        }
    }

    public static void putInteger(final String key, final Integer value) {
        synchronized (s_Preferences) {
            s_Preferences.putInteger(key, value);
            s_Preferences.flush();
        }
    }

    public static Integer getInteger(final String key) {
        synchronized (s_Preferences) {
            return s_Preferences.getInteger(key);
        }
    }

    public static Integer getInteger(final String key, final Integer nullValue) {
        synchronized (s_Preferences) {
            return s_Preferences.contains(key) ? getInteger(key) : nullValue;
        }
    }
}
