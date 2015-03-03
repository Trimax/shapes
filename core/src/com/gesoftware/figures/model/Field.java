package com.gesoftware.figures.model;

import com.gesoftware.figures.Definitions;

import java.util.Random;

public final class Field {
    private final Random m_Generator;
    private final Ruby m_Cells[][];

    public Field() {
        m_Cells = new Ruby[Definitions.c_NumberCells][Definitions.c_NumberCells];

        m_Generator = new Random();
        m_Generator.setSeed(System.currentTimeMillis());
    }

    public final void set(final Vec2i position, final Ruby ruby) {
        m_Cells[position.getX()][position.getY()] = ruby;
    }

    public final Ruby get(final Vec2i position) {
        return get(position.getX(), position.getY());
    }

    public final Ruby get(final int x, final int y) {
        return m_Cells[x][y];
    }

    public final void remove(final Vec2i position) {
        set(position, null);
    }

    public final Ruby extract(final Vec2i position) {
        final Ruby ruby = get(position);
        remove(position);
        return ruby;
    }

    public final Vec2i getFreeCell() {
        if (!hasFreeCells())
            return null;

        Vec2i position = new Vec2i(m_Generator.nextInt(Definitions.c_NumberCells), m_Generator.nextInt(Definitions.c_NumberCells));
        while (!isFree(position))
            position = new Vec2i(m_Generator.nextInt(Definitions.c_NumberCells), m_Generator.nextInt(Definitions.c_NumberCells));

        return position;
    }

    public final boolean hasFreeCells() {
        for (int x = 0; x < Definitions.c_NumberCells; x++)
            for (int y = 0; y < Definitions.c_NumberCells; y++)
                if (isFree(new Vec2i(x, y)))
                    return true;

        return false;
    }

    public final boolean isEmpty() {
        for (int x = 0; x < Definitions.c_NumberCells; x++)
            for (int y = 0; y < Definitions.c_NumberCells; y++)
                if (!isFree(new Vec2i(x, y)))
                    return false;

        return true;
    }

    public final boolean isAllowed(final Vec2i position) {
        return isValid(position) && isFree(position);
    }

    public final boolean isFree(final Vec2i position) {
        return isFree(position.getX(), position.getY());
    }

    public final boolean isFree(final int x, final int y) {
        return m_Cells[x][y] == null;
    }

    public final boolean isValid(final Vec2i position) {
        return position.getX() >= 0 && position.getX() < Definitions.c_NumberCells && position.getY() >= 0 && position.getY() < Definitions.c_NumberCells;
    }

    public final boolean isGameOver() {
        return !hasFreeCells();
    }
}
