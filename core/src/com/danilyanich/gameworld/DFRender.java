package com.danilyanich.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.danilyanich.assets.GameAssetLoader;
import com.danilyanich.gameobject.EmptyGameObject;
import com.danilyanich.gameobject.Fish;
import com.danilyanich.tween.GameObjectTweenAccessor;
import com.danilyanich.ui.Ui;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class DFRender {
    public static Vector2 GameSize = new Vector2(80, 100);
    float BG = GameAssetLoader.background.getHeight();
    private DFWorld World;
    private float offset;
    private SpriteBatch batch, batchHighRes;
    private final GlyphLayout TAP;
    private GlyphLayout SCORE = new GlyphLayout();
    float coef = GameAssetLoader.font.getScaleX() * 200;

    private Sprite black;
    public DFRender(DFWorld world) {
        OrthographicCamera camera;
        this.World = world;
        float coef = GameSize.x / world.getScreenSize().x;
        offset = (world.getScreenSize().y * coef - GameSize.y) / 2f;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSize.x, GameSize.y + 2 * offset);
        black = new Sprite(new Texture(Gdx.files.internal("sprites/Black.png")));
        black.setSize(GameSize.x, GameSize.y + 2 * offset);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        world.setFish(new Fish(
                        new Vector2(GameSize.x / 2 - 8, GameSize.y / 2 - 5), //pos
                        new Vector2(16, 10), //size
                        new Vector2(0, 230), //acceleration
                        -100, //jump
                        100, //max_v
                        true
                )
        );
        OrthographicCamera HighResCamera;
        HighResCamera = new OrthographicCamera();
        HighResCamera.setToOrtho(false, world.getScreenSize().x, world.getScreenSize().y);
        batchHighRes = new SpriteBatch();
        batchHighRes.setProjectionMatrix(HighResCamera.combined);
        GameAssetLoader.font.getData().setScale(World.getScreenSize().x * 40f / (250f * 200f));
        TAP = new GlyphLayout(GameAssetLoader.font,"TAP");
    }

    public void update(float delta) {
//low-res-draw--------------------------------------------------------------------------------------
        //Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        black.setAlpha(World.getOpacity().getPosition().x);
        batch.begin();
        for (int i = 0; i < 2; i++)
            batch.draw(
                    GameAssetLoader.background,
                    World.getBackground()[i].getPosition().x,
                    offset + (DFRender.GameSize.y - BG) / 2
            );
        batch.draw(
                GameAssetLoader.fish,
                World.getFish().getPosition().x,
                World.getFish().getPosition().y + offset,
                8,
                5,
                16,
                10,
                1,
                1,
                World.getFish().getRotation(),
                0,
                0,
                16,
                10,
                false,
                false
        );
        for (int i = 0; i < 4; i++) {
            batch.draw(
                    GameAssetLoader.high[World.getObstacle()[i].getHightexNo()],
                    World.getObstacle()[i].getPosition().x - World.getObstacle()[i].getSize().x / 2,
                    World.getObstacle()[i].getPosition().y + World.getObstacle()[i].getGap() / 2 + offset);
            batch.draw(
                    GameAssetLoader.low[World.getObstacle()[i].getLowtexNo()],
                    World.getObstacle()[i].getPosition().x - World.getObstacle()[i].getSizelow().x / 2,
                    World.getObstacle()[i].getPosition().y - World.getObstacle()[i].getSizelow().y - World.getObstacle()[i].getGap() / 2 + offset);
        }
        for (int i = 0; i < 4; i++)
            batch.draw(GameAssetLoader.weed[0], World.getWeed()[i].getPosition().x, World.getWeed()[i].getPosition().y);
        batch.end();
//high-res-draw-------------------------------------------------------------------------------------
        batchHighRes.begin();
        if (World.state != DFWorld.GameState.Ready) {
            String score = "" + Highscore.getSession();
            SCORE.setText(GameAssetLoader.font,score);
            GameAssetLoader.font.draw(
                    batchHighRes,
                    SCORE,
                    coef*8/40,
                    World.getScreenSize().y - coef*8/40
            );
            for (int i = 0; i < World.getButtons().length; i++)
                if (World.getButtons()[i].active)
                    batchHighRes.draw(
                            GameAssetLoader.button[World.getButtons()[i].getTextureNumber()],
                            World.getButtons()[i].getPosition().x,
                            World.getScreenSize().y - World.getButtons()[i].getPosition().y - World.getButtons()[i].getSize().y,
                            World.getButtons()[i].getSize().x,
                            World.getButtons()[i].getSize().y
                    );
        }
        if (World.state == DFWorld.GameState.Ready) {
            float c = World.getScreenSize().x / 250;
            batchHighRes.draw(
                    GameAssetLoader.button[Ui.fish.ordinal()],
                    (World.getScreenSize().x - World.getController().getSize().x)/2,
                    (World.getScreenSize().y - World.getController().getSize().y)/2,
                    World.getController().getSize().x,
                    World.getController().getSize().y
            );
            GameAssetLoader.font.draw(
                    batchHighRes,
                    TAP,
                    ((World.getScreenSize().x) - TAP.width)/2,
                    ((World.getScreenSize().y) - c*90)/2
            );
        }
        batchHighRes.end();
        batch.begin();
        black.draw(batch);
        batch.end();
    }
}
