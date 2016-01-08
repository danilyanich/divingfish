package com.danilyanich.input;

import com.badlogic.gdx.math.Vector2;
import com.danilyanich.constants.Constants;
import com.danilyanich.gameobject.GameObject;

public class InputController extends GameObject {

    ActionInputListener action;

    public InputController(ActionInputListener action, Vector2 pos, Vector2 size, boolean active) {
        super(pos, size, active);
        this.action = action;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void Input(KindOfInput in, int screenX, int screenY, int pointer, int button) {
        action.action(Constants.game.getScreen(), in, screenX,screenY, pointer, button);
    }
}
