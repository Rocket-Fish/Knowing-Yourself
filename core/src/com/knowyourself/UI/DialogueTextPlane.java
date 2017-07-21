package com.knowyourself.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.Viewport;
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


    public void setTitle(String title) {
        this.getTitleLabel().setText(title);
    }

    public void setText(String str) {
        textArea.setText(str);
    }

    public void updateSize() {
        setSize(vpReference.getScreenWidth(), vpReference.getScreenHeight() / 5);
    }

    private void addVisWidgets() {
        textArea = new CustomScrollableTextArea("...");

        // Change Text Area Font
        VisTextField.VisTextFieldStyle style = textArea.getStyle();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("CasablancaAntique.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        BitmapFont cas16  = generator.generateFont(parameter);
        style.font = cas16;
        // Change speaker Title Font
        Label.LabelStyle lStyle = this.getTitleLabel().getStyle();
        lStyle.font = cas16;
        getTitleLabel().setStyle(lStyle);

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

    public interface DialogueOnClickCallback{
        void onClick();
    }

}
