package com.knowyourself;

import com.badlogic.gdx.Game;
import com.bearfishapps.libs.Tools.Prefs;
import com.knowyourself.UI.SplashScreen;

public class MainGameClass extends Game {
	public MainGameClass() {
	}

	@Override
	public void create () {
		Prefs.init(Constants.prefName);
		this.setScreen(new SplashScreen(this));
	}
}
