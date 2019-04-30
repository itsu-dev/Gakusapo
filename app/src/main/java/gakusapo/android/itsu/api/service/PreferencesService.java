package gakusapo.android.itsu.api.service;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesService {

    private static SharedPreferences preferences;

    public static void initialize(Context context) {
        if (preferences == null) preferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);

        /*
        List<String> trains = new ArrayList<>();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("CurrentTimetable", Timetable.DEFAULT_TIMETABLE_NAME);
        editor.putInt("ReloadTime", 16);
        editor.putString("WeatherCity", "none");
        editor.putString("Trains", new Gson().toJson(trains));
        editor.putString("EditData", Timetable);
        editor.putBoolean("Editing", false);
        */
    }

    public static SharedPreferences get() {
        return preferences;
    }

    public static SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

}
