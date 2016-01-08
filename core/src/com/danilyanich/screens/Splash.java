package com.danilyanich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.danilyanich.assets.GameAssetLoader;
import com.danilyanich.constants.Constants;
import com.danilyanich.gameobject.EmptyGameObject;
import com.danilyanich.gameworld.DFRender;
import com.danilyanich.tween.GameObjectTweenAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

public class Splash implements Screen {
    private SpriteBatch batch;
    private Texture splash;
    private Sprite black;
    private float offset;
    private EmptyGameObject opacity = new EmptyGameObject(new Vector2(1,0),new Vector2(0,0),true);
    TweenManager tweenManager;

    public Splash() {
        int X = Gdx.graphics.getWidth();
        int Y = Gdx.graphics.getHeight();
        float coef = DFRender.GameSize.x / X;
        offset = (Y * coef - DFRender.GameSize.y) / 2f;
        splash = new Texture(Gdx.files.internal("sprites/splash.png"));
        black = new Sprite(new Texture(Gdx.files.internal("sprites/Black.png")));
        black.setPosition(0,0);
        black.setSize(DFRender.GameSize.x, DFRender.GameSize.y + 2 * offset);
        OrthographicCamera camera;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, DFRender.GameSize.x, DFRender.GameSize.y + 2 * offset);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        GameAssetLoader.Load();
        batch.enableBlending();
    }

    @Override
    public void show() {
        tweenManager = new TweenManager();
        Tween.set(opacity,GameObjectTweenAccessor.POSITION_X).target(1f).start(tweenManager);
        Timeline.createSequence().beginSequence()
                .push(Tween.to(opacity,  GameObjectTweenAccessor.POSITION_X, 1f).target(0f).ease(Quad.INOUT))
                .pushPause(1f)
                .push(Tween.to(opacity,  GameObjectTweenAccessor.POSITION_X, 0.125f).target(1f).ease(Quad.INOUT))
                .setCallback(new TweenCallback() {
                                 @Override
                                 public void onEvent(int type, BaseTween<?> source) {
                                     Constants.game.setScreen(new Menu());
                                 }
                             }
                ).end()
                .start(tweenManager);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(
                splash,
                0,
                offset + DFRender.GameSize.y / 2 - splash.getHeight() / 2
        );
        black.setAlpha(opacity.getPosition().x);
        black.draw(batch);
        batch.end();
        tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
