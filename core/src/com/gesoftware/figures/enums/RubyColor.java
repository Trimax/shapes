package com.gesoftware.figures.enums;

import java.util.Random;

public enum RubyColor {
    Red("ruby_red.png"),
    Blue("ruby_blue.png"),
    Pink("ruby_pink.png"),
    Green("ruby_green.png");

    private static final Random s_Generator;
    private final String m_TextureName;

    private static Integer m_CurrentColorIndex;

    static {
        s_Generator = new Random();
        s_Generator.setSeed(System.currentTimeMillis());

        m_CurrentColorIndex = 0;
    }

    private RubyColor(final String textureName) {
        m_TextureName = textureName;
    }

    public final String getTextureName() {
        return m_TextureName;
    }

    public static RubyColor getRandom() {
        return RubyColor.values()[s_Generator.nextInt(RubyColor.values().length)];
    }

    public static RubyColor getNext() {
       return RubyColor.values()[getNextIndex()];
    }

    private static int getNextIndex() {
        return (++m_CurrentColorIndex) % values().length;
    }
}
