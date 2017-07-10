package com.knowyourself;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.ScrollableTextArea;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;

public class DialogueTextPlane extends VisWindow{
    Viewport vpReference;
    ScrollableTextArea textArea;

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

    public void setText(String str) {
        textArea.setText(str);
    }

    public void updateSize() {
        setSize(vpReference.getScreenWidth(), vpReference.getScreenHeight()/5);
    }

    private void addVisWidgets () {
        textArea = new ScrollableTextArea("This is an ordinary morning. You wake up at 7:20 and slip on your coat. Your foster fathers, Lawrence and Sol, are waiting for you just outside your room. Sol has his favourite grimace on. Sol used to be a military man, until he adopted you and ran away with Lawrence. Now your family hides in the small village of Fallow. The situation makes Sol grim for most of his days. Lawrence stays optimistic, at least outwardly. Sol is the one that greets you.");
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
