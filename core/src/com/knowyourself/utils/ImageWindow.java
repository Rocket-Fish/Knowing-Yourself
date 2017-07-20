package com.knowyourself.utils;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.knowyourself.Constants;
import com.kotcrab.vis.ui.widget.VisWindow;

public class ImageWindow extends VisWindow{
    private String imagePath = Constants.charDirectory+Constants.blank;

    public ImageWindow(Drawable background) {
        super("");
//        System.out.println(getColor());
//        setColor(0, 1, 0, 0.5f);

        setImage(background);
    }

    public void setFaded() {
        setColor(0.8f, 0.8f, 0.8f, 0.5f);
    }

    public void setNonFaded() {
        setColor(1, 1, 1, 1);
        setOnTop();
    }

    private void setImage(Drawable background){
        this.setBackground(background);
    }

    public void changeImage(String imagePath, Drawable background) {
        setImage(background);
        this.imagePath = imagePath;
    }

    public boolean isCurrentlyBlank() {
        return imagePath.equals(Constants.charDirectory+Constants.blank);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setOnTop() {
        this.setZIndex(this.getParent().getChildren().size+1);
    }
}

