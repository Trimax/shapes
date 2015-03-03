package com.gesoftware.figures.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gesoftware.figures.managers.AchievementsManager;
import com.gesoftware.figures.managers.AdManager;
import com.gesoftware.figures.managers.ThemesManager;
import com.gesoftware.figures.services.IServices;
import com.gesoftware.figures.services.IAdService;
import com.gesoftware.figures.RubyGame;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;

public class AndroidLauncher extends AndroidApplication implements IServices, IAdService, GameHelper.GameHelperListener {
    private final static String BANNER_ID = "ca-app-pub-5889738357994752/7949122628";
    private GameHelper gameHelper;

    private final static int REQUEST_ACHIEVEMENTS =1;

    private AdView m_ADView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_ALL);
        gameHelper.setConnectOnStart(true);
        gameHelper.enableDebugLog(true);

		final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;

		AchievementsManager.setPlayService(this);
        AdManager.setAdService(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        m_ADView = createAd();
        layout.addView(m_ADView);
        View gameView = createGameView(config);
        layout.addView(gameView);
        setContentView(layout);
        startAd();

        gameHelper.setup(this);
    }

    private AdView createAd() {
        final AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(BANNER_ID);
        adView.setId(IDFactory.generateViewID());

        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        adView.setLayoutParams(params);
        adView.setBackgroundColor(Color.WHITE);
        return adView;
    }

    @Override
    public void setBackgroundColor(final int color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                m_ADView.setBackgroundColor(color);
            }
        });
    }

    public void startAd() {
        final AdRequest adRequest = new AdRequest.Builder().build();
        m_ADView.loadAd(adRequest);
    }

    private View createGameView(final AndroidApplicationConfiguration cfg) {
        final View gameView = initializeForView(new RubyGame(), cfg);

        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, m_ADView.getId());
        gameView.setLayoutParams(params);
        return gameView;
    }

    @Override
    protected final void onStart() {
        super.onStart();

        gameHelper.onStart(this);
    }

    @Override
    protected final void onStop() {
        super.onStop();

        gameHelper.onStop();
    }

    @Override
    protected final void onPause() {
        if (m_ADView != null)
            m_ADView.pause();

        super.onPause();
    }

    @Override
    protected final void onResume() {
        super.onResume();

        if (m_ADView != null)
            m_ADView.resume();
    }

    @Override
    public final void onDestroy() {
        if (m_ADView != null)
            m_ADView.destroy();

        super.onDestroy();
    }

    @Override
    protected final void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        gameHelper.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public final void addScore(final String scoreName, final Integer value) {
        try {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(), scoreName, value);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final Integer getScore(final String scoreName) {
        return null;
    }

    @Override
    public final void unlockAchievement(final String achievementID) {
        try {
            Games.Achievements.unlock(gameHelper.getApiClient(), achievementID);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final boolean isAchievementUnlocked(final String achievementID) {
        return false;
    }

    @Override
    public final void onSignInFailed() {

    }

    @Override
    public final void onSignInSucceeded() {

    }

    @Override
    public final void signIn() {
        try {
            runOnUiThread(new Runnable() {
                public final void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (final Exception ignored) {}
    }

    @Override
    public final void showAchievements() {
        try {
            runOnUiThread(new Runnable() {
                public final void run() {
                    startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.mGoogleApiClient), REQUEST_ACHIEVEMENTS);
                }
            });
        } catch (final Exception ignored) {}
    }

    @Override
    public final void showLeaderboard() {
        try {
            runOnUiThread(new Runnable() {
                public final void run() {
                    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.mGoogleApiClient, AchievementsManager.c_ScoreID), REQUEST_ACHIEVEMENTS);
                }
            });
        } catch (final Exception ignored) {}
    }
}
