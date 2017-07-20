package com.knowyourself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bearfishapps.libs.GeneralScreens;
import com.knowyourself.Plot.Choice;
import com.knowyourself.Plot.Dialogue;
import com.knowyourself.Plot.PlotManager;
import com.knowyourself.utils.AssetFinder;
import com.knowyourself.utils.ImageWindow;
import com.knowyourself.utils.Text;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.PopupMenu;

import java.util.ArrayList;
import java.util.HashSet;

public class GameScreen extends GeneralScreens {

    private ArrayList<Choice> plotChoices;
    private ArrayList<Dialogue> listofDialogues;
    private HashSet<Dialogue> setofDialogues;
    private AssetManager assets;
    public GameScreen(Game game) {
        super(game);
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assets = new AssetManager(resolver);
        AssetFinder assetFinder = new AssetFinder(assets, resolver);
        assetFinder.load();
        assets.finishLoading();

        plotChoices = new ArrayList<Choice>();
        listofDialogues = new ArrayList<Dialogue>();
        setofDialogues = new HashSet<Dialogue>();
        String unparsed[] = assets.get( Constants.textDirectory+Constants.A1, Text.class ).getString().split("\n");
        int prevLine = -1;
        for(String upar: unparsed) {
            if(upar.contains("[sel:")) {
                Choice c = Choice.choiceParser(prevLine, upar);
                plotChoices.add(c);
            } else {
                Dialogue d = Dialogue.dialogueParser(upar);
                listofDialogues.add(d);
                setofDialogues.add(d);
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

    @Override
    public void preShow(final Table table, InputMultiplexer multiplexer) {
        VisUI.load(VisUI.SkinScale.X1);
        table.add().expand().fill();
        System.out.println(assets.getAssetNames());

//        stage.addActor(new TestTextAreaAndScroll());
        DialogueTextPlane dtp = new DialogueTextPlane("", viewport);

        ImageWindow iw = new ImageWindow(Color.WHITE);
//        ImageWindow iw = new ImageWindow(
        iw.setPosition(0, viewport.getScreenHeight()/5);
        iw.setWidth(viewport.getScreenHeight()/2);
        iw.setHeight(viewport.getScreenHeight()/2);

        ImageWindow iw2 = new ImageWindow(Color.WHITE);
        iw2.setPosition(viewport.getScreenWidth()-viewport.getScreenHeight()/2, viewport.getScreenHeight()/5);
        iw2.setWidth(viewport.getScreenHeight()/2);
        iw2.setHeight(viewport.getScreenHeight()/2);

        dtp.attachImageWindows(iw, iw2, assets);


        stage.addActor(dtp);
        stage.addActor(iw);
        stage.addActor(iw2);

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

        // This needs to be at the very very end.
        PlotManager manager = new PlotManager(dtp, plotChoices, listofDialogues, setofDialogues);
        dtp.setClickCallBack(manager);
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
