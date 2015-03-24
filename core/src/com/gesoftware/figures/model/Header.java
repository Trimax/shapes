package com.gesoftware.figures.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.enums.Achievement;
import com.gesoftware.figures.managers.*;
import com.gesoftware.figures.math.Matrix;

import java.util.Set;

public final class Header extends Group {
    private final Image m_Rating;

    private final Text m_ScoreCurrent;
    private final Text m_ScoreBest;

    public Header() {
        setSize(Definitions.c_FieldSize, Definitions.c_HeaderSize);

        final Texture ratingTexture = TexturesManager.getTexture("rating.png");
        float ratingSize = Definitions.c_HeaderSize * .8f;
        m_Rating = new Image(ratingTexture);
        m_Rating.setBounds((Definitions.c_FieldSize - ratingSize) / 2,
                (float) Math.floor((Definitions.c_SizeCell - ratingSize) / 2),
                ratingSize,
                ratingSize);


        m_ScoreCurrent = new Text(String.valueOf(ScoreManager.getScore()), FontsManager.getLabelScoreFont(), m_Rating.getX(), m_Rating.getY());
        m_ScoreCurrent.setX(m_Rating.getX() - 20.f - m_ScoreCurrent.getWidth());
        m_ScoreCurrent.setY((float) Math.floor((Definitions.c_SizeCell + m_ScoreCurrent.getHeight()) / 2));
        m_ScoreCurrent.update();

        m_ScoreBest = new Text(String.valueOf(ScoreManager.getBestScore()), FontsManager.getLabelBestScoreFont(), m_Rating.getX(), m_Rating.getY());
        m_ScoreBest.setX(m_Rating.getX() + 20.f + m_Rating.getWidth());
        m_ScoreBest.setY((float) Math.floor((Definitions.c_SizeCell + m_ScoreBest.getHeight()) / 2));
        m_ScoreBest.update();

        addActor(m_Rating);
        addActor(m_ScoreBest);
        addActor(m_ScoreCurrent);
    }

    public final void animateScore(final int value, final Set<Ruby> shape) {
        final Text text = new Text("+" + value, FontsManager.getLabelScoreDarkFont(),
                                   m_Rating.getX() + (m_Rating.getWidth() - FontsManager.getLabelScoreDarkFont().getBounds("+" + value).width) / 2f,
                                   m_Rating.getY() + m_Rating.getHeight() / 2f);

        text.addAction(Actions.sequence(Actions.moveTo(text.getX(), text.getY() + Definitions.c_DistanceScore, Definitions.c_DurationScore), Actions.run(new Runnable() {
            @Override
            public void run() {
                text.remove();
            }
        })));


        final Figure figure = new Figure(shape,
                                         m_Rating.getX() + (m_Rating.getWidth() - FontsManager.getLabelScoreDarkFont().getBounds("+" + value).width) / 2f,
                                         m_Rating.getY() + m_Rating.getHeight() / 2f);
        figure.addAction(Actions.sequence(Actions.moveTo(figure.getX(), figure.getY() + Definitions.c_DistanceScore, Definitions.c_DurationScore), Actions.run(new Runnable() {
            @Override
            public final void run() {
                figure.remove();
            }
        })));

        m_ScoreBest.setValue(String.valueOf(ScoreManager.getBestScore()));
        m_ScoreCurrent.setValue(String.valueOf(ScoreManager.getScore()));

        m_ScoreCurrent.update();
        m_ScoreBest.update();

        m_ScoreCurrent.setX(m_Rating.getX() - 20.f - m_ScoreCurrent.getWidth());

        addActor(figure);
        addActor(text);
    }
}
