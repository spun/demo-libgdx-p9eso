package com.me.p9game;

import com.badlogic.gdx.Game;
import com.me.p9game.screens.Splash;

public class P9Game extends Game {
	public final static int WIDTH = 480;
	public final static int HEIGHT = 800;
	public static final String TITLE = "RoadRun 2", VERSION = "0.1";
	boolean firstTimeCreate = true;

	@Override
	public void create () {
		Settings.load();
		Assets.load();
		setScreen(new Splash(this));
	}
	
	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		Assets.dispose();
		getScreen().dispose();
	}
}
