package com.knowyourself.utils;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.kotcrab.vis.ui.widget.VisWindow;

public class ImageWindow extends VisWindow{
    public ImageWindow(Drawable background) {
        super("");
        System.out.println(getColor());
        this.setBackground(background);
    }
}
