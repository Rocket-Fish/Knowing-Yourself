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
/*
    public static void setBoolean(String name, boolean value) {
        prefs.putBoolean(name, value);
        prefs.flush();
    }

    public static boolean isTrue(String name) {
        return prefs.getBoolean(name);
    }
*/
    private static void checkString(String name) {
        if(!prefs.contains(name)) {
            prefs.putString(name,"0");
            prefs.flush();
        }
    }

    public static void setString(String name, String value) {
        checkString(name);
        prefs.putString(name, value);
        prefs.flush();
    }

    public static String getStringValue(String name) {
        checkString(name);
        return prefs.getString(name);
    }

    private static void checkInt(String name) {
        if(!prefs.contains(name)) {
            prefs.putInteger(name,0);
            prefs.flush();
        }
    }

    public static void setInteger(String name, int value) {
        checkInt(name);
        prefs.putInteger(name, value);
        prefs.flush();
    }

    public static int getIntValue(String name) {
        checkInt(name);
        return prefs.getInteger(name);
    }
}
