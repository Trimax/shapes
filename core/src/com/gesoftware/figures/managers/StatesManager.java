package com.gesoftware.figures.managers;

import com.gesoftware.figures.enums.GameState;
import com.gesoftware.figures.enums.RubyColor;

public final class StatesManager {
    public static boolean hasFieldChanged = false;
    public static RubyColor colorNext = RubyColor.getRandom();

    private static GameState m_GameState;

    public static GameState getGameState() {
        return m_GameState;
    }

    public static void setGameState(GameState m_GameState) {
        StatesManager.m_GameState = m_GameState;
    }
}
