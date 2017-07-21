package com.knowyourself.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bearfishapps.libs.GeneralScreens;
import com.kotcrab.vis.ui.VisUI;

public class MainMenuScreen extends GeneralScreens {
    public MainMenuScreen(AssetManager assetManager, Game game) {
        super(assetManager, game);
        VisUI.load();
    }

    @Override
    public void step(float delta, float animationKeyFrame) {

    }

    @Override
    public void postRender(float delta, float animationKeyFrame) {

    }

    @Override
    public void preShow(Table table, InputMultiplexer multiplexer) {

    }

    @Override
    public void destroy() {
        VisUI.dispose();
    }
}
