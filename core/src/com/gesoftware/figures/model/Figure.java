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

        final Vec2i bounds = getBounds();
        setBounds(x, y, bounds.getX() * Definitions.c_SizeIcon * 1.2f, bounds.getY() * Definitions.c_SizeIcon * 1.2f);
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    private Vec2i getBounds() {
        final Vec2i bounds = new Vec2i();

        for (int row = 0; row < m_Matrix.getWidth(); row++)
            for (int column = 0; column < m_Matrix.getHeight(); column++)
                if (m_Matrix.contains(row, column)) {
                    if (row > bounds.getX())
                        bounds.setX(row);

                    if (column > bounds.getY())
                        bounds.setY(column);
                }

        return Vec2i.add(bounds, 1);
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
        batch.draw(m_Texture, getX() + row * Definitions.c_SizeIcon * 1.2f - getWidth() / 2, getY() + column * Definitions.c_SizeIcon * 1.2f, Definitions.c_SizeIcon, Definitions.c_SizeIcon);
    }
}
