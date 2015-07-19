package com.me.p9game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = P9Game.TITLE + " v" + P9Game.VERSION;
		cfg.vSyncEnabled = true;
		cfg.width = 480;
		cfg.height = 800;
		new LwjglApplication(new P9Game(), cfg);
	}
}
