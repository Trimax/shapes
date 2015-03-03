package com.gesoftware.figures.enums;

import com.gesoftware.figures.math.Matrix;

public enum Achievement {
    CleanField("CgkIkvj8yrgWEAIQCA",   100, null),

    Double("CgkIkvj8yrgWEAIQCQ",        50, null),

    Figure_Block("CgkIkvj8yrgWEAIQBg",   0, new Matrix(new byte[][]{{1, 1},
                                                                    {1, 1}})),


    Figure_Corner("CgkIkvj8yrgWEAIQDA",  0, new Matrix(new byte[][]{{1, 0},
                                                                    {1, 1}})),

    Figure_C("CgkIkvj8yrgWEAIQBA",       0, new Matrix(new byte[][]{{1, 1, 1},
                                                                    {1, 0, 0},
                                                                    {1, 1, 1}})),

    Figure_H("CgkIkvj8yrgWEAIQAw",       0, new Matrix(new byte[][]{{1, 0, 1},
                                                                    {1, 1, 1},
                                                                    {1, 0, 1}})),

    Figure_I("CgkIkvj8yrgWEAIQBQ",       0, new Matrix(new byte[][]{{1, 0, 0},
                                                                    {1, 0, 0},
                                                                    {1, 0, 0}})),

    Figure_T("CgkIkvj8yrgWEAIQAQ",       0, new Matrix(new byte[][]{{1, 1, 1},
                                                                    {0, 1, 0},
                                                                    {0, 1, 0}})),

    Figure_W("CgkIkvj8yrgWEAIQBw",       0, new Matrix(new byte[][]{{1, 0, 0},
                                                                    {1, 1, 0},
                                                                    {0, 1, 1}})),

    Figure_Plus("CgkIkvj8yrgWEAIQAg",    0, new Matrix(new byte[][]{{0, 1, 0},
                                                                    {1, 1, 1},
                                                                    {0, 1, 0}})),

    Hundred("CgkIkvj8yrgWEAIQDg",        0, null),
    Thousand("CgkIkvj8yrgWEAIQDw",       0, null),
    TenThousand("CgkIkvj8yrgWEAIQEA",    0, null),

    Triple("CgkIkvj8yrgWEAIQCg",       200, null),

    Zero("CgkIkvj8yrgWEAIQDQ",           0, null);

    private final String m_ID;
    private final Matrix m_Matrix;
    private final Integer m_Score;

    private Achievement(final String id, final int score, final Matrix matrix) {
        m_Matrix = matrix;
        m_Score  = score;
        m_ID     = id;
    }

    public final Matrix getMatrix() {
        return m_Matrix;
    }

    public final Integer getScore() {
        return m_Score;
    }

    public final String getID() {
        return m_ID;
    }
}
