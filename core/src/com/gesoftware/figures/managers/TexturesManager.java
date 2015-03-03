package com.gesoftware.figures.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public final class TexturesManager {
    private static final TextureLoader.TextureParameter c_TextureParameter = new TextureLoader.TextureParameter();
    private static AssetManager m_AssetManager;

    static {
        c_TextureParameter.minFilter = Texture.TextureFilter.Linear;
        c_TextureParameter.magFilter = Texture.TextureFilter.Linear;
    }

    public static void init() {
        m_AssetManager = new AssetManager();
    }

    public static Texture getTexture(final String name) {
        if (m_AssetManager.isLoaded(name))
            return m_AssetManager.get(name, Texture.class);

        m_AssetManager.load(name, Texture.class, c_TextureParameter);
        m_AssetManager.finishLoading();
        return m_AssetManager.get(name, Texture.class);
    }

    private static NinePatch createNinePath(final Texture texture, final int padding) {
        return createNinePath(texture, padding, padding, padding, padding);
    }

    private static NinePatch createNinePath(final Texture texture, final int left, final int right, final int top, final int bottom) {
        return new NinePatch(new TextureRegion(texture, 1, 1, texture.getWidth() - 2, texture.getHeight() - 2), left, right, top, bottom);
    }

    public static NinePatch createButtonTexture() {
        return createNinePath(new Texture("balloon.9.png"), 30);
    }

    public static void dispose() {
        m_AssetManager.dispose();
    }
}

