package com.danilyanich.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.danilyanich.input.KindOfInput;

public class EmptyGameObject extends GameObject {

    public EmptyGameObject(Vector2 pos, Vector2 size, boolean active) {
        super(pos, size, active);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void Input(KindOfInput in, int screenX, int screenY, int pointer, int button) {

    }
}
