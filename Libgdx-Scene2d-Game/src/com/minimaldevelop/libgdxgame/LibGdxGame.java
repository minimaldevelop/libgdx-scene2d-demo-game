package com.minimaldevelop.libgdxgame;

import com.badlogic.gdx.Game;
import com.minimaldevelop.libgdxgame.screens.SplashScreen;

public class LibGdxGame extends Game {
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		Assets.load();
		setScreen(new SplashScreen(this));
	}
	
}
