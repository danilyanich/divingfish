package com.danilyanich.tween;

import com.badlogic.gdx.math.Vector2;
import com.danilyanich.gameobject.GameObject;

import aurelienribon.tweenengine.TweenAccessor;

public class GameObjectTweenAccessor implements TweenAccessor<GameObject> {

    public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int POSITION_XY = 3;
    public static final int SIZE_XY = 4;

    @Override
    public int getValues(GameObject target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POSITION_X:
                returnValues[0] = target.getPosition().x;
                return 1;
            case POSITION_Y:
                returnValues[1] = target.getPosition().y;
                return 1;
            case POSITION_XY:
                returnValues[0] = target.getPosition().x;
                returnValues[1] = target.getPosition().y;
                return 2;
            case SIZE_XY:
                returnValues[0] = target.getSize().x;
                returnValues[1] = target.getSize().y;
                return 2;
        }
        return 0;
    }

    @Override
    public void setValues(GameObject target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POSITION_X:
                target.setPosition(new Vector2(newValues[0], target.getPosition().y));
                break;
            case POSITION_Y:
                target.setPosition(new Vector2(target.getPosition().x, newValues[0]));
                break;
            case POSITION_XY:
                target.setPosition(new Vector2(newValues[0], newValues[1]));
                break;
            case SIZE_XY:
                target.setSize(new Vector2(newValues[0], newValues[1]));
                break;
        }
    }
}
