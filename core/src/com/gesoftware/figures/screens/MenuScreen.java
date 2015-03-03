package com.gesoftware.figures.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gesoftware.figures.Definitions;
import com.gesoftware.figures.enums.Theme;
import com.gesoftware.figures.managers.*;
import com.gesoftware.figures.model.Text;
import com.gesoftware.figures.ui.Button;
import com.gesoftware.figures.ui.ClickableButton;
import com.gesoftware.figures.ui.ToggleButton;
import com.gesoftware.figures.utils.Utils;

public final class MenuScreen implements Screen {
    public static final int TABLE_CELL_PADDING = 6;

    private Stage m_Stage;
    private Table m_Table;
    private Image m_Rating;
    private Text m_ScoreBest;
    private ClickableButton m_PlayButton;
    private ClickableButton m_AchievementsButton;
    private ClickableButton m_LeaderBoardButton;
    private ToggleButton m_ChangeThemeButton;
    private Button m_RateButton;

    public MenuScreen() {
        createHeader();
        createButtons();
        createTable();
    }

    private void createHeader() {
        final Texture ratingTexture = TexturesManager.getTexture("rating.png");
        m_Rating = new Image(ratingTexture);
        m_Rating.setSize(Definitions.c_HeaderSize, Definitions.c_HeaderSize);

        m_ScoreBest = new Text(String.valueOf(ScoreManager.getBestScore()), FontsManager.getLabelBestScoreFont(), m_Rating.getX(), m_Rating.getY());
        m_ScoreBest.update();
    }

