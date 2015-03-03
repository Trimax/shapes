package com.gesoftware.figures.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public final class Utils {
    public static Color getRGBColor(final int r, final int g, final int b) {
        return getRGBColor(r, g, b, 1.f);
    }

    public static Color getRGBColor(final int r, final int g, final int b, final float a) {
        return new Color(r / 255f, g / 255f, b / 255f, a);
    }

    public static Action moveFrom(final Actor actor, final float fromX, final float fromY, final float duration) {
        float initialX = actor.getX();
        float initialY = actor.getY();
        final Action moveFrom = Actions.moveTo(fromX, fromY);
        final Action moveTo   = Actions.moveTo(initialX, initialY, duration, Interpolation.swingOut);

        final Action sequence = Actions.sequence(moveFrom, moveTo);
        moveFrom.setTarget(actor);
        moveTo.setTarget(actor);

        return sequence;
    }

    public static Action moveTo(final Actor actor, final float toX, final float toY, final float duration) {
        final Action moveTo = Actions.moveTo(toX, toY, duration, Interpolation.swingIn);
        moveTo.setTarget(actor);
        return moveTo;
    }

    public static Group wrapToGroup(final Actor actor) {
        final Group group = new Group();
        group.addActor(actor);
        group.setSize(actor.getWidth(), actor.getHeight());

        return group;
    }
}
