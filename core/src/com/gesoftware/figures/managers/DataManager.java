package com.gesoftware.figures.managers;

import com.gesoftware.figures.RubyGame;
import com.gesoftware.figures.screens.RubyScreen;

public final class DataManager {
    public static RubyGame   m_RubyGame;
    public static RubyScreen m_RubyScreen;

    public static void setRubyStage(final RubyScreen rubyStage) {
        m_RubyScreen = rubyStage;
    }

    public static RubyScreen getRubyScreen() {
        return m_RubyScreen;
    }
}
