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
import com.me.p9game.Settings;

/*
 * Clase HighscoresScreen que representa la pantalla de puntuaciones
 */
public class HighscoresScreen implements Screen {
	Game game;					// Juego
	OrthographicCamera guiCam;	// Camara
	SpriteBatch batcher;		// Lienzo
	
	// Zonas de toque
	Rectangle backBounds;
	Rectangle resetBounds;
	// Vector de toque
	Vector3 touchPoint;
	// Puntuaciones
	String[] highScores;
	float xOffset = 0;

	/*
	 *  Constructor sobrecargado de la clase HighscoresScreen
	 */
	public HighscoresScreen (Game game) {
		this.game = game;
		guiCam = new OrthographicCamera(480, 800);
		guiCam.position.set(480 / 2, 800 / 2, 0);
		batcher = new SpriteBatch();
		
		// Definimos las zonas de los botones
		backBounds = new Rectangle(20, 20, 64, 64);
		resetBounds = new Rectangle(416, 20, 48, 48);	
		
		// Recogemos las puntuaciones
		highScores = new String[5];		
		for (int i = 0; i < 5; i++) {
			highScores[i] = i + 1 + ". " + Settings.highscores[i];
			xOffset = Math.max(Assets.font.getBounds(highScores[i]).width, xOffset);
		}
		xOffset = 160 - xOffset / 2 + Assets.font.getSpaceWidth() / 2;
		
		touchPoint = new Vector3();
	}

	/*
	 *  Metodo update llamada para actualizar la informacion antes de pintar el frame
	 */
	public void update () {
		// Si se ha tocado la pantalla
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			// Si se ha pincahdo en el boton atras
			if (backBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
			// Si se ha pincahdo en el boton reseteo
			if (resetBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				for (int i = 0; i < 5; i++) {
					Settings.highscores[i] = 0;
					highScores[i] = i + 1 + ". " + Settings.highscores[i];
				}
				Settings.save();
				return;
			}
		}
	}

	/*
	 *  Metodo draw llamado cada vez que se quiere pintar un frame
	 */
	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.disableBlending();
		// Fondo
		batcher.begin();
		batcher.draw(Assets.menuBackgroundRegion, 0, 0, 480, 800);
		batcher.end();
		// Puntuaciones
		batcher.enableBlending();
		batcher.begin();
		batcher.draw(Assets.scoresTitleRegion, 64, 800 - 150, 352, 92);
		batcher.draw(Assets.scoresBoardRegion, 64, 150, 352, 438);
		
		float y = 330;		
		for (int i = 4; i >= 0; i--) {
			Assets.font.draw(batcher, highScores[i], xOffset, y);
			y += Assets.font.getLineHeight() + 20;
		}
		// Botones
		batcher.draw(Assets.backArrowRegion, 20, 20, 64, 64);
		batcher.draw(Assets.resetButtonRegion, 416, 20, 48, 48);
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
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
		batcher.dispose();
	}
}
