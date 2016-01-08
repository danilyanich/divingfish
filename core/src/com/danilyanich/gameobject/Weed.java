package com.danilyanich.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.danilyanich.input.KindOfInput;

public class Weed extends GameObject {
    private float Velocity;
    private float screensizeX;

    public Weed(Vector2 pos, Vector2 size, float velocity, float screensizeX, boolean active) {
        super(pos, size, active);
        this.Velocity = velocity;
        this.screensizeX = screensizeX;
    }

    @Override
    public void update(float delta) {
        if (this.position.x < (-1) * this.size.x)
            this.position = new Vector2(screensizeX, this.position.y);
        else this.position.x += Velocity * delta;
    }

    @Override
    public void Input(KindOfInput in, int screenX, int screenY, int pointer, int button) {

    }

    public float getVelocity() {
        return Velocity;
    }

    public void setVelocity(float velocity) {
        Velocity = velocity;
    }
}
