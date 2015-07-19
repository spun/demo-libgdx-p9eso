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
 * Clase HackScreen que representa la pantalla de debug
 */
public class HackScreen implements Screen {
	Game game;					// Juego
	OrthographicCamera guiCam;	// Camara
	SpriteBatch batcher;		// Lienzo
	
	// Rectangulos de impacto
	Rectangle backBounds;
	Rectangle vSyncBounds;	
	Rectangle enemysBounds;
	Rectangle effectsBounds;
	Rectangle vibrationBounds;	
	Rectangle liveControlBounds;
	// Zona de toque
	Vector3 touchPoint;
	float xOffset = 0;

	/*
	 *  Constructor sobrecargado de la clase HackScreen
	 */
	public HackScreen (Game game) {
		this.game = game;
		guiCam = new OrthographicCamera(480, 800);
		guiCam.position.set(480 / 2, 800 / 2, 0);
		batcher = new SpriteBatch();
		
		// Definimos las zonas
		backBounds = new Rectangle(20, 20, 64, 64);
		vSyncBounds = new Rectangle(40, 675, 390, 25);	
		enemysBounds = new Rectangle(40, 675 - 80, 390, 25);	
		effectsBounds = new Rectangle(40, 675 - 160,390, 25);	
		vibrationBounds = new Rectangle(40, 675 - 240, 390, 25);	
		liveControlBounds = new Rectangle(40, 675 - 320, 390, 25);	
				
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
				Settings.save();
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
			// Si se ha pincahdo en alguna opcion
			if (vSyncBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.vSyncEnabled = !Settings.vSyncEnabled;
				return;
			}
			if (enemysBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.enemysEnabled = !Settings.enemysEnabled;
				return;
			}
			if (effectsBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.effectsEnabled = !Settings.effectsEnabled;
				return;
			}
			if (vibrationBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.vibrationEnabled = !Settings.vibrationEnabled;
				return;
			}
			if (liveControlBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				Settings.liveControlEnabled = !Settings.liveControlEnabled;
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
		batcher.begin();
		
		// Opciones debug
		float y = 700;
		Assets.font.draw(batcher, "VSync: " + Settings.vSyncEnabled, 40, y);
		Assets.font.draw(batcher, "Enemigos: " + Settings.enemysEnabled, 40, y - 80);
		Assets.font.draw(batcher, "Efectos particulas: " + Settings.effectsEnabled, 40, y - 160);
		Assets.font.draw(batcher, "Vibración: " + Settings.vibrationEnabled, 40, y - 240);
		Assets.font.draw(batcher, "Control vida: " + Settings.liveControlEnabled, 40, y - 320);		
		// Boton atras
		batcher.draw(Assets.backArrowRegion, 20, 20, 64, 64);
		batcher.end();
	}

	@Override
	public void render (float delta) {
		update();	// Actualiza
		draw();		// Pinta
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
