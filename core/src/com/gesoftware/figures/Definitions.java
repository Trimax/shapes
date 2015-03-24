package com.gesoftware.figures;

import com.badlogic.gdx.Gdx;

public final class Definitions {
    /* Android package*/
    public static String c_AndroidId = "com.gesoftware.figures";

    /* Default window size */
    public static final int c_SizeWindow = 512;

    /* Field right and left borders size*/
    public static final float c_FieldBorderSize = (int) (0.02f * c_SizeWindow);

    /* Default field size with border*/
    public static final int c_FieldSize = (int)(c_SizeWindow - 2 * c_FieldBorderSize);

    /* The number of cells in a row */
    public static final int c_NumberCells = 4;

    /* Border size */
    public static final int c_SizeBorder = (int) (0.02f * c_FieldSize);

    /* Cell size */
    public static final float c_SizeCell = (c_FieldSize - c_SizeBorder * (c_NumberCells + 1)) / c_NumberCells;

    /* When achievement unlocked this icon will be shown */
    public static final float c_SizeIcon = c_SizeCell * 0.5f;

    /* Footer size*/
    public static final float c_FooterHeight = c_SizeCell * 1.2f;

    /* Footer size*/
    public static final float c_HeaderSize = c_SizeCell * 0.8f;

    /* Ruby moving duration */
    public static final float c_DurationMove = 0.1f;

    /* Ruby injection duration */
    public static final float c_DurationInject = 0.2f;

    /* Ruby fading duration */
    public static final float c_DurationFade = 0.2f;

    /* Score update duration */
    public static final float c_DurationScore = 1.0f;

    /* Score fading duration */
    public static final float c_DurationScoreFade = 0.5f;

    /* Field fading duration */
    public static final float c_DurationFieldFade = 0.8f;

    /* Menu buttons fading duration */
    public static final float c_DurationMenuFade = 0.7f;

    /* Minimal figure size to be reduced */
    public static final int c_SizeFigure = 3;

    /*** UI definitions ***/

    /* Title */
    public static final String c_Title = "Shapes";

    /* Score movement distance */
    public static final float c_DistanceScore = 30.f;

    /*** Properties ***/

    /* Best score property */
    public static final String c_PreferenceScoreBest = "scoreBest";
}
