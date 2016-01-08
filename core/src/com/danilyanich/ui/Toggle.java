package com.danilyanich.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.danilyanich.constants.Constants;
import com.danilyanich.input.ActionInputListener;
import com.danilyanich.input.KindOfInput;

public class Toggle extends Button {
    private ActionInputListener unaction;
    private Ui checkNumber;
    private boolean toggle;

    public Toggle(Vector2 pos, Vector2 size, Ui textureNumber, Ui checkNumber, String text,
                  ActionInputListener action,ActionInputListener unaction ,boolean is_toggled,boolean active) {
        super(
                pos,
                size,
                textureNumber,
                text,
                action,
                active
        );
        this.unaction = unaction;
        this.textureNumber = textureNumber;
        this.checkNumber = checkNumber;
        this.text = text;
        this.toggle = is_toggled;
        this.bound = new Rectangle(pos.x, pos.y, size.x, size.y);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void Input(KindOfInput in, int screenX, int screenY, int pointer, int button) {
        if (bound.contains(screenX, screenY) && in == KindOfInput.touchDown && active){
            if(toggle){
                action.action(Constants.game.getScreen(),  in, screenX, screenY,  pointer,  button);
                toggle = false;
            }
            else{
                unaction.action(Constants.game.getScreen(),  in, screenX, screenY,  pointer,  button);
                toggle = true;
            }
        }
    }

    @Override
    public int getTextureNumber() {
        if(toggle)
            return textureNumber.ordinal();
        else return checkNumber.ordinal();
    }

    public void Toggle()
    {
        if(toggle){
            action.action(Constants.game.getScreen(),  KindOfInput.touchDown, 0, 0, 0, 0);
            toggle = false;
        }
        else{
            unaction.action(Constants.game.getScreen(), KindOfInput.touchDown, 0, 0, 0, 0);
            toggle = true;
        }
    }

    public boolean isToggled() {
        return toggle;
    }

    public int getCheckNumber() {
        return checkNumber.ordinal();
    }
}
