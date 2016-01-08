package com.danilyanich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.danilyanich.assets.GameAssetLoader;
import com.danilyanich.constants.Constants;
import com.danilyanich.gameobject.EmptyGameObject;
import com.danilyanich.gameobject.GameObject;
import com.danilyanich.input.ActionInputListener;
import com.danilyanich.input.Handler;
import com.danilyanich.input.InputController;
import com.danilyanich.input.KindOfInput;
import com.danilyanich.tween.GameObjectTweenAccessor;
import com.danilyanich.ui.Button;
import com.danilyanich.ui.Ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class About implements Screen {
    GlyphLayout a, b, c;
    SpriteBatch batchHighRes;
    int X = Gdx.graphics.getWidth();
    int Y = Gdx.graphics.getHeight();
    Button exit;
    private TweenManager tweenManager = new TweenManager();
    private Sprite black = new Sprite(new Texture(Gdx.files.internal("sprites/Black.png")));;
    private EmptyGameObject opacity = new EmptyGameObject(new Vector2(1,0),new Vector2(0,0),true);



    public About() {
        OrthographicCamera HighResCamera;
        HighResCamera = new OrthographicCamera();
        HighResCamera.setToOrtho(false, X, Y);
        black.setPosition(0,0);
        black.setSize(X, Y);
        batchHighRes = new SpriteBatch();
        batchHighRes.setProjectionMatrix(HighResCamera.combined);
        GameAssetLoader.font.getData().setScale(X * 20f / (250f * 200f));
        a = new GlyphLayout(GameAssetLoader.font,"ABOUT");
        b = new GlyphLayout(GameAssetLoader.font,"Author");
        c = new GlyphLayout(GameAssetLoader.font,"Dan Krachkouski");
        GameObject arr[] = new GameObject[2];
        arr[0] = new InputController(
                new ActionInputListener() {
                    @Override
                    public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                        if(in == KindOfInput.keyDown && button == Input.Keys.BACK)
                            Tween.to(opacity, GameObjectTweenAccessor.POSITION_X,0.125f)
                                    .target(1f)
                                    .setCallback(new TweenCallback() {
                                        @Override
                                        public void onEvent(int type, BaseTween<?> source) {
                                            Constants.game.setScreen(new Menu());
                                        }
                                    })
                                    .start(tweenManager);
                    }
                },
                new Vector2(0,0),
                new Vector2(0,0),
                true
        );
        float coef = X / (250f);
        float size = 40*coef, offset = 8*coef;
        exit = new Button(
                new Vector2((X - size) / 2, Y - size - offset),
                new Vector2(size, size),
                Ui.exit,
                "exit",
                new ActionInputListener() {
                    @Override
                    public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                        Tween.to(opacity, GameObjectTweenAccessor.POSITION_X,0.125f)
                                .target(1f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        Constants.game.setScreen(new Menu());
                                    }
                                })
                                .start(tweenManager);
                    }
                },
                true
        );
        arr[1] = exit;
        Handler input = new Handler(arr);
        Gdx.input.setInputProcessor(input);
        Tween.to(opacity, GameObjectTweenAccessor.POSITION_X, 0.125f)
                .target(0f)
                .start(tweenManager);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(29f / 255f, 104 / 255f, 134 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        black.setAlpha(opacity.getPosition().x);
        batchHighRes.begin();
        GameAssetLoader.font.draw(batchHighRes, a, (X - a.width) / 2, Y - b.height*1.5f);
        GameAssetLoader.font.draw(batchHighRes, b, (X - b.width) / 2, Y/2 + b.height*1.2f);
        GameAssetLoader.font.draw(batchHighRes, c, (X - c.width) / 2, Y/2 - b.height*1.2f);
        batchHighRes.draw(
                GameAssetLoader.button[exit.getTextureNumber()],
                exit.getPosition().x,
                Y - exit.getPosition().y - exit.getSize().y,
                exit.getSize().x,
                exit.getSize().y
        );
        black.draw(batchHighRes);
        batchHighRes.end();
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
