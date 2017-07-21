package com.knowyourself.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.knowyourself.Constants;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.*;

public class DialogueTextPlane extends VisWindow {
    private Viewport vpReference;
    private ScrollableTextArea textArea;

    ////////////////////////////////
    //////////////////////////////// Important... Must Initialize These for the text plane to function properly
    ////////////////////////////////
    ////////////////////////////////


    public DialogueTextPlane(String title, Viewport viewport) {
        super(title, false);
        vpReference = viewport;
        TableUtils.setSpacingDefaults(this);
        columnDefaults(0).left();
        addVisWidgets();

        setResizable(true);
        setSize(viewport.getScreenWidth(), viewport.getScreenHeight() / 5);
        setPosition(0, 0);
//        setSize(sizeX, sizeY);
//        setPosition(228, 300);
        clearListeners();
        addListener(new GameScreen.WindowResizeListener() {
            @Override
            public void resize() {
                updateSize();
            }
        });

//        this.getTitleTable().padRight(30).padLeft(30);



    }
    private DialogueOnClickCallback callback;
    public void setClickCallBack(DialogueOnClickCallback d) {
        callback = d;
    }


    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////
    ////////////////////////////////

    private final float transitionTime = 0.2f;
    public void setTitle(String title) {
        this.getTitleLabel().addAction(Actions.sequence(Actions.fadeOut(transitionTime), Actions.run(new Runnable() {
            @Override
            public void run() {
                getTitleLabel().setText(title);
            }
        }) , Actions.fadeIn(transitionTime)));
    }

    public void setText(String str) {
        this.textArea.addAction(Actions.sequence(Actions.fadeOut(transitionTime), Actions.run(new Runnable() {
            @Override
            public void run() {
                textArea.setText(str);
            }
        }) , Actions.fadeIn(transitionTime)));
    }

    public void updateSize() {
        setSize(vpReference.getScreenWidth(), vpReference.getScreenHeight() / 5);
    }

    private void addVisWidgets() {
        textArea = new CustomScrollableTextArea("...");

        Color grey = new Color(Color.GRAY);
        grey.a = 0.1f;
        // Change Text Area style
        VisTextField.VisTextFieldStyle style = textArea.getStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.font));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont cas20  = generator.generateFont(parameter);
        style.font = cas20;
        style.fontColor = Color.WHITE;
//        style.background = new ColoredDrawable(grey);
        // Change speaker Title style
        Label.LabelStyle lStyle = this.getTitleLabel().getStyle();
        lStyle.font = cas20;
        lStyle.font.setColor(Color.WHITE);
//        lStyle.background = new ColoredDrawable(grey);
        getTitleLabel().setStyle(lStyle);
        // change background's background style
//        WindowStyle wStyle = this.getStyle();
//        wStyle.background = new ColoredDrawable(grey);
//        setStyle(wStyle);

//        Array<EventListener> listeners = textArea.getListeners();
        // make text uneditable by clearing the listeners
//        textArea.clearListeners();
        // add a click listener

        VisTable table = new VisTable();

        VisScrollPane scrollPane = new VisScrollPane(table);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);

        // ---

        add(textArea.createCompatibleScrollPane()).growX().growY().row();
        add(scrollPane).spaceTop(1).growX().fillY().row();
    }

    public class CustomScrollableTextArea extends ScrollableTextArea {
        public CustomScrollableTextArea(String text) {
            super(text);
        }

        @Override
        protected void drawCursor(Drawable cursorPatch, Batch batch, BitmapFont font, float x, float y) {

        }

        @Override
        protected InputListener createInputListener() {
            return new CustomScrollTextAreaListener();
        }

        public class CustomScrollTextAreaListener extends TextAreaListener {
            @Override
            protected void setCursorPosition(float x, float y) {
            }
            @Override
            protected void goHome (boolean jump) {
            }
            @Override
            protected void goEnd (boolean jump) {
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
                if(callback != null)
                    callback.onClick();
            }

            @Override
            public boolean keyDown (InputEvent event, int keycode) {
                return false;//super.keyDown(event, keycode);
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                return false;
            }
        }
    }

    public class ColoredDrawable implements Drawable{
        private ShapeRenderer renderer;
        private Color desiredColor;
        public ColoredDrawable(Color dc) {
            renderer = new ShapeRenderer();
            desiredColor = dc;
        }
        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            renderer.setProjectionMatrix(batch.getProjectionMatrix());
            batch.end();
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(desiredColor);
            renderer.rect(x, y, width, height);
            renderer.end();
            batch.begin();
        }

        @Override
        public float getLeftWidth() {
            return 0;
        }

        @Override
        public void setLeftWidth(float leftWidth) {

        }

        @Override
        public float getRightWidth() {
            return 0;
        }

        @Override
        public void setRightWidth(float rightWidth) {

        }

        @Override
        public float getTopHeight() {
            return 0;
        }

        @Override
        public void setTopHeight(float topHeight) {

        }

        @Override
        public float getBottomHeight() {
            return 0;
        }

        @Override
        public void setBottomHeight(float bottomHeight) {

        }

        @Override
        public float getMinWidth() {
            return 0;
        }

        @Override
        public void setMinWidth(float minWidth) {

        }

        @Override
        public float getMinHeight() {
            return 0;
        }

        @Override
        public void setMinHeight(float minHeight) {

        }
    }

    public interface DialogueOnClickCallback{
        void onClick();
    }

}
