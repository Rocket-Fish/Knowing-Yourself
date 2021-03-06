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
import com.knowyourself.Acts;
import com.knowyourself.Constants;
import com.knowyourself.utils.Text;

public class MainMenuScreen extends GeneralScreens {
    private Label title, play, actSelection, loading;
    private Table loadingTable, actSelectionTable;
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

        actSelectionTable = new Table();
        actSelectionTable.setVisible(false);
    }

    private boolean checkIfBlank(String text) {
        if(text.equals(" "))
            return true;
        return false;
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

        actSelectionTable.setFillParent(true);
        FontGenerator.generate(48, Constants.font);
        CustomLabel.make(FontGenerator.returnFont(), Color.BLACK);
        int i = 1;
        for(Acts act: Acts.values()) {
            if(checkIfBlank(assets.get(Constants.textDirectory+act.getName(), Text.class).getString()))
                continue;
            Label label = new Label(act.getName(), CustomLabel.style);
            label.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new GameScreen(assets, game, act.getName()));
                        }
                    })));
                }
            });
            actSelectionTable.add(label).pad(5);
            if(i%7 == 0)
                actSelectionTable.row();
            i++;
        }

        stage.addActor(loadingTable);
        stage.addActor(actSelectionTable);
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));

        play.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.fadeIn(1f), Actions.fadeOut(1f))));
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new GameScreen(assets, game, Acts.A1.getName()));
                    }
                })));
            }
        });

        actSelection.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        table.setVisible(false);
                        actSelectionTable.addAction(Actions.sequence(Actions.alpha(0), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                actSelectionTable.setVisible(true);
                            }
                        }), Actions.fadeIn(1f)));
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
