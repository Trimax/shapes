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
import com.gesoftware.figures.ui.Button;
import com.gesoftware.figures.ui.ClickableButton;
import com.gesoftware.figures.ui.ToggleButton;
import com.gesoftware.figures.utils.Utils;

public final class MenuScreen implements Screen {
    public static final int TABLE_CELL_PADDING = 6;

    private Stage m_Stage;
    private Table m_Table;
    private Image m_Rating;

    private ClickableButton m_ButtonPlayTime;
    private ClickableButton m_ButtonPlayMoves;
    private ClickableButton m_ButtonPlayInfinity;
    private ClickableButton m_ButtonAchievements;

    private ToggleButton m_ButtonTheme;
    private Button m_ButtonLeaderBoard;
    private Button m_ButtonRateApplication;

    public MenuScreen() {
        createHeader();
        createButtons();
        createTable();
    }

    private void createHeader() {
        float titleHeight = Gdx.graphics.getHeight() * .15f;
        final Texture ratingTexture = TexturesManager.getTexture("title.png");
        m_Rating = new Image(ratingTexture);
        m_Rating.setSize(ratingTexture.getWidth() / ratingTexture.getHeight() * titleHeight, titleHeight);
    }

    private void createButtons() {
        final int buttonSize       = (int) (Gdx.graphics.getWidth() * .3f);
        final int buttonSmallSize  = (int) (buttonSize * .5f);

        m_ButtonPlayTime     = new ClickableButton(buttonSize, buttonSize, Utils.getRGBColor(2, 191, 230), Utils.getRGBColor(2, 142, 180), TexturesManager.getTexture("time.png"), 0.5f);
        m_ButtonPlayMoves    = new ClickableButton(buttonSize, buttonSize, Utils.getRGBColor(255, 151, 64), Utils.getRGBColor(205, 107, 64), TexturesManager.getTexture("move.png"), 0.5f);
        m_ButtonPlayInfinity = new ClickableButton(buttonSize, buttonSize, Utils.getRGBColor(250, 102, 85), Utils.getRGBColor(199, 75, 59), TexturesManager.getTexture("infinity.png"), 0.5f);
        m_ButtonAchievements = new ClickableButton(buttonSize, buttonSize, Utils.getRGBColor(2, 204, 132), Utils.getRGBColor(218, 127, 29), TexturesManager.getTexture("achievements.png"));

        m_ButtonTheme = new ToggleButton(buttonSmallSize,
                                         buttonSmallSize,
                                         ThemesManager.getCurrentTheme().getColor(),
                                         Color.LIGHT_GRAY,
                                         ThemesManager.getCurrentTheme() == Theme.Dark,
                                         TexturesManager.getTexture("sun.png"),
                                         TexturesManager.getTexture("night.png")) {
            @Override
            public final void onChange(boolean enable) {
                ThemesManager.setCurrentTheme(enable ? Theme.Dark : Theme.Light);
                m_ButtonTheme.setInitialColor(ThemesManager.getCurrentTheme().getColor());
                AdManager.setBackgroundColor(ThemesManager.getCurrentTheme().getColor().toIntBits());
            }
        };

        m_ButtonRateApplication = new Button(buttonSmallSize, buttonSmallSize, TexturesManager.getTexture("star.png"), 0.7f);
        m_ButtonRateApplication.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=" + Definitions.c_AndroidId);
            }
        });

        m_ButtonLeaderBoard = new Button(buttonSmallSize, buttonSmallSize, TexturesManager.getTexture("rating.png"), 0.7f);
        m_ButtonLeaderBoard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (AchievementsManager.m_Service != null)
                        AchievementsManager.m_Service.showLeaderboard();
                } catch (final Throwable ignored) {
                }
            }
        });

        m_ButtonPlayTime.addListener(new ClickListener() {
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

        m_ButtonPlayMoves.addListener(new ClickListener() {
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

        m_ButtonPlayInfinity.addListener(new ClickListener() {
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

        m_ButtonAchievements.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (AchievementsManager.m_Service != null)
                        AchievementsManager.m_Service.showAchievements();
                } catch (final Throwable ignored) {
                }
            }
        });
    }

    private void createTable() {
        final Table footer = new Table();

        footer.add(m_ButtonTheme).pad(TABLE_CELL_PADDING).bottom().left();
        footer.add(m_ButtonLeaderBoard).pad(TABLE_CELL_PADDING).center();
        footer.add(m_ButtonRateApplication).pad(TABLE_CELL_PADDING).bottom().right();

        m_Table = new Table();
        m_Table.setFillParent(true);
        m_Table.add(Utils.wrapToGroup(m_Rating)).colspan(2).padTop(Definitions.c_HeaderSize / 4).expandY().top();
        m_Table.row();
        m_Table.add(Utils.wrapToGroup(m_ButtonPlayTime)).pad(TABLE_CELL_PADDING).right();
        m_Table.add(Utils.wrapToGroup(m_ButtonPlayMoves)).pad(TABLE_CELL_PADDING).left();
        m_Table.row();
        m_Table.add(Utils.wrapToGroup(m_ButtonPlayInfinity)).pad(TABLE_CELL_PADDING).expandY().top().right();
        m_Table.add(Utils.wrapToGroup(m_ButtonAchievements)).pad(TABLE_CELL_PADDING).expandY().top().left();
        m_Table.row();
        m_Table.add(footer).colspan(2).center().expandX().pad(TABLE_CELL_PADDING);
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
        final Action animateHeader = Actions.parallel(Utils.moveFrom(m_Rating,               m_Rating.getX(),          2 * Definitions.c_HeaderSize, Definitions.c_DurationScoreFade),
                                                      Utils.moveFrom(m_ButtonPlayTime,      -Definitions.c_SizeWindow, m_ButtonPlayTime.getY(),      Definitions.c_DurationMenuFade),
                                                      Utils.moveFrom(m_ButtonPlayMoves,     -Definitions.c_SizeWindow, m_ButtonPlayMoves.getY(),     Definitions.c_DurationMenuFade + 0.1f),
                                                      Utils.moveFrom(m_ButtonPlayInfinity,  -Definitions.c_SizeWindow, m_ButtonPlayInfinity.getY(),  Definitions.c_DurationMenuFade + 0.2f),
                                                      Utils.moveFrom(m_ButtonAchievements, -Definitions.c_SizeWindow, m_ButtonAchievements.getY(), Definitions.c_DurationMenuFade + 0.3f)
                                                      );
        m_Stage.addAction(animateHeader);
    }

    private void fadeOutAnimation(final Runnable onFinish) {
        final Action animateHeader = Actions.parallel(Utils.moveTo(m_Rating,              m_Rating.getX(),          2 * Definitions.c_HeaderSize, Definitions.c_DurationScoreFade),
                                                      Utils.moveTo(m_ButtonPlayTime,     -Definitions.c_SizeWindow, m_ButtonPlayTime.getY(),      Definitions.c_DurationMenuFade),
                                                      Utils.moveTo(m_ButtonPlayMoves,    -Definitions.c_SizeWindow, m_ButtonPlayMoves.getY(),     Definitions.c_DurationMenuFade + 0.1f),
                                                      Utils.moveTo(m_ButtonPlayInfinity, -Definitions.c_SizeWindow, m_ButtonPlayInfinity.getY(),  Definitions.c_DurationMenuFade + 0.2f),
                                                      Utils.moveTo(m_ButtonAchievements, -Definitions.c_SizeWindow, m_ButtonAchievements.getY(), Definitions.c_DurationMenuFade + 0.3f));

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
