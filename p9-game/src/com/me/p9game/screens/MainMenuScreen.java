package com.me.p9game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.me.p9game.Assets;
import com.me.p9game.P9Game;
import com.me.p9game.Settings;

/*
 * Clase MainMenuScreen que representa la pantalla de menú principal
 */
public class MainMenuScreen implements Screen {
	Game game;
	OrthographicCamera guiCam;
	SpriteBatch batcher;
	
	Rectangle soundBounds;
	Rectangle consoleBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Rectangle exitBounds;
	Rectangle helpBounds;
	
	Vector3 touchPoint;

	/*
	 *  Constructor sobrecargado de la clase MainMenuScreen
	 */
	public MainMenuScreen (Game game) {
		this.game = game;

		guiCam = new OrthographicCamera(P9Game.WIDTH, P9Game.HEIGHT);
		guiCam.position.set(P9Game.WIDTH / 2, P9Game.HEIGHT / 2, 0);
		batcher = new SpriteBatch();
		soundBounds = new Rectangle(0, 0, 64, 64);
		consoleBounds = new Rectangle(P9Game.WIDTH - 50, P9Game.HEIGHT - 50, P9Game.WIDTH - 18, P9Game.HEIGHT - 18);	
		playBounds = new Rectangle(102, 328, 362 - 102, 426 - 328);			
		highscoresBounds = new Rectangle(66, 222, 397-66, 292-222);
		exitBounds = new Rectangle(155, 123, 308 -155, 192-123);
		helpBounds = new Rectangle(422, 10, 48, 48);
		touchPoint = new Vector3();
		
		if (Settings.soundEnabled) Assets.music.play();
	}

	/*
	 *  Metodo update llamada para actualizar la informacion antes de pintar el frame
	 */
	public void update () {
		// Si se ha tocado la pantalla
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			// Si se ha pincahdo en el boton Jugar
			if (playBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new Play(game));
				return;
			}
			// Si se ha pincahdo en el boton Puntuaciones
			if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HighscoresScreen(game));
				return;
			}
			// Si se ha pincahdo en el boton Salir
			if (exitBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Gdx.app.exit();
				return;
			}
			// Si se ha pincahdo en el boton ayuda
			if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HelpScreen(game));
				return;
			}
			// Si se ha pincahdo en el boton Debug
			if (consoleBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new HackScreen(game));
				return;
			}			
			// Si se ha pincahdo en el boton Mute/Sonido
			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				Settings.save();
				if (Settings.soundEnabled)
					Assets.music.play();
				else
					Assets.music.pause();
			}
		}
	}

	/*
	 *  Metodo draw llamado cada vez que se quiere pintar un frame
	 */
	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		// Fondo
		batcher.disableBlending();
		batcher.begin();
		batcher.draw(Assets.menuBackgroundRegion, 0, 0, P9Game.WIDTH, P9Game.HEIGHT);
		batcher.end();
		// Botones
		batcher.enableBlending();
		batcher.begin();
		batcher.draw(Assets.menuLogoRegion, (480 - 450) / 2, P9Game.HEIGHT-300 , 450, 228);
		batcher.draw(Assets.playButtonRegion, 61, 100, 358, 328);
		batcher.draw(Assets.consoleButtonRegion, P9Game.WIDTH - 50, P9Game.HEIGHT - 50, 32, 32);
		batcher.draw(Settings.soundEnabled ? Assets.soundOnRegion : Assets.soundOffRegion, 0, 0, 64, 64);
		batcher.draw(Assets.helpButtonRegion, 422, 10, 48, 48);
		batcher.end();
	}

	@Override
	public void render (float delta) {
		update();
		draw();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void show () {
	}

	@Override
	public void hide () {
	}

	@Override
	public void pause () {
		Settings.save();
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
		batcher.dispose();
	}
}
