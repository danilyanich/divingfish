package com.danilyanich.input;

import com.badlogic.gdx.Screen;

public interface ActionInputListener {
    void action(Screen screen, KindOfInput in, int screenX, int screenY, int pointer, int button);
}
