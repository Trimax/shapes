package com.gesoftware.figures.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.RubyGame;
import com.gesoftware.figures.managers.AchievementsManager;
import com.gesoftware.figures.services.IServices;

public class DesktopLauncher {
	public static void main(final String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.addIcon("icon16.png", Files.FileType.Internal);
		config.width 	 = Definitions.c_SizeWindow;
		config.height    = (int) (Definitions.c_SizeWindow + Definitions.c_SizeCell + 2 * Definitions.c_FooterHeight);
		config.resizable = false;

        AchievementsManager.setPlayService(new IServices() {
            @Override
            public void signIn() {
                System.out.println("Sigh in");

            }

            @Override
            public void showAchievements() {
                System.out.println("Show achievements");

            }

            @Override
            public void showLeaderboard() {
                System.out.println("Show leaderboard");

            }

            @Override
            public final void addScore(final String scoreName, final Integer value) {
                System.out.println("Score " + scoreName + " must be set to " + value);
            }

            @Override
            public final Integer getScore(final String scoreName) {
                return 0;
            }

            @Override
            public final void unlockAchievement(final String achievementID) {
                System.out.println("Achievement " + achievementID + " unlocked");
            }

            @Override
            public final boolean isAchievementUnlocked(final String achievementID) {
                return false;
            }
        });

		new LwjglApplication(new RubyGame(), config);
	}
}
