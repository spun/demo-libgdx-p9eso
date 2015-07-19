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

/*
 * Clase HelpScreen que representa la pantalla de ayuda
 */
public class HelpScreen implements Screen {
	
	Game game;					// Juego	
	OrthographicCamera guiCam;	// Camara	
	SpriteBatch batcher;		// Lienzo
	
	// Rectangulos de impacto
	Rectangle backBounds;
	Rectangle nextBounds;	
	// Zona de toque
	Vector3 touchPoint;
	// Puntuaciones
	String[] highScores;
	
	float xOffset = 0;
	int screenPos = 0;

	/*
	 *  Constructor sobrecargado de la clase HelpScreen
	 */
	public HelpScreen (Game game) 
	{
		this.game = game;
		guiCam = new OrthographicCamera(480, 800);
		guiCam.position.set(480 / 2, 800 / 2, 0);
		
		batcher = new SpriteBatch();
		backBounds = new Rectangle(20, 20, 64, 64);
		nextBounds = new Rectangle(396, 20, 64, 64);
		
		touchPoint = new Vector3();		
	}
	

	/*
	 *  Metodo update llamada para actualizar la informacion antes de pintar el frame
	 */
	public void update () 
	{
		// Si se ha tocado la pantalla
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));			
			// Si se ha pincahdo en el boton atras
			if (backBounds.contains(touchPoint.x, touchPoint.y)) {
				if(screenPos > 0) {
					screenPos--;					
				} else {
					game.setScreen(new MainMenuScreen(game));
				}
				return;
			}
			// Si se ha pincahdo en el boton adelante
			if (nextBounds.contains(touchPoint.x, touchPoint.y)) {				
				if(screenPos < Assets.helpScreens.size-1) {
					screenPos++;
				} else {					
					game.setScreen(new MainMenuScreen(game));
				}
				return;
			}
		}
	}

	
	/*
	 *  Metodo draw llamado cada vez que se quiere pintar un frame
	 */
	public void draw () 
	{
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		// Fondo de pantalla
		batcher.disableBlending();
		batcher.begin();
		batcher.draw(Assets.helpScreens.get(screenPos), 0, 0, 480, 800);
		batcher.end();
		// Botones
		batcher.enableBlending();
		batcher.begin();
		batcher.draw(Assets.backArrowRegion, 20, 20, 64, 64);
		batcher.draw(Assets.nextArrowRegion, 396, 20, 64, 64);
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
