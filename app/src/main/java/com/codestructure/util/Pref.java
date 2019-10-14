package com.codestructure.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {

    private static SharedPreferences sharedPreferences = null;

    public static void openPref(Context context) {
        sharedPreferences = context.getSharedPreferences(Config.PREF_FILE,
                Context.MODE_PRIVATE);
    }

    public static String getValue(Context context, String key,
                                  String defaultValue) {
        Pref.openPref(context);
        String result = Pref.sharedPreferences.getString(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setValue(Context context, String key, String value) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putString(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static int getValue(Context context, String key, int defaultValue) {
        Pref.openPref(context);
        int result = Pref.sharedPreferences.getInt(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }
    public static boolean getValue(Context context, String key, boolean defaultValue) {
        Pref.openPref(context);
        boolean result = Pref.sharedPreferences.getBoolean(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }
    public static double getValue(Context context, String key, double defaultValue) {
        Pref.openPref(context);
        double result = Double.longBitsToDouble(Pref.sharedPreferences.getLong(key, Double.doubleToLongBits(defaultValue)));
        Pref.sharedPreferences = null;
        return result;
    }
    public static long getValue(Context context, String key, long defaultValue) {
        Pref.openPref(context);
        long result = Pref.sharedPreferences.getLong(key, Double.doubleToLongBits(defaultValue));
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setValue(Context context, String key, int value) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putInt(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }
    public static void setValue(Context context, String key, boolean value) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putBoolean(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }
    public static void setValue(Context context, String key, double value) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putLong(key, Double.doubleToRawLongBits(value));
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }
    public static void setValue(Context context, String key, long value) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putLong(key, Double.doubleToRawLongBits(value));
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static void clear(Context context ) {
        Pref.openPref(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}