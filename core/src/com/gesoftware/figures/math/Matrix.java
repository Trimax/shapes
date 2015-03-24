package com.gesoftware.figures.math;

import com.gesoftware.figures.model.Vec2i;

public final class Matrix {
    private final byte m_Items[][];
    private final Vec2i m_Size;

    public Matrix(final int rows, final int columns) {
        m_Size  = new Vec2i(rows, columns);
        m_Items = new byte[rows][columns];
    }

    public Matrix(final byte[][] items) {
        m_Items = items;
        m_Size = new Vec2i(items.length, items[0].length);
    }

    public final boolean hasSameSize(final Matrix matrix) {
        return m_Size.equals(matrix.m_Size);
    }

    public final void set(final int row, final int column, final byte value) {
        m_Items[row][column] = value;
    }

    public final Matrix rotate() {
        final Matrix rotatedMatrix = new Matrix(m_Size.getY(), m_Size.getX());
        for (int row = 0; row < m_Size.getX(); row++)
            for (int col = 0; col < m_Size.getY(); col++)
                rotatedMatrix.m_Items[col][m_Size.getX() - row - 1] = m_Items[row][col];

        return rotatedMatrix;
    }

    public final Matrix mirror() {
        final Matrix mirroredMatrix = new Matrix(m_Size.getX(), m_Size.getY());
        for (int row = 0; row < m_Size.getX(); row++)
            System.arraycopy(m_Items[row], 0, mirroredMatrix.m_Items[m_Size.getX() - row - 1], 0, m_Size.getY());

        return mirroredMatrix;
    }

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof Matrix && hasSameSize((Matrix) obj) && areElementsEqual(((Matrix) obj).m_Items);
    }

    private boolean areElementsEqual(final byte[][] items) {
        for (int row = 0; row < m_Size.getX(); row++)
            for (int col = 0; col < m_Size.getY(); col++)
                if (m_Items[row][col] != items[row][col])
                    return false;

        return true;
    }

    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();

        for (int row = 0; row < m_Size.getX(); row++) {
            builder.append(" ");
            for (int col = 0; col < m_Size.getY(); col++)
                builder.append(m_Items[row][col]).append(" ");

            builder.append("\n");
        }

        return builder.toString();
    }

    public final int getWidth() {
        return m_Size.getX();
    }

    public final int getHeight() {
        return m_Size.getY();
    }

    public final boolean contains(final int row, final int column) {
        return m_Items[row][column] != 0;
    }
}
