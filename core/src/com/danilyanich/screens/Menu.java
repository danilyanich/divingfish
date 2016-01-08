package com.danilyanich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.danilyanich.assets.GameAssetLoader;
import com.danilyanich.constants.Constants;
import com.danilyanich.gameobject.Background;
import com.danilyanich.gameobject.EmptyGameObject;
import com.danilyanich.gameworld.DFRender;
import com.danilyanich.gameworld.Highscore;
import com.danilyanich.input.ActionInputListener;
import com.danilyanich.input.Handler;
import com.danilyanich.input.KindOfInput;
import com.danilyanich.tween.GameObjectTweenAccessor;
import com.danilyanich.ui.Button;
import com.danilyanich.ui.Toggle;
import com.danilyanich.ui.Ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class Menu implements Screen {
    public static Vector2 GameSize = new Vector2(80, 100);
    float BG = GameAssetLoader.background.getHeight();
    private float offset;
    private SpriteBatch batch, batchHighRes;
    private Background background[] = new Background[2];
    private Button buttons[] = new Button[5];
    private TweenManager tweenManager;
    private Sprite black;
    private EmptyGameObject opacity = new EmptyGameObject(new Vector2(1,0),new Vector2(0,0),true);
    private GlyphLayout highscore;

    int X;
    int Y;
    public Menu() {
        OrthographicCamera camera;
        X = Gdx.graphics.getWidth();
        Y = Gdx.graphics.getHeight();
        float coef = GameSize.x /Gdx.graphics.getWidth();
        offset = (Gdx.graphics.getHeight() * coef - GameSize.y) / 2f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSize.x, GameSize.y + 2 * offset);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        OrthographicCamera HighResCamera;
        HighResCamera = new OrthographicCamera();
        HighResCamera.setToOrtho(false, X, Y);
        batchHighRes = new SpriteBatch();
        batchHighRes.setProjectionMatrix(HighResCamera.combined);
        batchHighRes.enableBlending();
        Highscore.load();
        GameAssetLoader.font.getData().setScale(X * 20f / (250f * 200f));
        highscore= new GlyphLayout();
        highscore.setText(GameAssetLoader.font,"HIGHSCORE: " + Highscore.getHighscore());
        black = new Sprite(new Texture(Gdx.files.internal("sprites/Black.png")));
        black.setPosition(0, 0);
        black.setSize(DFRender.GameSize.x, DFRender.GameSize.y + 2 * offset);
        background[0] = new Background(
                new Vector2(0, 0),
                new Vector2(200, 100),
                -50,
                true
        );
        background[1] = new Background(
                new Vector2(200, 0),
                new Vector2(200, 100),
                -50,
                true
        );
        coef = X / (250f);
        float size = 40*coef, offset = 8*coef;
        buttons[0] = new Button(
                new Vector2((X-size*1.5f)/2,(Y-size*1.5f)/2),
                new Vector2(size*1.5f, size*1.5f),
                Ui.play,
                "play",
                new ActionInputListener() {
                    @Override
                    public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                        Tween.to(opacity, GameObjectTweenAccessor.POSITION_X,0.125f)
                                .target(1f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        Constants.game.setScreen(new MainScreen());
                                    }
                                })
                                .start(tweenManager);
                    }
                },
                true
        );
        buttons[1] = new Button(
                new Vector2(X/2 - offset/2 - size,(Y - size*1.5f)/2 + (size + 3*offset)),
                new Vector2(size,size),
                Ui.highscore,
                "google play",
                new ActionInputListener() {
                    @Override
                    public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                        if (Constants.actionResolver.getSignedInGPGS())
                        Constants.actionResolver.getLeaderboardGPGS();
                        else Constants.actionResolver.loginGPGS();
                    }
                },
                true
        );
        buttons[2] = new Button(
                new Vector2(X/2 + offset/2,(Y - size*1.5f)/2 + (size + 3*offset)),
                new Vector2(size,size),
                Ui.about,
                "about",
                new ActionInputListener() {
                    @Override
                    public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {

                        Tween.to(opacity, GameObjectTweenAccessor.POSITION_X,0.125f)
                                .target(1f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        Constants.game.setScreen(new About());
                                    }
                                })
                                .start(tweenManager);
                    }
                },
                true
        );
        buttons[3] = new Toggle(
                new Vector2(X/2 - offset/2 - size,(Y - size*1.5f)/2 + 2*(size + 2f*offset)),
                new Vector2(size,size),
                Ui.sound,
                Ui.soundScheck,
                "sound",
                new ActionInputListener() {
                    @Override
                    public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                    }
                },
                new ActionInputListener() {
                    @Override
                    public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                    }
                },
                true,
                true
        );
        buttons[4] = new Button(
                new Vector2(X/2 + offset/2,(Y - size*1.5f)/2 + 2*(size + 2f*offset)),
                new Vector2(size,size),
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
                                        Gdx.app.exit();
                                    }
                                })
                                .start(tweenManager);
                    }
                },
                true
        );
        Handler input = new Handler(buttons);
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void show() {
        tweenManager = new TweenManager();
        Tween.to(opacity, GameObjectTweenAccessor.POSITION_X,0.125f)
                .target(0f)
                .start(tweenManager);
    }

    @Override
    public void render(float delta) {
        for (int i = 0; i < 2; i++)
            background[i].update(delta);
        batch.begin();
        for (int i = 0; i < 2; i++)
            batch.draw(
                    GameAssetLoader.background,
                    background[i].getPosition().x,
                    offset + (DFRender.GameSize.y - BG) / 2
            );
        batch.end();
        batchHighRes.begin();
        for (Button button : buttons)
            if (button.active)
                batchHighRes.draw(
                        GameAssetLoader.button[button.getTextureNumber()],
                        button.getPosition().x,
                        Y - button.getPosition().y - button.getSize().y,
                        button.getSize().x,
                        button.getSize().y
                );
        GameAssetLoader.font.draw(
                batchHighRes,
                highscore,
                (X - highscore.width)/2f,
                buttons[0].getPosition().y + buttons[0].getSize().y*1.5f
        );
        batchHighRes.end();
        black.setAlpha(opacity.getPosition().x);
        batch.begin();
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
