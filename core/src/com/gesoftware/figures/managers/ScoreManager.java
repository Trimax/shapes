package com.gesoftware.figures.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.model.Header;
import com.gesoftware.figures.model.Ruby;

import java.util.Set;

public final class ScoreManager {
    private static final Preferences c_Preferences;
    private static Integer s_Score;

    private static Header s_Panel;

    static {
        c_Preferences = Gdx.app.getPreferences(Definitions.c_Title);
        s_Score = 0;
    }

    public static Integer getBestScore() {
        if (!c_Preferences.contains(Definitions.c_PreferenceScoreBest))
            return 0;

        return c_Preferences.getInteger(Definitions.c_PreferenceScoreBest);
    }

    public static Integer getScore() {
        return s_Score;
    }

    public static void addScore(final Integer additionalScore, final Set<Ruby> figure) {
        s_Score += additionalScore;
        if (s_Score > getBestScore()) {
            c_Preferences.putInteger(Definitions.c_PreferenceScoreBest, s_Score);
            c_Preferences.flush();

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
