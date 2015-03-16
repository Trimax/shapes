package com.gesoftware.figures.managers;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.enums.Direction;
import com.gesoftware.figures.model.Body;
import com.gesoftware.figures.model.Field;
import com.gesoftware.figures.model.Ruby;
import com.gesoftware.figures.model.Vec2i;

import java.util.*;

public final class ReduceManager {
    public static void reduce(final Body body, final ParallelAction reduces) {
        final Map<Ruby, ArrayList<Ruby>> adjacencyList = buildAdjacencyList(body.getField());

        int reducedFiguresCount = 0;
        final HashSet<Ruby> visitedVertices = new HashSet<Ruby>();
        for (final Ruby ruby : adjacencyList.keySet())
            if (!visitedVertices.contains(ruby))
                if (reduceFigure(search(ruby, adjacencyList, visitedVertices), body, reduces))
                    reducedFiguresCount++;

        AchievementsManager.tryUnlock(reducedFiguresCount);
    }

    private static Map<Ruby, ArrayList<Ruby>> buildAdjacencyList(final Field field) {
        final Map<Ruby, ArrayList<Ruby>> adjacencyList = new HashMap<Ruby, ArrayList<Ruby>>();
        for (int x = 0; x < Definitions.c_NumberCells; x++)
            for (int y = 0; y < Definitions.c_NumberCells; y++)
                if (!field.isFree(x, y))
                    adjacencyList.put(field.get(x, y), new ArrayList<Ruby>());

        final Set<Ruby> vertices = new HashSet<Ruby>(adjacencyList.keySet());
        for (final Ruby vertex : vertices)
            for (final Direction direction : Direction.values()) {
                final Vec2i position = Vec2i.add(vertex.getPosition(), direction.getVector());
                if ((field.isValid(position))&&(!field.isFree(position))&&(vertex.equals(field.get(position))))
                    adjacencyList.get(vertex).add(field.get(position));
            }

        return adjacencyList;
    }

    private static Set<Ruby> search(final Ruby startupVertex, final Map<Ruby, ArrayList<Ruby>> adjacencyList, final Set<Ruby> visitedVertices) {
        final HashSet<Ruby> figure = new HashSet<Ruby>();

        final Queue<Ruby> queue = new LinkedList<Ruby>();
        queue.add(startupVertex);
        while (!queue.isEmpty()) {
            final Ruby ruby = queue.poll();
            if (visitedVertices.contains(ruby))
                continue;

            visitedVertices.add(ruby);
            figure.add(ruby);

            queue.addAll(adjacencyList.get(ruby));
        }

        if (figure.size() >= 3)
            AchievementsManager.tryUnlock(figure);

        return figure;
    }

    private static boolean reduceFigure(final Set<Ruby> figure, final Body body, final ParallelAction reduces) {
        if (figure.size() < Definitions.c_SizeFigure)
            return false;

        for (final Ruby block : figure) {
            final Action action = block.getFadingAnimation(new Runnable() {
                @Override
                public final void run() {
                    body.getField().remove(block.getPosition());
                    body.removeActor(block);
                }
            });
            action.setTarget(body);
            reduces.addAction(action);
        }

        ScoreManager.addScore((int) Math.pow(figure.size(), 2), figure);
        AchievementsManager.tryUnlock();

        return true;
    }
}
