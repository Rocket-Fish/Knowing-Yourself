package com.knowyourself.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.knowyourself.Constants;
import com.kotcrab.vis.ui.widget.VisWindow;

import java.util.Timer;
import java.util.TimerTask;

public class ImageWindow extends VisWindow{
    private String imagePath = Constants.charDirectory+Constants.blank;
    private boolean isCurrentlyFaded = true;
     private int colorBeingChangedBy = -1;

    public ImageWindow(Drawable background) {
        super("");
//        System.out.println(getColor());
//        setColor(0, 1, 0, 0.5f);
        setImage(background);
    }

    public boolean isCurrentlyFaded() {
        return isCurrentlyFaded;
    }

    private final Color fadedColor = new Color(0.8f, 0.8f, 0.8f, 0.5f);
    private final Color fullColor = new Color(1, 1, 1, 1);
    private final Color zeroColor = new Color(0.6f, 0.6f, 0.6f, 0.0f);
    private final float totalTime = 0.4f;
    private final float clockRate = 1/100f;
    private final float removalInterval = 0.2f*clockRate;
    private final float alphaRemovalInterval = 0.5f*clockRate;
    public void setFaded() {
        if(isCurrentlyFaded())
            return;

        isCurrentlyFaded = true;
//        currentTask;
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                int ID = 1;
                if(colorBeingChangedBy >0 && colorBeingChangedBy != ID) {
                    return;
                }
                colorBeingChangedBy = ID;
                setColor(getColor().r-removalInterval, getColor().g-removalInterval, getColor().b-removalInterval, getColor().a-alphaRemovalInterval);
                if(getColor().equals(fadedColor)) {
                    timer.cancel();
                    timer.purge();
                    colorBeingChangedBy = -1;
                }
            }
        }, 0, (int)(totalTime*clockRate*1000));
    }

    public void setNonFaded() {
        if(!isCurrentlyFaded)
            return;
        isCurrentlyFaded = false;
//        currentTask =
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        int ID = 2;
                        if(colorBeingChangedBy >0 && colorBeingChangedBy != ID) {
                            return;
                        }
                        colorBeingChangedBy = ID;
                        setColor(getColor().r+removalInterval, getColor().g+removalInterval, getColor().b+removalInterval, getColor().a+alphaRemovalInterval);
                        if(getColor().equals(fullColor)) {
                            timer.cancel();
                            timer.purge();
                            colorBeingChangedBy = -1;
                        }
                    }
                }, 0, (int)(totalTime*clockRate*1000));

        setOnTop();
    }

    private void setImage(Drawable background){
        this.setBackground(background);
    }

    public void changeImage(String imagePath, Drawable background) {
//        this.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.4f)));
        this.setImage(background);
        this.setColor(zeroColor);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        int ID = 3;
                        if(colorBeingChangedBy >0 && colorBeingChangedBy != ID) {
                            return;
                        }
                        colorBeingChangedBy = ID;
                        Color currC = getColor();
                            setColor(getColor().r + removalInterval, getColor().g + removalInterval, getColor().b + removalInterval, getColor().a + alphaRemovalInterval);
                            // Note that there is a bug due to floating point addition inaccuracies.
                            // after a certain amount of additions the inaccuacies stack up
                            // while faded color is 0xcccccc7f
                            // the set color will create 0xcccccc80. Thus the get color will not equal faded color
                            // going to just manually adjust that
                        if(getColor().a > fadedColor.a)
                            getColor().a = fadedColor.a;

                            if (getColor().equals(fadedColor)) {
                                timer.cancel();
                                timer.purge();
                                colorBeingChangedBy = -1;
                        }
                    }
                }, 0, (int)(totalTime*clockRate*1000));
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

