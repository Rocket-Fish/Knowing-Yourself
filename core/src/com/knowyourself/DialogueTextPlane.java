package com.knowyourself;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;

import java.util.ArrayList;

public class DialogueTextPlane extends VisWindow {
    Viewport vpReference;
    ScrollableTextArea textArea;
    ArrayList<Dialogue> dialogues;

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
    }

    public void setDialogues(ArrayList<Dialogue> dialogues) {
        this.dialogues = dialogues;
    }

    public void setText(String str) {
        textArea.setText(str);
    }

    public void updateSize() {
        setSize(vpReference.getScreenWidth(), vpReference.getScreenHeight() / 5);
    }

    private int nextTextNum = 0;

    public void showNextText() {
        if (dialogues != null) {
            setText(dialogues.get(nextTextNum++).getContent());
        }
    }

    private void addVisWidgets() {
        textArea = new CustomScrollableTextArea("...");

        Array<EventListener> listeners = textArea.getListeners();
        // make text uneditable by clearing the listeners
//        textArea.clearListeners();
        // add a click listener


        VisTable table = new VisTable();

        VisScrollPane scrollPane = new VisScrollPane(table);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);

        // ---

        add(textArea.createCompatibleScrollPane()).growX().growY().row();
        add(scrollPane).spaceTop(8).growX().growY().row();
    }

    public class CustomScrollableTextArea extends ScrollableTextArea {
        public CustomScrollableTextArea(String text) {
            super(text);
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
                showNextText();
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



}
