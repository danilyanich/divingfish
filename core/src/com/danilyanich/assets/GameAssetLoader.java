package com.danilyanich.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameAssetLoader {
    public static final int highC = 1, lowC = 3, weedC = 3, buttonC = 12;
    public static Texture high[] = new Texture[highC];
    public static Texture low[] = new Texture[lowC];
    public static Texture background;
    public static Texture weed[] = new Texture[weedC];
    public static Texture fish;
    public static Texture button[] = new Texture[buttonC];
    public static Sound pilink;
    public static BitmapFont font;

    public static void Load() {
        pilink = Gdx.audio.newSound(Gdx.files.internal("Pilink.wav"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        for (int i = 1; i <= highC; i++)
            high[i - 1] = new Texture(Gdx.files.internal("sprites/high/" + i + ".png"));
        for (int i = 1; i <= lowC; i++)
            low[i - 1] = new Texture(Gdx.files.internal("sprites/low/" + i + ".png"));
        background = new Texture(Gdx.files.internal("sprites/Background.png"));
        fish = new Texture(Gdx.files.internal("sprites/fish.png"));
        for (int i = 1; i <= weedC; i++)
            weed[i - 1] = new Texture(Gdx.files.internal("sprites/weed/" + i + ".png"));
        for (int i = 1; i <= buttonC; i++)
            button[i - 1] = new Texture(Gdx.files.internal("sprites/ui/" + i + ".png"));
    }

    public static void dispose() {
        pilink.dispose();
        for (int i = 0; i < highC; i++)
            high[i].dispose();
        for (int i = 0; i < lowC; i++)
            low[i].dispose();
        background.dispose();
        fish.dispose();
        for (int i = 0; i < weedC; i++)
            weed[i].dispose();
        for (int i = 1; i <= buttonC; i++)
            button[i - 1].dispose();
        font.dispose();
    }
}
