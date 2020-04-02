package com.morbid.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.morbid.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1500;
		config.height = 800;
//		config.fullscreen = true;
//		config.vSyncEnabled = true;

		new LwjglApplication(new MainGame(), config);
	}
}
