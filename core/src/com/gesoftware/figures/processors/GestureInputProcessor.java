package com.gesoftware.figures.processors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public final class GestureInputProcessor implements GestureDetector.GestureListener {
    private final InputProcessor m_InputProcessor;

    public GestureInputProcessor(final InputProcessor inputProcessor) {
        m_InputProcessor = inputProcessor;
    }

    @Override
    public final boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public final boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public final boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public final boolean fling(final float velocityX, final float velocityY, final int button) {
        if (Math.abs(velocityX) > Math.abs(velocityY))
            m_InputProcessor.keyDown(isPositive(velocityX)? Input.Keys.RIGHT:Input.Keys.LEFT);
        else
            m_InputProcessor.keyDown(isPositive(velocityY)? Input.Keys.DOWN:Input.Keys.UP);

        return false;
    }

    private boolean isPositive(final float value) {
        return value > 0.f;
    }

    @Override
    public final boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public final boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public final boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public final boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}