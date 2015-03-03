package com.gesoftware.figures.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.enums.Theme;
import com.gesoftware.figures.managers.TexturesManager;
import com.gesoftware.figures.managers.ThemesManager;

public final class Cell extends Actor {
    public Cell(final float x, final float y) {
        setBounds(x, y, Definitions.c_SizeCell, Definitions.c_SizeCell);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(ThemesManager.getCurrentTheme() == Theme.Light ? TexturesManager.getTexture("empty.png") : TexturesManager.getTexture("empty_dark.png") , getX(), getY(), getWidth(), getHeight());
    }
}
