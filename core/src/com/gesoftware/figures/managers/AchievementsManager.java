package com.gesoftware.figures.managers;

import com.gesoftware.figures.enums.Achievement;
import com.gesoftware.figures.enums.GameState;
import com.gesoftware.figures.math.Matrix;
import com.gesoftware.figures.model.Ruby;
import com.gesoftware.figures.model.Vec2i;
import com.gesoftware.figures.services.IServices;

import java.util.Set;

public final class AchievementsManager {
    public static final String c_ScoreID = "CgkIkvj8yrgWEAIQAA";
    public static IServices m_Service;

    public static void setPlayService(final IServices service) {
        m_Service = service;
    }

    public static void tryUnlock(final Set<Ruby> figure) {
        final Matrix matrix = computeMatrix(figure);

        for (final Achievement achievement : Achievement.values())
            if ((achievement.getMatrix() != null)&&(achievement.getMatrix().hasSameSize(matrix)))
                tryUnlock(matrix, achievement);
    }

    private static void tryUnlock(final Matrix matrix, final Achievement achievement) {
        Matrix currentMatrix = matrix;
        for (int angle = 0; angle < 4; angle++) {
            currentMatrix = currentMatrix.rotate();
            if (achievement.getMatrix().equals(currentMatrix))
                m_Service.unlockAchievement(achievement.getID());
        }
    }

    private static Matrix computeMatrix(final Set<Ruby> figure) {
        final Vec2i size = computeSize(figure);
        final int side = Math.max(size.getX(), size.getY());
        final Vec2i min  = computeMin(figure);

        final byte[][] items = new byte[side][side];
        for (final Ruby ruby : figure) {
            final Vec2i position = Vec2i.diff(ruby.getPosition(), min);
            items[position.getX()][position.getY()] = 1;
        }

        return new Matrix(items);
    }

    private static Vec2i computeMin(final Set<Ruby> figure) {
        final Vec2i min = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (final Ruby ruby : figure) {
            min.setX(Math.min(min.getX(), ruby.getPosition().getX()));
            min.setY(Math.min(min.getY(), ruby.getPosition().getY()));
        }

        return min;
    }

    private static Vec2i computeSize(final Set<Ruby> figure) {
        final Vec2i min = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
        final Vec2i max = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (final Ruby ruby : figure) {
            max.setX(Math.max(max.getX(), ruby.getPosition().getX()));
            max.setY(Math.max(max.getY(), ruby.getPosition().getY()));

            min.setX(Math.min(min.getX(), ruby.getPosition().getX()));
            min.setY(Math.min(min.getY(), ruby.getPosition().getY()));
        }

        return new Vec2i(1 + max.getX() - min.getX(), 1 + max.getY() - min.getY());
    }

    public static void tryUnlock(final int reducedFiguresCount) {
        switch (reducedFiguresCount) {
            case 2:
                unlock(Achievement.Double);
                break;

            case 3:
                unlock(Achievement.Triple);
                break;
        }
    }

    public static void tryUnlock() {
        if ((StatesManager.getGameState() == GameState.GameOver)&&(ScoreManager.getScore() == 0))
            unlock(Achievement.Zero);

        if (ScoreManager.getScore() >= 100)
            unlock(Achievement.Hundred);

        if (ScoreManager.getScore() >= 1000)
            unlock(Achievement.Thousand);

        if (ScoreManager.getScore() >= 10000)
            unlock(Achievement.TenThousand);
    }

    public static void tryUnlock(final boolean isFieldEmpty) {
        if (!isFieldEmpty)
            return;

        unlock(Achievement.CleanField);
    }

    public static void submitScore(final Integer score) {
        if (m_Service != null)
            m_Service.addScore(c_ScoreID, score);
    }

    private static void unlock(final Achievement achievement) {
        if (PreferencesManager.has(achievement.getID()))
            return;

        if (m_Service != null) {
            m_Service.unlockAchievement(achievement.getID());
            PreferencesManager.putString(achievement.getID(), achievement.getID());
        }
    }
}
