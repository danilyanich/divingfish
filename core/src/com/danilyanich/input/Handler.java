package com.danilyanich.input;

import com.badlogic.gdx.InputProcessor;
import com.danilyanich.gameobject.GameObject;

public class Handler implements InputProcessor {
    private GameObject object[];

    public Handler(GameObject object[]) {
        this.object = object;
    }

    @Override
    public boolean keyDown(int keycode) {
        for (int i = 0; i < object.length; i++)
            if (object[i].active)
                object[i].Input(KindOfInput.keyDown, 0, 0, 0, keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (int i = 0; i < object.length; i++)
            if (object[i].active)
                object[i].Input(KindOfInput.keyUp, 0, 0, 0, keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        for (int i = 0; i < object.length; i++)
            if (object[i].active)
                object[i].Input(KindOfInput.keyTyped, 0, 0, 0, character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < object.length; i++)
            if (object[i].active)
                object[i].Input(KindOfInput.touchDown, screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < object.length; i++)
            if (object[i].active)
                object[i].Input(KindOfInput.touchUp, screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (int i = 0; i < object.length; i++)
            if (object[i].active)
                object[i].Input(KindOfInput.touchDrag, screenX, screenY, pointer, 0);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (int i = 0; i < object.length; i++)
            if (object[i].active)
                object[i].Input(KindOfInput.mouseMove, screenX, screenY, 0, 0);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        for (int i = 0; i < object.length; i++)
            if (object[i].active)
                object[i].Input(KindOfInput.scroll, 0, 0, 0, amount);
        return false;
    }
}
