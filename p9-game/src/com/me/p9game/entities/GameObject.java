package com.me.p9game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/*
 *  Clase GameObject que representa un objeto movil del juego
 */
public class GameObject extends Actor {	
	public final Vector2 velocity;
	
	/*
	 *  Constructor sobrecargado de la clase GameObject
	 */
	public GameObject() {
		this.velocity = new Vector2();		
	}
}
