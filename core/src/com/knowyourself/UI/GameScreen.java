package com.knowyourself.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bearfishapps.libs.GeneralScreens;
import com.knowyourself.Constants;
import com.knowyourself.Plot.Choice;
import com.knowyourself.Plot.Dialogue;
import com.knowyourself.Plot.PlotManager;
import com.knowyourself.utils.Text;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.PopupMenu;

import java.util.ArrayList;

public class GameScreen extends GeneralScreens {

    private ArrayList<Choice> plotChoices;
    private ArrayList<Dialogue> listofDialogues;
    public GameScreen(AssetManager assets, Game game) {
        super(assets, game);

        VisUI.load();
        setBackgroundColor(255, 255, 255, 1);

        plotChoices = new ArrayList<Choice>();
        listofDialogues = new ArrayList<Dialogue>();
        String unparsed[] = assets.get( Constants.textDirectory+Constants.A1, Text.class ).getString().split("\n");
        int prevLine = -1;
        for(String upar: unparsed) {
            if(upar.contains("[sel:")) {
                Choice c = Choice.choiceParser(prevLine, upar);
                plotChoices.add(c);
            } else {
                Dialogue d = Dialogue.dialogueParser(upar);
                listofDialogues.add(d);
                prevLine = d.getId();
            }
        }

    }

    @Override
    public void step(float delta, float animationKeyFrame) {

    }

    @Override
    public void postRender(float delta, float animationKeyFrame) {

    }

    private ImageWindow createBlankWindow(int position) {
        ImageWindow iw = new ImageWindow(new TextureRegionDrawable(new TextureRegion(assets.get(Constants.charDirectory+ Constants.blank, Texture.class))));
        int width = viewport.getScreenHeight()/2;
        iw.setWidth(width);
        iw.setHeight(width);

        int x;
        int pos1 = viewport.getScreenWidth()/4-width/2, pos2 = viewport.getScreenWidth()/4*3-width/2, variance = viewport.getScreenWidth()/12;

        switch (position) {
            default:
                x = pos1;
                break;
            case 1:
                x = pos1-variance;
                break;
            case 2:
                x = pos1+variance/3*2;
                break;
            case 3:
                x = pos2;
                break;
            case 4:
                x = pos2+variance;
                break;
            case 5:
                x = pos2-variance/3*2;
                break;
        }

        iw.setPosition(x, viewport.getScreenHeight()/5);

        return iw;
    }

    @Override
    public void preShow(final Table table, InputMultiplexer multiplexer) {
        table.add().expand().fill();
        System.out.println(assets.getAssetNames());

//        stage.addActor(new TestTextAreaAndScroll());
        DialogueTextPlane dtp = new DialogueTextPlane("", viewport);

        ImageWindow[] iw = {createBlankWindow(0),createBlankWindow(1),createBlankWindow(2),createBlankWindow(3),createBlankWindow(4),createBlankWindow(5)};

        PlotManager manager = new PlotManager(dtp, plotChoices, listofDialogues);
        dtp.setClickCallBack(manager);
        manager.attachImageWindows(iw, assets);

        stage.addActor(dtp);
        for(ImageWindow i: iw) {
            stage.addActor(i);
        }

        stage.addListener(new InputListener() {
            boolean debug = false;

            @Override
            public boolean keyDown (InputEvent event, int keycode) {
                if (keycode == Input.Keys.F12) {
                    debug = !debug;
                    table.setDebug(debug, true);
                    for (Actor actor : stage.getActors()) {
                        if (actor instanceof Group) {
                            Group group = (Group) actor;
                            group.setDebug(debug, true);
                        }
                    }
                    return true;
                }

                return false;
            }
        });

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));
    }

    @Override
    public void destroy() {
        VisUI.dispose();
    }

    @Override
    public void resize (int width, int height) {
        if (width == 0 && height == 0) return;
        stage.getViewport().update(width, height, true);

        PopupMenu.removeEveryMenu(stage);
        WindowResizeEvent resizeEvent = new WindowResizeEvent();
        for (Actor actor : stage.getActors()) {
            actor.fire(resizeEvent);
        }
    }

    abstract static class WindowResizeListener implements EventListener {
        @Override
        public boolean handle (Event event) {
            if (event instanceof WindowResizeEvent == false) return false;
            resize();
            return false;
        }

        public abstract void resize ();
    }

    class WindowResizeEvent extends Event {
    }

}
