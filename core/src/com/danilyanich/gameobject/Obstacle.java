package com.danilyanich.gameobject;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.danilyanich.assets.GameAssetLoader;
import com.danilyanich.gameworld.DFRender;
import com.danilyanich.gameworld.Highscore;
import com.danilyanich.input.KindOfInput;

import java.util.Random;

public class Obstacle extends GameObject {
    public static int offset = 75;
    private Rectangle colliderHigh;
    private Rectangle colliderLow;
    private int lowtexNo, hightexNo;
    private float velocity;
    private float random_offset;
    private float gap;
    private float origin;
    private Vector2 sizelow;
    private boolean once = true;

    public Obstacle(Vector2 pos, float velocity, float random_offset, float origin, float gap, int lowtexNo, int hightexNo, boolean active) {

        super(pos, new Vector2(
                GameAssetLoader.high[hightexNo].getWidth(),
                GameAssetLoader.high[hightexNo].getHeight()
        ), active);
        this.sizelow = new Vector2(
                GameAssetLoader.low[lowtexNo].getWidth(),
                GameAssetLoader.low[lowtexNo].getHeight()
        );
        this.velocity = velocity;
        this.random_offset = random_offset;
        this.gap = gap;
        this.lowtexNo = lowtexNo;
        this.hightexNo = hightexNo;
        this.origin = origin;

        colliderHigh = new Rectangle(
                this.position.x - this.size.x / 2,
                this.position.y + this.size.y + gap / 2,
                20,
                82
        );
        colliderLow = new Rectangle(
                this.position.x - this.sizelow.x / 2,
                this.position.y - gap / 2,
                20,
                100
        );
    }

    @Override
    public void update(float delta) {
        if (this.position.x <= -offset) {
            Random r = new Random();
            this.position = new Vector2(
                    offset * 3,
                    origin + random_offset * (0.5f - r.nextFloat())
            );
            this.hightexNo = r.nextInt(GameAssetLoader.highC * 10) / 10;
            this.lowtexNo = r.nextInt(GameAssetLoader.lowC * 10) / 10;
        } else this.position.x += velocity * delta;
        if (position.x <= DFRender.GameSize.x / 2 && once) {
            GameAssetLoader.pilink.play();
            once = false;
            Highscore.add();
            Highscore.save();
        } else if (position.x > DFRender.GameSize.x / 2) once = true;

        colliderHigh.setX(this.position.x - 9);
        colliderHigh.setY(this.position.y + gap / 2 + 2);

        colliderLow.setX(this.position.x - 11);
        if (this.lowtexNo == 1)
            colliderLow.setY(this.position.y - this.sizelow.y - gap / 2 - 5);
        else
            colliderLow.setY(this.position.y - this.sizelow.y - gap / 2);
    }

    @Override
    public void Input(KindOfInput in, int screenX, int screenY, int pointer, int button) {

    }

    public int getLowtexNo() {
        return lowtexNo;
    }

    public int getHightexNo() {
        return hightexNo;
    }

    public float getGap() {
        return gap;
    }

    public Vector2 getSizelow() {
        return sizelow;
    }

    public Rectangle getColliderHigh() {
        return colliderHigh;
    }

    public Rectangle getColliderLow() {
        return colliderLow;
    }

}
