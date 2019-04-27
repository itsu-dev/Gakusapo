package gakusapo.android.itsu.api.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import gakusapo.android.itsu.db.TimetableDBHelper;
import gakusapo.android.itsu.db.TrainDBHelper;
import gakusapo.android.itsu.entity.Timetable;
import gakusapo.android.itsu.utils.TimetableUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TimetableDBService {

    private TimetableDBHelper helper;
    private SQLiteDatabase db;

    public TimetableDBService(Context context) {
        if (helper == null) helper = new TimetableDBHelper(context);
        if (db == null) db = helper.getWritableDatabase();
    }

    public Map<String, Timetable> getTimetables() {
        Map<String, Timetable> result = new HashMap<>();
        Cursor cursor = db.query(
                TimetableDBHelper.DB_TABLENAME,
                new String[]{"name", "data", "dayType", "timeType"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            result.put(cursor.getString(0), new Timetable(
                    cursor.getString(0),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    TimetableUtils.jsonToSubjectList(cursor.getString(1))
            ));
            cursor.moveToNext();
        }

        return result;
    }

    public boolean addTimetable(Timetable timetable) {
        if (getTimetables().containsKey(timetable.getName())) return false;

        ContentValues values = new ContentValues();
        values.put("name", timetable.getName());
        values.put("data", TimetableUtils.listToSubjectJson(timetable.getSubjects()));
        values.put("dayType", timetable.getDayType());
        values.put("timeType", timetable.getTimeType());

        db.insert(TimetableDBHelper.DB_TABLENAME, null, values);
        return true;
    }

    public boolean updateTimetable(Timetable timetable) {
        if (!getTimetables().containsKey(timetable.getName())) return false;

        ContentValues values = new ContentValues();
        values.put("name", timetable.getName());
        values.put("data", TimetableUtils.listToSubjectJson(timetable.getSubjects()));
        values.put("dayType", timetable.getDayType());
        values.put("timeType", timetable.getTimeType());

        db.update(TimetableDBHelper.DB_TABLENAME, values,  "name = '" + timetable.getName() + "'", null);
        return true;
    }
}
