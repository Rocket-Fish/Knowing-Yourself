package com.knowyourself;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;

public class DialogueTextPlane extends VisWindow{
    Viewport vpReference;
    public DialogueTextPlane(String title, Viewport viewport) {
        super(title, false);
        vpReference = viewport;
        TableUtils.setSpacingDefaults(this);
        columnDefaults(0).left();
        addVisWidgets();

        setResizable(true);
        setSize(viewport.getScreenWidth(), viewport.getScreenHeight()/5);
        setPosition(0, 0);
//        setSize(sizeX, sizeY);
//        setPosition(228, 300);
        clearListeners();
        addListener(new GameScreen.WindowResizeListener() {
            @Override
            public void resize () {
                updateSize();
            }
        });
    }

    public void updateSize() {
        setSize(vpReference.getScreenWidth(), vpReference.getScreenHeight()/5);
    }

    private void addVisWidgets () {
        ScrollableTextArea textArea = new ScrollableTextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec iaculis odio.\nFun thing: This text area supports scrolling.");
        textArea.clearListeners();

//        textArea.setDisabled(true);
        //TODO: need to make the text uneditable

        // ---

        VisTable table = new VisTable();

        VisScrollPane scrollPane = new VisScrollPane(table);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);

        // ---

        add(textArea.createCompatibleScrollPane()).growX().growY().row();
        add(scrollPane).spaceTop(8).growX().growY().row();
    }

}
