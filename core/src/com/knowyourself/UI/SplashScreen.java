package com.knowyourself.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.bearfishapps.libs.GeneralScreens;
import com.bearfishapps.libs.Tools.FontGenerator;
import com.bearfishapps.libs.Tools.UICreationTools.CustomLabel;
import com.knowyourself.Constants;
import com.knowyourself.utils.AssetFinder;

public class SplashScreen extends GeneralScreens {
    private boolean isTransitioning = false;
    private final int slashMinTime = 3;
    public SplashScreen(Game game) {
        super(null, game);

        setBackgroundColor(255, 255, 255, 1);

        // Sorry this is terrible code
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assets = new AssetManager(resolver);

        assets.load(Constants.logo1, Texture.class);
        assets.load(Constants.logo2, Texture.class);
        assets.finishLoading();

        AssetFinder assetFinder = new AssetFinder(assets, resolver);
        assetFinder.load();

    }

    private boolean readyToTransition;
    @Override
    public void step(float delta, float animationKeyFrame) {
        assets.update();
        Gdx.app.log("Progress", String.valueOf(assets.getProgress()));
        if (assets.getProgress() > 0.5f && animationKeyFrame >= slashMinTime) {
            if(!isTransitioning) {
                isTransitioning = true;
                transition();
            }
        }
    }

    public void transition() {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
            public void run() {
                game.setScreen(new MainMenuScreen(assets, game));
            }
        })));
    }

    @Override
    public void postRender(float delta, float animationKeyFrame) {

    }

    @Override
    public void preShow(Table table, InputMultiplexer multiplexer) {
        Image logo1 = new Image(assets.get(Constants.logo1, Texture.class));
        logo1.setScaling(Scaling.fit);
        Image logo2 = new Image(assets.get(Constants.logo2, Texture.class));
        logo2.setScaling(Scaling.fit);

        // Sorry this is a sin in coding
        FontGenerator.generate(48, Constants.font);
        CustomLabel.make(FontGenerator.returnFont(), Color.BLACK);
        Label loading = new Label("Loading...", CustomLabel.style);
        loading.setVisible(false);

//        table.setDebug(true);
        table.center();
        table.add(logo2).width(200).height(200).fill();
        table.add(logo1).width(300).height(200).fill().row();
        table.add(loading).colspan(2).row();

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(slashMinTime)));
        loading.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(slashMinTime), Actions.run(new Runnable() {
            @Override
            public void run() {
                loading.setVisible(true);
            }
        }), Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.fadeIn(1), Actions.fadeOut(1)))));

    }

    @Override
    public void destroy() {

    }
}
