package com.knowyourself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bearfishapps.libs.GeneralScreens;
import com.knowyourself.utils.AssetFinder;

public class GameScreen extends GeneralScreens {

    public GameScreen(Game game) {
        super(game);
        FileHandleResolver resolver = new InternalFileHandleResolver();
        AssetManager assets = new AssetManager(resolver);
        AssetFinder assetFinder = new AssetFinder(assets, resolver);
        assetFinder.load();
        assets.finishLoading();
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

    }
}
