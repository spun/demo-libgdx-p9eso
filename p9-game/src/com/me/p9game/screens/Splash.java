package com.me.p9game.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.p9game.Assets;
import com.me.p9game.Settings;
import com.me.p9game.tween.SpriteAccessor;

/*
 * Clase Splash que representa la pantalla prentacion
 */
public class Splash implements Screen {
	Game game;
	private SpriteBatch batch;
	private TweenManager tweenManager;

	/*
	 *  Constructor sobrecargado de la clase Splash
	 */
	public Splash(Game game) {
		this.game = game;		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		Assets.splashSprite.draw(batch);
		batch.end();

		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		Gdx.graphics.setVSync(Settings.vSyncEnabled);
		// apply preferences		
		batch = new SpriteBatch();

		// TWEENENGINE
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		Assets.splashSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Tween.set(Assets.splashSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(Assets.splashSprite, SpriteAccessor.ALPHA, 1.5f).target(1).repeatYoyo(1, 1.0f).setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));				
			}
		}).start(tweenManager);

		tweenManager.update(Float.MIN_VALUE);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}