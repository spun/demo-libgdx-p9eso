package com.me.p9game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/*
 * Clase Settings que contiene la configuración del usuario
 */
public class Settings {
	public static boolean soundEnabled = true;	
	public static boolean vSyncEnabled = true;
	public static boolean enemysEnabled = true;
	public static boolean effectsEnabled = true;
	public static boolean vibrationEnabled = true;
	public static boolean liveControlEnabled = true;
	public final static int[] highscores = new int[] {0, 0, 0, 0, 0};
	public final static String file = ".roadrun";

	/*
	 * Metodo load que carga la configuracion del usuario
	 */
	public static void load () {
		try {
			FileHandle filehandle = Gdx.files.external(file);			
			String[] strings = filehandle.readString().split("\n");		
			// Leemos sonido
			soundEnabled = Boolean.parseBoolean(strings[0]);
			// Leemos vSync
			vSyncEnabled = Boolean.parseBoolean(strings[1]);
			// Leemos enemigos
			enemysEnabled = Boolean.parseBoolean(strings[2]);
			// Leemos effectos de particulas
			effectsEnabled = Boolean.parseBoolean(strings[3]);
			// Leemos vibracion
			vibrationEnabled = Boolean.parseBoolean(strings[4]);
			// Leemos control vida
			liveControlEnabled = Boolean.parseBoolean(strings[5]);
			
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(strings[i+6]);
			}
		} catch (Throwable e) {
		}
	}

	/*
	 * Metodo save que guarda la configuracion del usuario
	 */
	public static void save () {
		try {
			FileHandle filehandle = Gdx.files.external(file);			
			filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
			filehandle.writeString(Boolean.toString(vSyncEnabled)+"\n", true);
			filehandle.writeString(Boolean.toString(enemysEnabled)+"\n", true);
			filehandle.writeString(Boolean.toString(effectsEnabled)+"\n", true);
			filehandle.writeString(Boolean.toString(vibrationEnabled)+"\n", true);
			filehandle.writeString(Boolean.toString(liveControlEnabled)+"\n", true);			
			for (int i = 0; i < 5; i++) {
				filehandle.writeString(Integer.toString(highscores[i])+"\n", true);
			}
		} catch (Throwable e) {
		}
	}

	/*
	 * Metodo addScore que guarda una puntuacion si está entre las mejores
	 */
	public static void addScore (int score) {
		for (int i = 0; i < 5; i++) {
			if (highscores[i] < score) {
				for (int j = 4; j > i; j--)
					highscores[j] = highscores[j - 1];
				highscores[i] = score;
				break;
			}
		}
	}
}
