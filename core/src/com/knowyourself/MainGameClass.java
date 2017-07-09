package com.knowyourself;

import com.badlogic.gdx.Game;
import com.knowyourself.screens.MainMenuScreen;

public class MainGameClass extends Game {
	public MainGameClass() {
	}

	@Override
	public void create () {
//		this.setScreen(new SplashScreen(this));
		this.setScreen(new MainMenuScreen(this));
	}
}
