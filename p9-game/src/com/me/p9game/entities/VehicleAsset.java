package com.me.p9game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.p9game.Assets;

/*
 * Clase VehicleAsset que contine elementos del aspecto
 * de un vehiculo
 */
public class VehicleAsset {
	public Texture image;
	public TextureRegion region;	
	
	public String orientation;
	public int width;
	public int height;
	
	/*
	 *  Constructor sobrecargado de la clase VehicleAsset
	 */
	public VehicleAsset(String imagePath, int width, int height, String orientation) {		
		this.image = Assets.loadTexture(imagePath);
		this.region = new Sprite(image);
		this.orientation = orientation;
		this.width = width;
		this.height = height;
	}

	public void dispose() {
		image.dispose();		
	}
}
