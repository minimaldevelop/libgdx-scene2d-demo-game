package com.minimaldevelop.libgdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


//Referenca za Tile Sprajtove http://www.netthreads.co.uk/2012/01/31/libgdx-example-of-using-scene2d-actions-and-event-handling/

public class Assets {
	public static TextureRegion platform;
	public static TextureRegion fallingManSplash;
	public static Animation fallingManAnim;
	public static Texture backgroundTexture;
	public static Skin skin;
	
	public static void load () {
		
		TextureAtlas textureAtlas = new TextureAtlas("data/PigTest.pack");
		fallingManAnim = new Animation(0.2f, textureAtlas.findRegion("falling1"), textureAtlas.findRegion("falling2"));
		platform = textureAtlas.findRegion("platform");
		fallingManSplash = textureAtlas.findRegion("rip");
		backgroundTexture = new Texture(Gdx.files.internal("data/back.jpg"));
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
	}
}
