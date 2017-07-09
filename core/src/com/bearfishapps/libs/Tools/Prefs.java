package com.bearfishapps.libs.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
    private static Preferences prefs;

    public static void init(String prefName) {
        prefs = Gdx.app.getPreferences(prefName);
/*        if (!prefs.contains(scoreName)) {
            prefs.putInteger(constants.scoreName, 0);
            prefs.flush();
        }
        */
    }

    public static void setBoolean(String name, boolean value) {
        prefs.putBoolean(name, value);
        prefs.flush();
    }

    public static boolean isTrue(String name) {
        return prefs.getBoolean(name);
    }

    public static void setInteger(String name, int value) {
        prefs.putInteger(name, value);
        prefs.flush();
    }

    public static int getIntValue(String name) {
        return prefs.getInteger(name);
    }
}
