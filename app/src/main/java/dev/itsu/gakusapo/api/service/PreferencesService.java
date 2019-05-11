package dev.itsu.gakusapo.api.service;

import android.content.Context;
import android.content.SharedPreferences;
import dev.itsu.gakusapo.MainApplication;

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

    public static int getScheduleReloadTime() {
        return preferences.getInt("ScheduleReloadTime", 16);
    }

    public static void setScheduleReloadTime(int time) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ScheduleReloadTime", time);
        editor.apply();
    }

    public static int getNotificationtime() {
        return preferences.getInt("NotificationTime", 16);
    }

    public static void setNotificationTime(int time) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("NotificationTime", time);
        editor.apply();
    }

    public static long getTrainReloadTime() {
        return preferences.getLong("TrainReloadTime", 0);//unix
    }

    public static void setTrainReloadTime(long time) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("TrainReloadTime", time);
        editor.apply();
    }

    public static String getTrainCache() {
        return preferences.getString("TrainCache", null);
    }

    public static void setTrainCache(String json) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TrainCache", json);
        editor.apply();
    }

    public static int getNotificationId() {
        return preferences.getInt("NotificationId", 0);
    }

    public static void setNotificationId(int id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("NotificationId", id);
        editor.apply();
    }

}
