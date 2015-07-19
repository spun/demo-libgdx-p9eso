package com.me.p9game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.me.p9game.entities.VehicleAsset;

/*
 * Clase Assets que contiene las imagenes y sonidos necesarios
 */
public class Assets {
	
	public static ParticleEffect fireEffect; 
	
	public static Texture splashImage;
	public static Sprite  splashSprite;
	
	public static Texture menuBackgroundImage;
	public static TextureRegion menuBackgroundRegion;
	
	public static Texture menuLogoImage;
	public static TextureRegion menuLogoRegion;
	
	public static Texture roadBackgroundImage;
	public static Sprite roadBackgroundSprite;
	
	public static Texture playerCarImage;
	public static TextureRegion playerCarRegion;
	
	public static Texture wombatImage;
	public static TextureRegion wombatRegion;
	
	public static Texture scoresTitleImage;
	public static TextureRegion scoresTitleRegion;
	
	public static Texture scoresBoardImage;
	public static TextureRegion scoresBoardRegion;
	
	public static Texture backArrowImage;
	public static TextureRegion backArrowRegion;
	
	public static Texture nextArrowImage;
	public static TextureRegion nextArrowRegion;	
	
	public static Texture resetButtonImage;
	public static TextureRegion resetButtonRegion;	
	
	public static Texture helpButtonImage;
	public static TextureRegion helpButtonRegion;	

	public static Texture consoleButtonImage;
	public static TextureRegion consoleButtonRegion;

	public static Texture playButtonImage;
	public static TextureRegion playButtonRegion;
	
	public static Texture readyMessageImage;
	public static TextureRegion readyMessageRegion;
	
	public static Texture pauseButtonImage;
	public static TextureRegion pauseButtonRegion;	
		
	public static Texture pauseMenuImage;
	public static TextureRegion pauseMenuRegion;	
	
	public static Texture gameOverImage;
	public static TextureRegion gameOverRegion;		
	
	public static Texture soundOnImage;
	public static TextureRegion soundOnRegion;
	public static Texture soundOffImage;
	public static TextureRegion soundOffRegion;
	
	public static BitmapFont font;

	public static Music music;
	public static Sound clickSound;
	public static Sound hitSound;
	
	public static Array<VehicleAsset> vehiclesImagesFront;
	public static Array<VehicleAsset> vehiclesImagesBack;


	public static Texture help1Image;
	public static Texture help2Image;
	public static Texture help3Image;
	public static Array<TextureRegion> helpScreens;
	
