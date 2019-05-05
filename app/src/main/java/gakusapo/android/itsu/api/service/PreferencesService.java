package gakusapo.android.itsu.api.service;

import android.content.Context;
import android.content.SharedPreferences;
import gakusapo.android.itsu.MainApplication;

public class PreferencesService {

    private static SharedPreferences preferences;

    static {
        preferences = MainApplication.getContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    public static String getCurrentTimetable() {
        return preferences.getString("CurrentTimetable", null);
    }

    public static void setCurrentTimetable(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("CurrentTimetable", name);
        editor.apply();
    }

    public static boolean isEditing() {
        return preferences.getBoolean("Editing", false);
    }

    public static void setEditing(boolean editing) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Editing", editing);
        editor.apply();
    }

    public static String getEditdata() {
        return preferences.getString("EditData", null);
    }

    public static void setEditData(String data) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("EditData", data);
        editor.apply();
    }

    public static void removeEditData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("EditData");
        editor.apply();
    }

    public static int getReloadtime() {
        return preferences.getInt("ReloadTime", 16);
    }

    public static void setReloadTime(int time) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ReloadTime", time);
        editor.apply();
    }

}
