package com.me.p9game.entities;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.me.p9game.Assets;
import com.me.p9game.P9Game;

/*
 * Clase TrafficCar que representa un vehiculo de trafico
 * no controlado por el jugador
 */
public class TrafficCar extends GameObject  {
	private Rectangle bounds = new Rectangle();
	public World game;
	VehicleAsset asset;
	
	/*
	 *  Constructor sobrecargado de la clase TrafficCar
	 */
	public TrafficCar(float roadLine, World play) {
		game = play;	// Juego
		
		// Caracteristicas del vehiculo
		setPosition(roadLine, P9Game.HEIGHT);		
		int randomIndex = 0;
		if(getX() < 200) {
			randomIndex = new Random().nextInt(Assets.vehiclesImagesFront.size);
			asset = Assets.vehiclesImagesFront.get(randomIndex);
		} else {
			randomIndex = new Random().nextInt(Assets.vehiclesImagesBack.size);
			asset = Assets.vehiclesImagesBack.get(randomIndex);
		}
		setWidth(asset.width);
		setHeight(asset.height);		
		velocity.y = 3;
	}

	@Override
	public void act(float delta){
		super.act(delta);
		float relativeSpeed = velocity.y;
		if(getX() < 200)
			relativeSpeed = -velocity.y;
		setY(getY() + (relativeSpeed - game.car.velocity.y));
		bounds.set(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);		
		batch.draw(asset.region, getX(), getY(), 0, 0, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
		
	/*
	 * Metodo crash que mueve el coche tras un choque
	 */
	public void crash(boolean front, boolean above) {
		clearActions();
		addAction(fadeOut(1f));
		velocity.y = game.car.velocity.y/2;
		if(getX() < 200)
			velocity.y = -velocity.y;		
		
		if (front && above) addAction(sequence(parallel(Actions.rotateBy(-360, 1.5f), Actions.moveBy(200, 200, 1.5f)), removeActor()));
		if (front && !above) addAction(sequence(parallel(Actions.rotateBy(360, 1.5f), Actions.moveBy(200, -200, 1.5f)), removeActor()));
		if (!front && above) addAction(sequence(parallel(Actions.rotateBy(360, 1.5f), Actions.moveBy(-200, 200, 1.5f)), removeActor()));
		if (!front && !above) addAction(sequence(parallel(Actions.rotateBy(-360, 1.5f), Actions.moveBy(-200, -200, 1.5f)), removeActor()));
	}

	public Rectangle getBounds() {
		return bounds;
	}
}
