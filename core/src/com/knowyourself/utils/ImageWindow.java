package com.knowyourself.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.kotcrab.vis.ui.widget.VisWindow;

public class ImageWindow extends VisWindow{
    public ImageWindow(Color color) {
        super("");
        System.out.println(getColor());
        setColor(color);
    }
    public ImageWindow(Drawable background) {
        super("");
        System.out.println(getColor());
        setImage(background);
    }

    public void setImage(Drawable background){
        this.setBackground(background);
    }
}
