package com.gesoftware.figures.enums;

import com.badlogic.gdx.Input;
import com.gesoftware.figures.model.Vec2i;

public enum Direction {
    Left(new Vec2i(-1, 0), Input.Keys.LEFT),
    Right(new Vec2i(1, 0), Input.Keys.RIGHT),
    Up(new Vec2i(0, 1),    Input.Keys.UP),
    Down(new Vec2i(0, -1), Input.Keys.DOWN);

    private final Vec2i m_Vector;
    private final int m_KeyCode;

    private Direction(final Vec2i vector, final int keyCode) {
        m_KeyCode = keyCode;
        m_Vector  = vector;
    }

    public final Vec2i getVector() {
        return m_Vector;
    }

    public static Direction getByKeyCode(final int keyCode) {
        for (final Direction direction : values())
            if (direction.m_KeyCode == keyCode)
                return direction;

        return null;
    }
}
