package com.knowyourself.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.knowyourself.Constants;
import com.kotcrab.vis.ui.widget.VisWindow;

import java.util.Timer;
import java.util.TimerTask;

public class ImageWindow extends VisWindow{
    private String imagePath = Constants.charDirectory+Constants.blank;

    public ImageWindow(Drawable background) {
        super("");
//        System.out.println(getColor());
//        setColor(0, 1, 0, 0.5f);

        setImage(background);
    }

    private final Color fadedColor = new Color(0.8f, 0.8f, 0.8f, 0.5f);
    private final Color fullColor = new Color(1, 1, 1, 1);
    private Color currentColor = new Color(0.8f, 0.8f, 0.8f, 0.5f);
    private final float totalTime = 0.4f;
    private final float clockRate = 1/100f;
    private final float removalInterval = 0.2f*clockRate;
    private final float alphaRemovalInterval = 0.5f*clockRate;
    public void setFaded() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        setColor(getColor().r-removalInterval, getColor().g-removalInterval, getColor().b-removalInterval, getColor().a-alphaRemovalInterval);
                        if(getColor().equals(fadedColor)) {
                            timer.cancel();
                            timer.purge();
                        }
                    }
                }, 0, (int)(totalTime*clockRate*1000));
    }

    public void setNonFaded() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        setColor(getColor().r+removalInterval, getColor().g+removalInterval, getColor().b+removalInterval, getColor().a+alphaRemovalInterval);
                        if(getColor().equals(fullColor)) {
                            timer.cancel();
                            timer.purge();
                        }
                    }
                }, 0, (int)(totalTime*clockRate*1000));

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

