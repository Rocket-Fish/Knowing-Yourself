package com.knowyourself;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bearfishapps.libs.GeneralScreens;
import com.knowyourself.utils.AssetFinder;
import com.knowyourself.utils.ImageWindow;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.PopupMenu;

public class GameScreen extends GeneralScreens {

    private AssetManager assets;
    public GameScreen(Game game) {
        super(game);
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assets = new AssetManager(resolver);
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
    public void preShow(final Table table, InputMultiplexer multiplexer) {
        VisUI.load(VisUI.SkinScale.X1);
        table.add().expand().fill();
        System.out.println(assets.getAssetNames());

//        stage.addActor(new TestTextAreaAndScroll());
        DialogueTextPlane dtp = new DialogueTextPlane("", viewport);
        ImageWindow iw = new ImageWindow(new TextureRegionDrawable(new TextureRegion(assets.get("Characters/Player.jpg", Texture.class))));
        iw.setPosition(0, viewport.getScreenHeight()/5);
        stage.addActor(dtp);
        stage.addActor(iw);

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
