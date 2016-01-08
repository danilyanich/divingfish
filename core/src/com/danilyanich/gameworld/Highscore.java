package com.danilyanich.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.danilyanich.constants.Constants;

public class Highscore {
    private static int session;
    private static Preferences prefs = Gdx.app.getPreferences("score");
    private static int highscore;

    public static void load() {
        session = 0;
        highscore = prefs.getInteger("highscore", 0);
    }

    public static void add() {
        session++;
        if (session >= highscore) {
            highscore = session;
            save();
        }
    }

    public static void save() {
        prefs.putInteger("highscore", highscore);
        if (Constants.actionResolver.getSignedInGPGS())
            Constants.actionResolver.submitScoreGPGS(highscore);
        prefs.flush();
    }

    public static int getSession() {
        return session;
    }

    public static int getHighscore() {
        return highscore;
    }

}