	/*
	 * Metodo loadTexture que carga una textura
	 */
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	/*
	 * Metodo load que carga los elementos
	 */
	public static void load () {
		fireEffect = new ParticleEffect();
		fireEffect.load(Gdx.files.internal("effects/flame.p"), Gdx.files.internal("effects"));	
		fireEffect.flipY();
				
		splashImage = loadTexture("images/splash.png");
		splashSprite = new Sprite(splashImage);
		
		menuBackgroundImage = loadTexture("images/background.png");
		menuBackgroundRegion = new TextureRegion(menuBackgroundImage, 0, 0, 480, 800);
		
		menuLogoImage = loadTexture("images/ui/logo.png");
		menuLogoRegion = new TextureRegion(menuLogoImage, 0, 0, 450, 228);
		
		roadBackgroundImage = loadTexture("images/road.png");		
		roadBackgroundImage.setWrap(TextureWrap.Repeat,TextureWrap.Repeat);
		roadBackgroundSprite = new Sprite(roadBackgroundImage, 0, 0, 479, 800);
		
		playerCarImage = loadTexture("images/car.png");
		playerCarRegion =  new TextureRegion(playerCarImage, 0, 0, 30, 48);
		
		wombatImage = loadTexture("images/wombat.png");
		wombatRegion =  new TextureRegion(wombatImage, 0, 0, 32, 30);
			
		scoresTitleImage = loadTexture("images/ui/scoresTitle.png");
		scoresTitleRegion =  new TextureRegion(scoresTitleImage, 0, 0, 352, 92);
		
		scoresBoardImage = loadTexture("images/ui/scoresBoard.png");
		scoresBoardRegion =  new TextureRegion(scoresBoardImage, 0, 0, 352, 438);
		
		backArrowImage = loadTexture("images/ui/back.png");
		backArrowRegion =  new TextureRegion(backArrowImage, 0, 0, 64, 64);
		
		nextArrowImage = loadTexture("images/ui/next.png");
		nextArrowRegion =  new TextureRegion(nextArrowImage, 0, 0, 64, 64);
		
		resetButtonImage = loadTexture("images/ui/resetButton.png");
		resetButtonRegion =  new TextureRegion(resetButtonImage, 0, 0, 48, 48);
		
		helpButtonImage = loadTexture("images/ui/helpButton.png");
		helpButtonRegion =  new TextureRegion(helpButtonImage, 0, 0, 48, 48);
		
		consoleButtonImage = loadTexture("images/ui/consoleButton.png");
		consoleButtonRegion =  new TextureRegion(consoleButtonImage, 0, 0, 32, 32);
		
		playButtonImage = loadTexture("images/ui/play.png");
		playButtonRegion =  new TextureRegion(playButtonImage, 0, 0, 358, 328);
		
		readyMessageImage = loadTexture("images/ui/readyMessage.png");
		readyMessageRegion =  new TextureRegion(readyMessageImage, 0, 0, 316, 33);
		
		pauseButtonImage = loadTexture("images/ui/pause.png");
		pauseButtonRegion =  new TextureRegion(pauseButtonImage, 0, 0, 64, 64);
						
		pauseMenuImage = loadTexture("images/ui/pauseMenu.png");
		pauseMenuRegion =  new TextureRegion(pauseMenuImage, 0, 0, 286, 411);
		
		gameOverImage = loadTexture("images/ui/gameOver.png");
		gameOverRegion =  new TextureRegion(gameOverImage, 0, 0, 230, 178);
				
		soundOnImage = loadTexture("images/ui/soundOn.png");
		soundOnRegion = new TextureRegion(soundOnImage, 0, 0, 64, 64);
		
		soundOffImage = loadTexture("images/ui/soundOff.png");
		soundOffRegion = new TextureRegion(soundOffImage, 0, 0, 64, 64);

		font = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font.png"), false);

		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
		music.setLooping(true);
		music.setVolume(0.3f);		
		
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));
		
		vehiclesImagesBack = new Array<VehicleAsset>();		
		vehiclesImagesBack.add(new VehicleAsset("images/vehicles/back1.png", 34, 40, "back"));
		vehiclesImagesBack.add(new VehicleAsset("images/vehicles/back2.png", 30, 48, "back"));
		vehiclesImagesBack.add(new VehicleAsset("images/vehicles/back3.png", 30, 47, "back"));
		vehiclesImagesBack.add(new VehicleAsset("images/vehicles/back4.png", 30, 51, "back"));
		vehiclesImagesBack.add(new VehicleAsset("images/vehicles/back5.png", 30, 48, "back"));
		vehiclesImagesBack.add(new VehicleAsset("images/vehicles/back6.png", 30, 48, "back"));
		vehiclesImagesBack.add(new VehicleAsset("images/vehicles/back7.png", 36, 67, "back"));
		vehiclesImagesBack.add(new VehicleAsset("images/vehicles/back8.png", 36, 68, "back"));
		vehiclesImagesBack.add(new VehicleAsset("images/vehicles/back9.png", 36, 65, "back"));
		
		vehiclesImagesFront = new Array<VehicleAsset>();
		vehiclesImagesFront.add(new VehicleAsset("images/vehicles/front1.png", 34, 41, "front"));
		vehiclesImagesFront.add(new VehicleAsset("images/vehicles/front2.png", 30, 48, "front"));
		vehiclesImagesFront.add(new VehicleAsset("images/vehicles/front3.png", 30, 48, "front"));
		vehiclesImagesFront.add(new VehicleAsset("images/vehicles/front4.png", 30, 51, "front"));
		vehiclesImagesFront.add(new VehicleAsset("images/vehicles/front5.png", 30, 48, "front"));
		vehiclesImagesFront.add(new VehicleAsset("images/vehicles/front6.png", 30, 48, "front"));
		vehiclesImagesFront.add(new VehicleAsset("images/vehicles/front7.png", 36, 70, "front"));
		vehiclesImagesFront.add(new VehicleAsset("images/vehicles/front8.png", 36, 68, "front"));
		vehiclesImagesFront.add(new VehicleAsset("images/vehicles/front9.png", 36, 68, "front"));
		
		
		help1Image = loadTexture("images/help/help1.png");
		help2Image = loadTexture("images/help/help2.png");
		help3Image = loadTexture("images/help/help3.png");
		helpScreens = new Array<TextureRegion>();
		helpScreens.add(new TextureRegion(help1Image, 0, 0, 480, 800));
		helpScreens.add(new TextureRegion(help2Image, 0, 0, 480, 800));
		helpScreens.add(new TextureRegion(help3Image, 0, 0, 480, 800));
	}

	/*
	 * Metodo playSound que reproduce un sonido
	 */
	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(1);
	}

	/*
	 * Libera los recursos
	 */
	public static void dispose() {
		fireEffect.dispose(); 
		splashImage.dispose();		
		menuBackgroundImage.dispose();		
		menuLogoImage.dispose();		
		roadBackgroundImage.dispose();		
		playerCarImage.dispose();		
		wombatImage.dispose();		
		scoresTitleImage.dispose();		
		scoresBoardImage.dispose();		
		backArrowImage.dispose();		
		nextArrowImage.dispose();		
		resetButtonImage.dispose();		
		helpButtonImage.dispose();
		consoleButtonImage.dispose();
		playButtonImage.dispose();		
		readyMessageImage.dispose();		
		pauseButtonImage.dispose();			
		pauseMenuImage.dispose();		
		gameOverImage.dispose();		
		soundOnImage.dispose();
		soundOffImage.dispose();		
		font.dispose();
		music.dispose();
		clickSound.dispose();
		hitSound.dispose();		
		for(VehicleAsset asset : vehiclesImagesFront) {
			asset.dispose();
		}
		for(VehicleAsset asset : vehiclesImagesBack) {
			asset.dispose();
		}
		help1Image.dispose();
		help2Image.dispose();
		help3Image.dispose();		
	}
}
