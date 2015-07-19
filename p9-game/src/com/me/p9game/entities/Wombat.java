package com.me.p9game.entities;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.me.p9game.Assets;
import com.me.p9game.P9Game;

/*
 * Clase VehicleAsset que representa un wombat en el escenario
 */
public class Wombat extends GameObject  {
	private Rectangle bounds = new Rectangle();
	public World game;
	VehicleAsset asset;
	
	/*
	 *  Constructor sobrecargado de la clase Wombat
	 */
	public Wombat(float roadLine, World play) {
		game = play;	// Juego
		
		// Definimos las caracteristicas
		setPosition(roadLine, P9Game.HEIGHT);	
		setWidth(32);
		setHeight(30);
		velocity.y = 0;
		
		// Rotamos para que mire a la carretera
		if(roadLine > 300) {
			setScaleX(-getScaleX());
			setX(getX()+getWidth());
		}
	}

	@Override
	public void act(float delta){
		super.act(delta);
		float relativeSpeed = velocity.y;
		setY(getY() + (relativeSpeed - game.car.velocity.y));
		
		// Al escalar para que mire a la carretera cambia su zona de impacto
		float posXbound = getX();
		if(getX() > 300) {
			posXbound = getX() - getWidth();
		}
		bounds.set(posXbound, getY(), getWidth(), getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);		
		batch.draw(Assets.wombatRegion, getX(), getY(), 0, 0, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
	/*
	 * Metodo crash que mueve el wombat tras un choque
	 */
	public void crash(boolean front, boolean above) {
		clearActions();
		addAction(fadeOut(1f));
		velocity.y = game.car.velocity.y/2;		
		if (front && above) addAction(sequence(parallel(Actions.rotateBy(-360, 1.5f), Actions.moveBy(200, 200, 1.5f)), removeActor()));
		if (front && !above) addAction(sequence(parallel(Actions.rotateBy(360, 1.5f), Actions.moveBy(200, -200, 1.5f)), removeActor()));
		if (!front && above) addAction(sequence(parallel(Actions.rotateBy(360, 1.5f), Actions.moveBy(-200, 200, 1.5f)), removeActor()));
		if (!front && !above) addAction(sequence(parallel(Actions.rotateBy(-360, 1.5f), Actions.moveBy(-200, -200, 1.5f)), removeActor()));
	
	}

	public Rectangle getBounds() {
		return bounds;
	}
}
