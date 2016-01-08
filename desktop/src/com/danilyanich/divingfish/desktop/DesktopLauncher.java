package com.danilyanich.divingfish.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.danilyanich.divingfish.DF;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config;
		config = new LwjglApplicationConfiguration();
		config.title = "divingfish";
		config.width = 500;
		config.height = 700;
		config.resizable = false;
		new LwjglApplication(new DF(null), config);
	}
}
