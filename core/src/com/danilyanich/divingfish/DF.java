package com.danilyanich.divingfish;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.danilyanich.assets.GameAssetLoader;
import com.danilyanich.constants.Constants;
import com.danilyanich.gameobject.GameObject;
import com.danilyanich.gameworld.Highscore;
import com.danilyanich.screens.Splash;
import com.danilyanich.tween.GameObjectTweenAccessor;

import aurelienribon.tweenengine.Tween;

public class DF extends Game {


    public DF(ActionResolver actionResolver)
    {
        Constants.actionResolver = actionResolver;
    }
    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);
        Constants.game = this;
        Tween.registerAccessor(GameObject.class, new GameObjectTweenAccessor());
        Highscore.load();
        setScreen(new Splash());
    }

    @Override
    public void dispose() {
        GameAssetLoader.dispose();
        super.dispose();
    }
}
