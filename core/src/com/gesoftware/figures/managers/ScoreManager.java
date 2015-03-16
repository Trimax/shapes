package com.gesoftware.figures.managers;

import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.model.Header;
import com.gesoftware.figures.model.Ruby;

import java.util.Set;

public final class ScoreManager {
    private static Integer s_Score;

    private static Header s_Panel;

    static {
        s_Score = 0;
    }

    public static Integer getBestScore() {
        return PreferencesManager.getInteger(Definitions.c_PreferenceScoreBest, 0);
    }

    public static Integer getScore() {
        return s_Score;
    }

    public static void addScore(final Integer additionalScore, final Set<Ruby> figure) {
        s_Score += additionalScore;
        if (s_Score > getBestScore()) {
            PreferencesManager.putInteger(Definitions.c_PreferenceScoreBest, s_Score);
            AchievementsManager.submitScore(s_Score);
        }

        if (s_Panel != null)
            s_Panel.animateScore(additionalScore, figure);
    }

    public static void resetScore() {
        s_Score = 0;
    }

    public static void setScorePanel(final Header panel) {
        s_Panel = panel;
    }
}
