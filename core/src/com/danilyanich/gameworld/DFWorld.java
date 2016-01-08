package com.danilyanich.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.danilyanich.constants.Constants;
import com.danilyanich.gameobject.Background;
import com.danilyanich.gameobject.EmptyGameObject;
import com.danilyanich.gameobject.Fish;
import com.danilyanich.gameobject.GameObject;
import com.danilyanich.gameobject.Obstacle;
import com.danilyanich.gameobject.Weed;
import com.danilyanich.input.ActionInputListener;
import com.danilyanich.input.Handler;
import com.danilyanich.input.InputController;
import com.danilyanich.input.KindOfInput;
import com.danilyanich.screens.MainScreen;
import com.danilyanich.screens.Menu;
import com.danilyanich.tween.GameObjectTweenAccessor;
import com.danilyanich.ui.Button;
import com.danilyanich.ui.Toggle;
import com.danilyanich.ui.Ui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Quad;

public class DFWorld {
    public GameState state;
    //private static Float ButtonOffset[] = new Float[2];
    private Vector2 ScreenSize;
    private Fish fish;
    private Background background[] = new Background[2];
    private Weed weed[] = new Weed[4];
    private Obstacle obstacle[] = new Obstacle[4];
    private float runtime = 0;
    private InputController controller;
    private Button buttons[] = new Button[3];
    private TweenManager tweenManager;
    private EmptyGameObject opacity = new EmptyGameObject(new Vector2(1,0),new Vector2(0,0),true);

    public DFWorld(int screen_size_x, int screen_size_y) {
//buttons-------------------------------------------------------------------------------------------
        this.ScreenSize = new Vector2(screen_size_x, screen_size_y);
        Constants.Button.coef = screen_size_x / (250f);
        Constants.Button.size = 40f * Constants.Button.coef;
        Constants.Button.offset = 8f * Constants.Button.coef;
        final TweenEquation equation = Cubic.INOUT;
        //ButtonOffset[0] = 0f;
        //ButtonOffset[1] = 0f;
        buttons[0] = new Toggle(
                new Vector2(screen_size_x - (Constants.Button.size + Constants.Button.offset), Constants.Button.offset),
                new Vector2(Constants.Button.size,Constants.Button.size),
                Ui.play,
                Ui.pause,
                "pause",
                new ActionInputListener() {
                    @Override
                    public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                        MainScreen sc = (MainScreen) screen;
                        sc.getWorld().state = GameState.Play;
                        TweenCallback cb = new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                MainScreen sc = (MainScreen) Constants.game.getScreen();
                                for (int i = 1; i < sc.getWorld().getButtons().length; i++)
                                    sc.getWorld().getButtons()[i].active = false;
                            }
                        };
                        Tween.set(buttons[1], GameObjectTweenAccessor.POSITION_Y).target(Constants.Button.offset);
                        Tween.set(buttons[2], GameObjectTweenAccessor.POSITION_Y).target(Constants.Button.offset);
                        Timeline.createParallel()
                                .push(Tween.to(buttons[2], GameObjectTweenAccessor.POSITION_Y,0.25f).target(-1f * Constants.Button.size).ease(equation))
                                .push(Tween.to(buttons[1], GameObjectTweenAccessor.POSITION_Y, 0.5f).target(-1f * Constants.Button.size).ease(equation))
                                .setCallback(cb)
                                .start(tweenManager);
                    }
                },
                new ActionInputListener() {
                    @Override
                    public void action(final Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                        MainScreen sc = (MainScreen) screen;
                        sc.getWorld().state = GameState.Pause;
                        for (int i = 1; i < sc.getWorld().getButtons().length; i++)
                            sc.getWorld().getButtons()[i].active = true;
                        Tween.set(buttons[1], GameObjectTweenAccessor.POSITION_Y).target(-1f * Constants.Button.size);
                        Tween.set(buttons[2], GameObjectTweenAccessor.POSITION_Y).target(-1f * Constants.Button.size);
                        Timeline.createParallel()
                                .push(Tween.to(buttons[1], GameObjectTweenAccessor.POSITION_Y,0.25f).target(Constants.Button.offset).ease(equation))
                                .push(Tween.to(buttons[2], GameObjectTweenAccessor.POSITION_Y, 0.5f).target(Constants.Button.offset).ease(equation))
                                .start(tweenManager);
                    }
                },
                false,
                true
        );
        buttons[1] = new Button(
                new Vector2(screen_size_x - 2 * (Constants.Button.size + Constants.Button.offset), -1 * Constants.Button.size),
                new Vector2( Constants.Button.size,Constants.Button.size),
                Ui.menu,
                "menu",
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
                false
        );
        buttons[2] = new Button(
                new Vector2(screen_size_x - 3 * (Constants.Button.size + Constants.Button.offset), -1 * Constants.Button.size),
                new Vector2(Constants.Button.size, Constants.Button.size),
                Ui.restart,
                "restart",
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
                false
        );
