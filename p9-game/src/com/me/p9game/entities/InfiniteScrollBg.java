package com.me.p9game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.me.p9game.Assets;

/*
 *  Clase InfiniteScrollBg que representa el fondo movil del juego
 */
public class InfiniteScrollBg extends Actor {
	
    float scrollTimer = 0.0f;	// Velocidad de scroll
    World world;				// Escenario

    /*
	 *  Constructor sobrecargado de la clase InfiniteScrollBg
	 */
	public InfiniteScrollBg(World world) {
		this.world = world;
	}

	@Override
	public void act(float deltaTime){
		// Actualiza la velocidad de scroll en base a la velocidad del vehiculo del usuario
		scrollTimer += Gdx.graphics.getDeltaTime() * world.car.velocity.y / 13.4;
        if(scrollTimer > 1.0f)
            scrollTimer = 0.0f;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// Pinta el fondo
		Assets.roadBackgroundSprite.setV(1.0f - scrollTimer);
	    Assets.roadBackgroundSprite.setV2(1.0f - scrollTimer + 1);    
        Assets.roadBackgroundSprite.draw(batch);
	}
}
