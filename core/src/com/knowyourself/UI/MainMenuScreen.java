package com.knowyourself.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bearfishapps.libs.GeneralScreens;
import com.bearfishapps.libs.Tools.FontGenerator;
import com.bearfishapps.libs.Tools.UICreationTools.CustomLabel;
import com.knowyourself.Constants;

public class MainMenuScreen extends GeneralScreens {
    Label title, play, actSelection, loading;
    Table loadingTable;
    public MainMenuScreen(AssetManager assetManager, Game game) {
        super(assetManager, game);
        setBackgroundColor(255, 255, 255, 1);

        FontGenerator.generate(150, Constants.font);
        CustomLabel.make(FontGenerator.returnFont(), Color.BLACK);
        title = new Label("Know Yourself", CustomLabel.style);

        FontGenerator.generate(80, Constants.font);
        CustomLabel.make(FontGenerator.returnFont(), Color.BLACK);
        play = new Label("Play", CustomLabel.style);

        FontGenerator.generate(48, Constants.font);
        CustomLabel.make(FontGenerator.returnFont(), Color.BLACK);
        actSelection = new Label("Scene Selection", CustomLabel.style);

        FontGenerator.generate(24, Constants.font);
        CustomLabel.make(FontGenerator.returnFont(), Color.BLACK);
        loading = new Label("Loading ...", CustomLabel.style);

        loadingTable = new Table();
    }

    @Override
    public void preShow(Table table, InputMultiplexer multiplexer) {
        table.center().top();
        table.add(title).pad(14).row();
        table.add(play).pad(7).row();
        table.add(actSelection).pad(4).row();

        loadingTable.setFillParent(true);
        loadingTable.center().bottom();
        loadingTable.add(loading);

        stage.addActor(table);
        stage.addActor(loadingTable);
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));

        play.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.fadeIn(1f), Actions.fadeOut(1f))));
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new GameScreen(assets, game));
                    }
                })));
            }
        });
    }

    @Override
    public void step(float delta, float animationKeyFrame) {
        assets.update();

        if(assets.getProgress()<1)
            loading.setText("Loading "+(int)(assets.getProgress()*100f)+"%");
        else
            loading.setText("Loading Complete");

    }

    @Override
    public void postRender(float delta, float animationKeyFrame) {

    }

    @Override
    public void destroy() {
    }
}
