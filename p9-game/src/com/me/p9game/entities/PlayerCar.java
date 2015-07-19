package com.me.p9game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.me.p9game.Assets;
import com.me.p9game.P9Game;
import com.me.p9game.Settings;

/*
 * Clase PlayerCar que representa el vehiculo controlado
 * por el jugador
 */
public class PlayerCar extends GameObject {
	private Rectangle bounds = new Rectangle();
	public int live;
	public boolean onFire = false;
	
	/*
	 *  Constructor sobrecargado de la clase PlayerCar
	 */
	public PlayerCar() {
		// Caracteristicas del vehiculos
		setWidth(30);
		setHeight(48);
		setPosition(283, 60);
		setColor(Color.YELLOW);
		live = 100;
		velocity.y = 5;
		
		// Efectos de particulas si estan activados
		if(Settings.effectsEnabled) {
			Assets.fireEffect.setPosition(getX()+(getWidth()/2), getY()+(getHeight()/2));
			Assets.fireEffect.start();
			Assets.fireEffect.findEmitter("flame").allowCompletion();
		}
		
	}
	
	
	@Override
	public void act(float deltaTime){
		super.act(deltaTime);	
		this.setPosition(getX() + (velocity.x * deltaTime), getY());
		
		// Evitamos que el usuario salga de la pantalla
		if(getX() < 0) {
			setX(0);
		} else if(getX() > (P9Game.WIDTH-getWidth())) {
			setX(P9Game.WIDTH-getWidth());
		}
		
		// Disminuimos la velocidad al ir por tierra
		if((getX() < 15) || (getX() > 212 && getX() < 238) || (getX() > 436)) {
			velocity.y -= 0.02;
			velocity.y = Math.max(5, velocity.y);			
		} else {
			velocity.y += 0.005;
		}
			
		// Efecto de humo o fuego dependiendo del daño
		if(Settings.effectsEnabled)	{			
			if(live < 30 && onFire == false) {	
				Assets.fireEffect.findEmitter("flame").start();
				onFire = true;
			}
			Assets.fireEffect.setPosition(getX()+(getWidth()/2), getY()+(getHeight()/2));
			Assets.fireEffect.update(deltaTime);
		}
		
		bounds.set(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a);			
		batch.draw(Assets.playerCarRegion, getX(), getY(), 0, 0, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		
		// Dibujamos los efectos si estan activados
		if(Settings.effectsEnabled && live < 70) {	
			Assets.fireEffect.draw(batch);
		}
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}

