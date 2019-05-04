package gakusapo.android.itsu.api.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gakusapo.android.itsu.db.DateEventDBHelper;
import gakusapo.android.itsu.entity.DateEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DateEventDBService {

    private DateEventDBHelper helper;
    private SQLiteDatabase db;

    public DateEventDBService(Context context) {
        if (helper == null) helper = new DateEventDBHelper(context);
        if (db == null) db = helper.getWritableDatabase();
    }

    public DateEvent getDateEvent(String date) {
        DateEvent event = null;
        Gson gson = new Gson();

        Cursor cursor = db.query(
                DateEventDBHelper.DB_TABLENAME,
                new String[]{"date", "memo", "homeworks", "submissions", "tests", "classes", "events", "reminders"},
                "date = ?",
                new String[]{date},
                null,
                null,
                null
        );

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            event = new DateEvent(cursor.getString(0));
            event.setMemo(cursor.getString(1));
            event.setHomeworks((List<String>) gson.fromJson(cursor.getString(2), new TypeToken<List>(){}.getType()));
            event.setSubmissions((List<String>) gson.fromJson(cursor.getString(3), new TypeToken<List>(){}.getType()));
            event.setTests((List<String>) gson.fromJson(cursor.getString(4), new TypeToken<List>(){}.getType()));
            event.setClasses((List<String>) gson.fromJson(cursor.getString(5), new TypeToken<List>(){}.getType()));
            event.setEvents((List<String>) gson.fromJson(cursor.getString(6), new TypeToken<List>(){}.getType()));
            event.setReminders((Map<String, Boolean>) gson.fromJson(cursor.getString(7), new TypeToken<Map<String, Boolean>>(){}.getType()));
        }

        return event;
    }

    public boolean addDateEvent(DateEvent event) {
        if (getDateEvent(event.getDate()) != null) return false;

        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put("date", event.getDate());
        values.put("memo", event.getMemo());
        values.put("homeworks", gson.toJson(event.getHomeworks()));
        values.put("submissions", gson.toJson(event.getSubmissions()));
        values.put("tests", gson.toJson(event.getTests()));
        values.put("classes", gson.toJson(event.getClasses()));
        values.put("events", gson.toJson(event.getEvents()));
        values.put("reminders", gson.toJson(event.getReminders()));

        db.insert(DateEventDBHelper.DB_TABLENAME, null, values);
        return true;
    }

    public boolean updateDateEvent(DateEvent event) {
        if (getDateEvent(event.getDate()) == null) return false;

        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put("date", event.getDate());
        values.put("memo", event.getMemo());
        values.put("homeworks", gson.toJson(event.getHomeworks()));
        values.put("submissions", gson.toJson(event.getSubmissions()));
        values.put("tests", gson.toJson(event.getTests()));
        values.put("classes", gson.toJson(event.getClasses()));
        values.put("events", gson.toJson(event.getEvents()));
        values.put("reminders", gson.toJson(event.getReminders()));

        db.update(DateEventDBHelper.DB_TABLENAME, values,  "date = '" + event.getDate() + "'", null);
        return true;
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String date;

        if (hour >= PreferencesService.get().getInt("ReloadTime", 16)) {
            date = String.format("%s/%s/%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE) + 1);
        } else {
            date = String.format("%s/%s/%s", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
        }

        try {
            return sdf.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isTomorrow() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= PreferencesService.get().getInt("ReloadTime", 16);
    }

}
