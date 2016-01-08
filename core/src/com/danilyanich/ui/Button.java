package com.danilyanich.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.danilyanich.constants.Constants;
import com.danilyanich.gameobject.GameObject;
import com.danilyanich.input.ActionInputListener;
import com.danilyanich.input.KindOfInput;

public class Button extends GameObject {
    protected ActionInputListener action;
    protected Rectangle bound;
    protected Ui textureNumber;
    protected String text;

    public Button(Vector2 pos, Vector2 size, Ui textureNumber, String text, ActionInputListener action, boolean active) {
        super(pos, size, active);
        this.action = action;
        this.textureNumber = textureNumber;
        this.text = text;
        this.bound = new Rectangle(pos.x, pos.y, size.x, size.y);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void Input(KindOfInput in, int screenX, int screenY, int pointer, int button) {
        if (bound.contains(screenX, screenY) && in == KindOfInput.touchDown && active) {
            action.action(Constants.game.getScreen(), in, screenX, screenY, pointer, button);
        }
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
        this.bound.setPosition(position);
    }

    @Override
    public void setSize(Vector2 size) {
        this.size = size;
        this.bound.setSize(size.x,size.y);
    }
    public int getTextureNumber() {
        return textureNumber.ordinal();
    }

    public String getText() {
        return text;
    }

}
