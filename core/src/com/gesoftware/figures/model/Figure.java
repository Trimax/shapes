package com.gesoftware.figures.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.managers.AchievementsManager;
import com.gesoftware.figures.managers.TexturesManager;
import com.gesoftware.figures.math.Matrix;

import java.util.Set;

public final class Figure extends Actor {
    private final Texture m_Texture;
    private final Matrix m_Matrix;

    public Figure(final Set<Ruby> figure, final float x, final float y) {
        m_Texture = TexturesManager.getTexture(figure.iterator().next().getRubyColor().getTextureName());
        m_Matrix  = AchievementsManager.computeMatrix(figure);

        setBounds(x, y, m_Matrix.getWidth() * Definitions.c_SizeIcon, m_Matrix.getHeight() * Definitions.c_SizeIcon);
        setScale(Definitions.c_SizeIcon / Definitions.c_SizeCell);
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    @Override
    public final void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (int row = 0; row < m_Matrix.getHeight(); row++)
            for (int column = 0; column < m_Matrix.getWidth(); column++)
                if (m_Matrix.contains(row, column))
                    draw(batch, row, column, parentAlpha);
    }

    private void draw(final Batch batch, final int row, final int column, final float parentAlpha) {
        batch.draw(m_Texture, getX() + column * Definitions.c_SizeIcon * 1.3f, getY() + row * Definitions.c_SizeIcon * 1.3f, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, m_Texture.getWidth(), m_Texture.getHeight(), false, false);
    }
}