//game-objects--------------------------------------------------------------------------------------
        state = GameState.Ready;
        float c = ScreenSize.x / 250f;
        controller = new InputController(new ActionInputListener() {
            @Override
            public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                if(in == KindOfInput.touchDown) {
                    MainScreen ms = (MainScreen) screen;
                    ms.getWorld().state = DFWorld.GameState.Play;
                    GameObject arr[] = new GameObject[ms.getWorld().getButtons().length + 2];
                    System.arraycopy(ms.getWorld().getButtons(), 0, arr, 0, ms.getWorld().getButtons().length);
                    arr[ms.getWorld().getButtons().length] = ms.getWorld().getFish();
                    arr[ms.getWorld().getButtons().length + 1] = new InputController(new ActionInputListener() {
                        @Override
                        public void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button) {
                            if(in == KindOfInput.keyDown && button == Input.Keys.BACK)
                            {
                                MainScreen ms = (MainScreen)screen;
                                Toggle t = (Toggle) ms.getWorld().getButtons()[0];
                                t.Toggle();
                            }
                        }
                    }, new Vector2(0, 0), new Vector2(0, 0), true);
                    Handler input = new Handler(arr);
                    Gdx.input.setInputProcessor(input);
                }
            }
        }, new Vector2(0,0), new Vector2(c * 80, c * 80), true);
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

        float Woffset = 90;
        weed[0] = new Weed(
                new Vector2(DFRender.GameSize.x + 10, -40),
                new Vector2(25, 108),
                -70,
                DFRender.GameSize.x + Woffset,
                true
        );
        weed[1] = new Weed(
                new Vector2(DFRender.GameSize.x + 65, -30),
                new Vector2(25, 108),
                -70,
                DFRender.GameSize.x + Woffset,
                true
        );
        weed[2] = new Weed(
                new Vector2(DFRender.GameSize.x + 110, -50),
                new Vector2(25, 108),
                -70,
                DFRender.GameSize.x + Woffset,
                true
        );
        weed[3] = new Weed(
                new Vector2(DFRender.GameSize.x + 160, -10),
                new Vector2(25, 108),
                -70,
                DFRender.GameSize.x + Woffset,
                true
        );
        for (int i = 0; i < 4; i++)
            obstacle[i] = new Obstacle(
                    new Vector2(Obstacle.offset * (i + 3), 50), //pos
                    -60, //velocity
                    40, //offset
                    50, //origin
                    40, //gap
                    0,
                    0,
                    true
            );
        tweenManager = new TweenManager();
        Timeline.createParallel()
                .push(
                        Tween.from(controller, GameObjectTweenAccessor.SIZE_XY, 0.6f)
                                .target(c * 800, c * 800)
                                .ease(Quad.OUT)
                ).push(
                Tween.to(opacity, GameObjectTweenAccessor.POSITION_X, 0.125f)
                        .target(0f)
        ).start(tweenManager);
    }
    public void update(float delta) {
        runtime += delta;
        if (state != GameState.Pause) {
            for (int i = 0; i < 2; i++)
                background[i].update(delta);
            for (int i = 0; i < 4; i++)
                weed[i].update(delta);
            if (state != GameState.Ready) {
                fish.update(delta);
                for (int i = 0; i < 4; i++)
                    obstacle[i].update(delta);
            }
            if (state != GameState.Over) {
                for (int i = 0; i < 4; i++)
                    if (Intersector.overlaps(
                            fish.getCollider(),
                            obstacle[i].getColliderHigh()) || Intersector.overlaps(fish.getCollider(),
                            obstacle[i].getColliderLow()
                    )) fish.isDead = true;
                if (fish.getPosition().y >= 2f * DFRender.GameSize.y || fish.getPosition().y <= -DFRender.GameSize.y/2)
                    fish.isDead = true;
                if (fish.isDead) {
                    state = GameState.Over;
                    Constants.game.setScreen(new MainScreen());
                }
            }
        }
        tweenManager.update(delta);
    }

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    public Vector2 getScreenSize() {
        return ScreenSize;
    }

    public float getRuntime() {
        return runtime;
    }

    public GameObject[] getBackground() {
        return background;
    }

    public GameObject[] getWeed() {
        return weed;
    }

    public Obstacle[] getObstacle() {
        return obstacle;
    }

    public InputController getController() {
        return controller;
    }

    public Button[] getButtons() {
        return buttons;
    }

    public TweenManager getTweenManager() {
        return tweenManager;
    }

    public EmptyGameObject getOpacity() {
        return opacity;
    }

    public enum GameState {
        Pause,
        Play,
        Ready,
        Over
    }

}