package com.gesoftware.figures;

import com.gesoftware.figures.managers.AdManager;
import com.gesoftware.figures.managers.DataManager;
import com.gesoftware.figures.managers.FontsManager;
import com.gesoftware.figures.managers.TexturesManager;
import com.gesoftware.figures.screens.MenuScreen;
import com.badlogic.gdx.Game;

public final class RubyGame extends Game {

	@Override
	public final void create () {
		DataManager.m_RubyGame = this;
        TexturesManager.init();
        FontsManager.init();
        AdManager.init();

		setScreen(new MenuScreen());
	}

    @Override
    public void resume() {
        TexturesManager.init();
        FontsManager.init();
    }

    @Override
    public final void dispose() {
        super.dispose();
        TexturesManager.dispose();
        FontsManager.dispose();
    }
}
