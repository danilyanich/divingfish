package com.danilyanich.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.danilyanich.input.KindOfInput;

public abstract class GameObject {
    public boolean active;
    protected Vector2 position;
    protected Vector2 size;
    protected float rotation;

    public GameObject(Vector2 pos, Vector2 size, boolean active) {
        this.position = pos;
        this.size = size;
        this.active = active;
        rotation = 0f;
    }

    public abstract void update(float delta);

    public abstract void Input(KindOfInput in, int screenX, int screenY, int pointer, int button);

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
