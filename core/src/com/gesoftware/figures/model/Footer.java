package com.gesoftware.figures.model;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.managers.StatesManager;

public final class Footer extends Group {
    private Ruby m_NextRuby;

    public Footer() {
        setBounds(0, 0, Definitions.c_FieldSize, Definitions.c_FooterHeight);
    }

    public final void update() {
        if (m_NextRuby != null)
            removeActor(m_NextRuby);

        m_NextRuby = new Ruby(StatesManager.colorNext, new Vec2i());
        m_NextRuby.setScale(0.8f);
        m_NextRuby.setPosition((Definitions.c_FieldSize - Definitions.c_SizeCell) * 0.5f, (Definitions.c_FooterHeight - Definitions.c_SizeCell) * 0.5f);
        addActor(m_NextRuby);
    }
}
