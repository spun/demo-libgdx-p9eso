package com.me.p9game.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.me.p9game.Assets;
import com.me.p9game.Settings;
import com.me.p9game.entities.World;

/*
 * Clase MainMenuScreen que representa la pantalla del juego
 */
public class Play implements Screen {
	// Estados posibles del juego
	int state;
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_OVER = 3;
		
	Game game;					// Juego
	OrthographicCamera guiCam;	// Camara
	World world;				// Mundo
	Batch batcher;				// Lienzo
	ShapeRenderer shapeRenderer;// Lienzo de vectores
	
	// Zonas de toque
	Vector3 touchPoint;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	
	// Informacion que se muestra al usuario
	int lastScore;
	String scoreString;
	String timeString;
	float time = 0;
	
	/*
	 *  Constructor sobrecargado de la clase Play
	 */
	public Play(Game game) {
		this.game = game;
		guiCam = new OrthographicCamera(480, 800);
		guiCam.position.set(480 / 2, 800 / 2, 0);
		world = new World();
		batcher = world.getSpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		// Zonas de toque de los botones
		touchPoint = new Vector3();				
		pauseBounds = new Rectangle(480 - 80, 800 - 80, 64, 64);
		resumeBounds = new Rectangle(103, 300, 362-103, 382-308);
		quitBounds = new Rectangle(174, 212, 303-174, 282-212);
		
		// Informacion
		lastScore = 0;
		scoreString = "Puntos: 0";
		timeString = "Tiempo: 0";
		
		// Estado del juego
		state = GAME_READY;
	}
	
	@Override
	public void render(float delta) {
		update(delta);	// Actualiza
		draw();			// Pinta		
	}
	
	/*
	 * Metodo update que actualiza la informacion dependiendo del estado del jego
	 */
	private void update(float deltaTime) {		
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}		
	}
	
	/*
	 * Metodo draw que dibuja los elementos dependiendo del estado del juego
	 */
	private void draw() {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.draw();		// Elementos del escenario
		
		batcher.setColor(Color.WHITE);	
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.enableBlending();
		batcher.begin();

		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:			
			presentRunning();			
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		batcher.end();		
	}
	
	/*
	 * Metodo updateGameOver que actualiza los elementos en GAME_OVER
	 */
	private void updateGameOver() {
		// Si se pulsa la pantalla se vuelve al menú
		if (Gdx.input.justTouched()) {
			game.setScreen(new MainMenuScreen(game));
		}		
	}

	/*
	 * Metodo updatePaused que actualiza los elementos en PAUSA
	 */
	private void updatePaused() {
		// Si se pulsa la pantalla
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			// Si se ha pulsado en reanudar
			if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}
			// Si se ha pulsado en quitar
			if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}		
	}

	/*
	 * Metodo updateRunning que actualiza los elementos durante el juego
	 */
	private void updateRunning(float deltaTime) {
		// Si se pulsa la pantalla
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			// Si se ha pulsado en pausar
			if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}
		}
		
		ApplicationType appType = Gdx.app.getType();		
		time += deltaTime;
		
		// Comprobamos si es un dispositivo movil o un ordenado
		if (appType == ApplicationType.Android || appType == ApplicationType.iOS) {
			// Usamos los acelerometros
			world.update(deltaTime, Gdx.input.getAccelerometerX());
		} else {
			// Usamos las teclas de direccion
			float accel = 0;
			if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) accel = 5f;
			if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) accel = -5f;
			world.update(deltaTime, accel);
		}
		
		// Actualizamos los puntos
		if (world.score != lastScore) {
			lastScore = world.score;
			scoreString = "Puntos: " + lastScore;
		}
		// Actualizamos el tiempo
		timeString = "Tiempo: "+(int)time;
		// Calculamos y guardamos los puntos
		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
			lastScore = Math.max((int) (lastScore - time), 0);
			if (lastScore >= Settings.highscores[0])
				scoreString = "Nuevo record: " + lastScore;
			else
				scoreString = "Puntuación: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save();
		}
	}

	/*
	 * Metodo updateReady que actualiza los elementos en modo espera antes de comenzar
	 */
	private void updateReady() {
		// Comienza el juego al pulsar la pantalla
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	/*
	 * Metodo presentGameOver que dibuja los elementos en GAME_OVER
	 */
	private void presentGameOver() {
		batcher.draw(Assets.gameOverRegion, (480 - 230) / 2, (800 - 178) / 2, 230, 178);
		float scoreWidth = Assets.font.getBounds(scoreString).width;
		Assets.font.draw(batcher, scoreString, (480 - scoreWidth) / 2, 280);		
	}

	/*
	 * Metodo presentPaused que dibuja los elementos en PAUSA
	 */
	private void presentPaused() {
		batcher.draw(Assets.pauseMenuRegion, (480 - 286) / 2, (800 - 411) / 2, 286, 411);
		showGameHud();	
	}

	/*
	 * Metodo presentRunning que dibuja los elementos durante el juego
	 */
	private void presentRunning() {
		batcher.draw(Assets.pauseButtonRegion, 480 - 80, 800 - 80, 64, 64);
		showGameHud();
		
	}

	/*
	 * Metodo presentReady que dibuja los elementos antes de jugar
	 */
	private void presentReady() {		
		batcher.draw(Assets.readyMessageRegion, (480 - 316) / 2, (800 - 33) / 2, 316, 33);		
	}
	
	/*
	 * Metodo showGameHud que dibuja el HUD con informacion del juego
	 */
	private void showGameHud() {		
		Assets.font.draw(batcher, "Vida: ", 16, 800 - 20);
		Assets.font.draw(batcher, scoreString, 16, 800 - 50);
		Assets.font.draw(batcher, timeString, 16, 800 - 80);
		
		// MARCADOR VIDA
		batcher.end();		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect((Gdx.graphics.getWidth()*100)/480, (Gdx.graphics.getHeight()*760)/800, (Gdx.graphics.getWidth()*280)/480, (Gdx.graphics.getHeight()*20)/800);
		if(world.car.live < 30)  {
			shapeRenderer.setColor(Color.RED);
		} else if (world.car.live < 70){
			shapeRenderer.setColor(Color.YELLOW);
		} else {
			shapeRenderer.setColor(Color.GREEN);
		}
		shapeRenderer.rect((Gdx.graphics.getWidth()*104)/480, (Gdx.graphics.getHeight()*764)/800, (Gdx.graphics.getWidth()*((world.car.live * 272) / 100))/480, (Gdx.graphics.getHeight()*12)/800);
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		batcher.begin();	
	}


	@Override
	public void resize(int width, int height) {
		world.getViewport().update(width, height, true);		
	}

	@Override
	public void show() {		
	}

	@Override
	public void hide() {
		Gdx.input.cancelVibrate();
	}

	@Override
	public void pause() {
		if (state == GAME_RUNNING) state = GAME_PAUSED;
		Gdx.input.cancelVibrate();
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		Gdx.input.cancelVibrate();
		batcher.dispose();
		shapeRenderer.dispose();
	}
}
