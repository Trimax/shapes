package com.gesoftware.figures.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.utils.Utils;

public final class FontsManager {
    private static BitmapFont m_LabelScoreDarkFont;
    private static BitmapFont m_LabelBestScoreFont;
    private static BitmapFont m_LabelScoreFont;
    private static BitmapFont m_GameOverLabelFont;

    private static final int m_DefaultScoreLabelSize = getScaledFont(60);
    private static final int m_ScorePopupSize        = getScaledFont(80);

    public static void init() {
        createLabelScoreDarkFont();
        createLabelBestScoreFont();
        createLabelScoreFont();
        createLabelGameOverFont();
    }

    private static void createLabelScoreFont() {
        m_LabelScoreFont = getBitmapFont(m_DefaultScoreLabelSize);
        m_LabelScoreFont.setColor(Utils.getRGBColor(165, 201, 137));
    }

    private static void createLabelBestScoreFont() {
        m_LabelBestScoreFont = getBitmapFont(m_DefaultScoreLabelSize);
        m_LabelBestScoreFont.setColor(Utils.getRGBColor(92, 190, 228));
    }

    private static void createLabelScoreDarkFont() {
        m_LabelScoreDarkFont = getBitmapFont(m_ScorePopupSize);
        m_LabelScoreDarkFont.setColor(Utils.getRGBColor(119, 110, 101, 0.9f));
    }

    private static void createLabelGameOverFont() {
        m_GameOverLabelFont = getBitmapFont(getScaledFont(40));
        m_GameOverLabelFont.setColor(Color.WHITE);
    }

    public static BitmapFont getLabelBestScoreFont() {
        return m_LabelBestScoreFont;
    }

    public static BitmapFont getLabelGameOverFont() {
        return m_GameOverLabelFont;
    }

    public static BitmapFont getLabelScoreFont() {
        return m_LabelScoreFont;
    }

    public static BitmapFont getLabelScoreDarkFont() {
        return m_LabelScoreDarkFont;
    }

    private static BitmapFont getBitmapFont(final float size) {
        final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        font.setScale(size / 128.f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        return font;
    }

    private static int getScaledFont(final int value) {
        float scaleFactor = Definitions.c_FieldSize / 400f;
        return (int) (value * scaleFactor);
    }

    public static void dispose() {
        m_LabelBestScoreFont.dispose();
        m_LabelScoreDarkFont.dispose();
        m_LabelScoreFont.dispose();
    }
}
