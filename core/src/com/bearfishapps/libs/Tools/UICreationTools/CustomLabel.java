package com.bearfishapps.libs.Tools.UICreationTools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bearfishapps.libs.Tools.FontGenerator;

public class CustomLabel {
    public static Label.LabelStyle style;

    public static void make(String fontPath, int size) {
        make(fontPath, size, Color.WHITE);
    }

    public static void make(String fontPath, int size, Color color) {
        make(size, color, fontPath);
    }

    public static void make(int size, Color color, String stringName) {
        FontGenerator.generate(size, stringName);
        style = new Label.LabelStyle(FontGenerator.returnFont(), color);
    }

    public static void make(BitmapFont font, Color color) {
        style = new Label.LabelStyle(font, color);
    }

    public static void dispose() {
        FontGenerator.dispose();
    }
}
