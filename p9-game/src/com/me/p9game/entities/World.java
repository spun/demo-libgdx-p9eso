package com.me.p9game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.me.p9game.Assets;
import com.me.p9game.Settings;

/*
 * Clase World que representa el escenario y contiene
 * sus elementos
 */
public class World extends Stage { 
	private Table worldContainer;	// Contenedor de elementos	
	
	InfiniteScrollBg backgroundRoad;				// Fondo	
	public PlayerCar car;							// Jugador	
	private final Array<TrafficCar> activeTrafic;	// Trafico
	private final Array<Wombat> activeWombats;		// Wombats	
	
	// Ultimo tiempo de creacion de elementos
	private long lastCarTime = 0;
	private long lastWombatTime = 0;
	
	// Posicion de las lineas en la carretera
	public final float dirtLane0 = 7;
	public final float dirtLane1 = 225;
	public final float dirtLane2 = 445;
	public final float lane0 = 58;
	public final float lane1 = 112;
	public final float lane2 = 170;
	public final float lane3 = 283;
	public final float lane4 = 338;
	public final float lane5 = 394;
	
	public int score = 0;	// Puntuacion
	// Estados posibles del escenario
	public int state;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	
	/*
	 *  Constructor sobrecargado de la clase World
	 */	
	public World() {
		// Creamos el contenedor de elementos
		worldContainer = new Table();
		worldContainer.setFillParent(true);
		this.setViewport(new StretchViewport(480, 800));
		this.addActor(worldContainer);		
		// Creamos los elementos 
		car = new PlayerCar();
		activeTrafic = new Array<TrafficCar>();	
		activeWombats = new Array<Wombat>();
		backgroundRoad = new InfiniteScrollBg(this);
		// Añadimos los elementos
		worldContainer.addActor(backgroundRoad);
		worldContainer.addActor(car);
		// Estado del juego
		this.state = WORLD_STATE_RUNNING;		
	}
	
	/*
	 *  Metodo update llamada para actualizar la informacion antes de pintar el frame
	 */
	public void update(float deltaTime, float accelerometerX) {		
		super.act(deltaTime);
		
		// Rotacion del vehiculos del jugador en base al giro
		car.setRotation(accelerometerX * 2);
		car.velocity.x = -accelerometerX / 10 * 800;  
		
		// Si la vida del jugador llega a 0
		if(car.live <= 0) {
			car.live = 0;
			state = WORLD_STATE_GAME_OVER;
		} else {
			checkCarCollisions();
			checkWombatCollision();
		}		
		// Crea un elemento si se cumple el tiempo
		if (Settings.enemysEnabled && (TimeUtils.nanoTime() - lastCarTime > 400000000f)) spawnCar();
		if (Settings.enemysEnabled && (TimeUtils.nanoTime() - lastWombatTime > 25000000000f)) spawnWombat();
	}	
	
	/*
	 *  Metodo checkWombatCollision que comprueba las colisiones con wombats
	 */
	private void checkWombatCollision() {
		// Recorremos todos los wombats
		for(Wombat wombat : activeWombats) {
			// Si el wombat sale de la pantalla
			if (wombat.getBounds().y + wombat.getHeight() <= 0) {
				// Eliminamos el wombat de los activos
				activeWombats.removeValue(wombat, true);
				worldContainer.removeActor(wombat);
			}
			// Si colisiona con el vehiculo
			if (wombat.getBounds().overlaps(car.getBounds())) {                
                wombat.crash(false, false);                
                if(Settings.vibrationEnabled) {
                	Gdx.input.vibrate(50);               
                }
                score += 100;
                Assets.playSound(Assets.hitSound);              
                activeWombats.removeValue(wombat, true);
			}
        }		
	}

	/*
	 *  Metodo checkWombatCollision que comprueba las colisiones con otros vehiculos
	 */
	private void checkCarCollisions() {
		// Recorremos todos los vehiculos
		for(TrafficCar enemyCar : activeTrafic) {
			// Si el vehiculo sale de la pantalla
			if (enemyCar.getBounds().y + enemyCar.getHeight() <= 0) {
				activeTrafic.removeValue(enemyCar, true);
				worldContainer.removeActor(enemyCar);
				
				// Aumenta los puntos dependiendo si ha esquivado el vehiculo en una linea u otra
				if(enemyCar.getX() >= lane0 && (enemyCar.getX() <= (lane2 + enemyCar.getWidth())) && 
						car.getX() >= lane0 && (car.getX() <= (lane2 + car.getWidth()))) {
					score += 10;
				} else if(enemyCar.getX() >= lane3 && (enemyCar.getX() <= (lane5 + enemyCar.getWidth())) && 
						car.getX() >= lane3 && (car.getX() <= (lane5 + car.getWidth()))) {
					score += 5;
				}				
			}
			
			// Si colisiona con el vehiculo
			if (enemyCar.getBounds().overlaps(car.getBounds())) {
                if (enemyCar.getX() > car.getX()) {
                    if (enemyCar.getY() > car.getY()) enemyCar.crash(true, true);
                    else enemyCar.crash(true, false);
                }
                else {
                    if (enemyCar.getY() > car.getY()) enemyCar.crash(false, true);
                    else enemyCar.crash(false, false);
                }
                
                if(Settings.vibrationEnabled) {
                	Gdx.input.vibrate(50);               
                }                
                if(Settings.liveControlEnabled) {
                	car.live -= 10;
                }
                
                Assets.playSound(Assets.hitSound);
                car.velocity.y = Math.min(car.velocity.y, 8);                
                activeTrafic.removeValue(enemyCar, true);
			}
        }
		
	}

	/*
	 *  Genera un vehiculo en una linea aleatoria
	 */
	private void spawnCar() {
		int lane = MathUtils.random(0, 5);
		float yPos = 0;
		if (lane == 0) yPos = lane0;
		if (lane == 1) yPos = lane1;
		if (lane == 2) yPos = lane2;
		if (lane == 3) yPos = lane3;
		if (lane == 4) yPos = lane4;
		if (lane == 5) yPos = lane5;
		TrafficCar enemyCar = new TrafficCar(yPos, this);
		activeTrafic.add(enemyCar);
		worldContainer.addActor(enemyCar);
		lastCarTime = TimeUtils.nanoTime();
	}
	
	/*
	 *  Genera un wombat en una linea aleatoria
	 */
	private void spawnWombat() {
		int lane = MathUtils.random(0, 2);
		float yPos = 0;
		if (lane == 0) yPos = dirtLane0;
		if (lane == 1) yPos = dirtLane1;
		if (lane == 2) yPos = dirtLane2;
		Wombat wombat = new Wombat(yPos, this);
		activeWombats.add(wombat);
		worldContainer.addActor(wombat);
		lastWombatTime = TimeUtils.nanoTime();
	}
}
	