    private void createButtons() {
        final int buttonWidth  = (int) (Gdx.graphics.getWidth() * .5f);
        final int buttonHeight = (int) (Gdx.graphics.getWidth() * .2f);

        m_PlayButton         = new ClickableButton(buttonWidth, buttonHeight, Utils.getRGBColor(152, 220, 85), Utils.getRGBColor(126, 181, 72), TexturesManager.getTexture("play.png"), 0.3f);
        m_AchievementsButton = new ClickableButton((int)(buttonWidth * 0.5f) - TABLE_CELL_PADDING, buttonHeight, Utils.getRGBColor(237, 149, 74), Utils.getRGBColor(218, 127, 29), TexturesManager.getTexture("achievements.png"));
        m_LeaderBoardButton  = new ClickableButton((int)(buttonWidth * 0.5f) - TABLE_CELL_PADDING, buttonHeight, Utils.getRGBColor(126, 142, 213), Utils.getRGBColor(104, 117, 176), TexturesManager.getTexture("leaderboard.png"));
        m_ChangeThemeButton  = new ToggleButton((int)(buttonWidth * 0.3f),
                                                (int)(buttonWidth * 0.3f),
                                                ThemesManager.getCurrentTheme().getColor(),
                                                Color.LIGHT_GRAY,
                                                ThemesManager.getCurrentTheme() == Theme.Dark,
                                                TexturesManager.getTexture("sun.png"),
                                                TexturesManager.getTexture("night.png")) {
            @Override
            public void onChange(boolean enable) {
                ThemesManager.setCurrentTheme(enable ? Theme.Dark : Theme.Light);
                m_ChangeThemeButton.setInitialColor(ThemesManager.getCurrentTheme().getColor());
                AdManager.setBackgroundColor(ThemesManager.getCurrentTheme().getColor().toIntBits());
            }
        };

        m_RateButton  = new Button((int)(buttonWidth * 0.3f), (int)(buttonWidth * 0.3f), TexturesManager.getTexture("star.png"), 0.7f);
        m_RateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=" + Definitions.c_AndroidId);
            }
        });

        m_PlayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fadeOutAnimation(new Runnable() {
                    @Override
                    public final void run() {
                        DataManager.m_RubyGame.setScreen(new RubyScreen());
                    }
                });
            }
        });

        m_AchievementsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (AchievementsManager.m_Service != null)
                        AchievementsManager.m_Service.showAchievements();
                } catch (final Throwable ignored) {}
            }
        });

        m_LeaderBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (AchievementsManager.m_Service != null)
                        AchievementsManager.m_Service.showLeaderboard();
                } catch (final Throwable ignored) {}
            }
        });
    }

    private void createTable() {
        m_Table = new Table();
        m_Table.setFillParent(true);
        m_Table.add(Utils.wrapToGroup(m_Rating)).colspan(2).height(Definitions.c_HeaderSize).width(Definitions.c_HeaderSize).padTop(Definitions.c_HeaderSize / 4);
        m_Table.row();
        m_Table.add(Utils.wrapToGroup(m_ScoreBest)).colspan(2).height(m_ScoreBest.getHeight() / 4).expandY().top();
        m_Table.row();
        m_Table.add(Utils.wrapToGroup(m_PlayButton)).colspan(2).pad(TABLE_CELL_PADDING);
        m_Table.row();
        m_Table.add(Utils.wrapToGroup(m_AchievementsButton)).pad(TABLE_CELL_PADDING).expandY().top().right();
        m_Table.add(Utils.wrapToGroup(m_LeaderBoardButton)).pad(TABLE_CELL_PADDING).expandY().top().left();
        m_Table.row();
        m_Table.add(m_ChangeThemeButton).expandX().pad(TABLE_CELL_PADDING).bottom().left();
        m_Table.add(m_RateButton).expandX().pad(TABLE_CELL_PADDING).bottom().right();
    }

    @Override
    public final void show() {
        m_Stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(m_Stage);
        Gdx.input.setCatchBackKey(false);
        m_Stage.addActor(m_Table);

        fadeInAnimation();
    }

    private void fadeInAnimation() {
        final Action animateHeader = Actions.parallel(Utils.moveFrom(m_Rating,             m_Rating.getX(),           2 * Definitions.c_HeaderSize,                           Definitions.c_DurationScoreFade),
                                                      Utils.moveFrom(m_ScoreBest,          m_ScoreBest.getX(),        2 * Definitions.c_HeaderSize + m_ScoreBest.getHeight(), Definitions.c_DurationScoreFade),
                                                      Utils.moveFrom(m_PlayButton,         -Definitions.c_SizeWindow, m_PlayButton.getY(),                                    Definitions.c_DurationMenuFade),
                                                      Utils.moveFrom(m_AchievementsButton, -Definitions.c_SizeWindow, m_AchievementsButton.getY(),                            Definitions.c_DurationMenuFade + 0.1f),
                                                      Utils.moveFrom(m_LeaderBoardButton,  -Definitions.c_SizeWindow, m_LeaderBoardButton.getY(),                             Definitions.c_DurationMenuFade + 0.1f)
                                                      );
        m_Stage.addAction(animateHeader);
    }

    private void fadeOutAnimation(final Runnable onFinish) {
        final Action animateHeader = Actions.parallel(Utils.moveTo(m_Rating,               m_Rating.getX(),           2 * Definitions.c_HeaderSize,                               Definitions.c_DurationScoreFade),
                                                      Utils.moveTo(m_ScoreBest,            m_ScoreBest.getX(),        2 * Definitions.c_HeaderSize + m_ScoreBest.getHeight(), Definitions.c_DurationScoreFade),
                                                      Utils.moveTo(m_PlayButton,           -Definitions.c_SizeWindow, m_PlayButton.getY(),                                    Definitions.c_DurationMenuFade),
                                                      Utils.moveTo(m_AchievementsButton,   -Definitions.c_SizeWindow, m_AchievementsButton.getY(),                            Definitions.c_DurationMenuFade + 0.1f),
                                                      Utils.moveTo(m_LeaderBoardButton,    -Definitions.c_SizeWindow, m_LeaderBoardButton.getY(),                             Definitions.c_DurationMenuFade + 0.1f));

        m_Stage.addAction(Actions.sequence(animateHeader, Actions.run(onFinish)));
    }

    @Override
    public final void render(float delta) {
        Color background = ThemesManager.getCurrentTheme().getColor();
        Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        m_Stage.act(Gdx.graphics.getDeltaTime());
        m_Stage.draw();
    }

    @Override
    public final void resize(int width, int height) {
        m_Stage.getViewport().update(width, height, true);
    }

    @Override
    public final void pause() {

    }

    @Override
    public final void resume() {
    }

    @Override
    public final void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public final void dispose() {
        m_Stage.dispose();
    }
}
