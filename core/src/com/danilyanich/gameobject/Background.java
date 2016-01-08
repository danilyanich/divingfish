package com.danilyanich.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.danilyanich.input.KindOfInput;

public class Background extends GameObject {
    private float Velocity;
    //private float previousX;

    public Background(Vector2 pos, Vector2 size, float velocity, boolean active) {
        super(pos, size, active);
        this.Velocity = velocity;
    }

    @Override
    public void update(float delta) {
        //if (previousX < this.position.x && previousX > (-1) * this.size.x)
        //    this.position = new Vector2(previousX + this.size.x - 2, 0);
        if (this.position.x < (-1) * this.size.x)
            this.position = new Vector2(this.size.x + Velocity * 2 * delta, 0);
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

    //public void setPreviousX(float previousX) {
    //    this.previousX = previousX;
   // }
}
