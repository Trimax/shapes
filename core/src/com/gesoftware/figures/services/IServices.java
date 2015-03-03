package com.gesoftware.figures.services;

public interface IServices {
    public void signIn();

    public void showAchievements();
    public void showLeaderboard();

    public void addScore(final String scoreName, final Integer value);
    public Integer getScore(final String scoreName);

    public void unlockAchievement(final String achievementID);
    public boolean isAchievementUnlocked(final String achievementID);
}
