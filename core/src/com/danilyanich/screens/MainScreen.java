package com.danilyanich.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.danilyanich.gameobject.GameObject;
import com.danilyanich.gameworld.DFRender;
import com.danilyanich.gameworld.DFWorld;
import com.danilyanich.gameworld.Highscore;
import com.danilyanich.input.Handler;

public class MainScreen implements Screen {
    private DFRender Renderer;
    private DFWorld World;

    public MainScreen() {
        int X = Gdx.graphics.getWidth();
        int Y = Gdx.graphics.getHeight();
        Highscore.load();
        World = new DFWorld(X, Y);
        Renderer = new DFRender(World);
        GameObject arr[] = new GameObject[1];
        arr[0] = World.getController();
        Handler input = new Handler(arr);
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        World.update(delta);
        Renderer.update(delta);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    public DFWorld getWorld() {
        return World;
    }

    public DFRender getRenderer() {
        return Renderer;
    }
}
