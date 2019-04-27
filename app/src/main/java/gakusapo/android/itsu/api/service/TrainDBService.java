package gakusapo.android.itsu.api.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import gakusapo.android.itsu.db.TrainDBHelper;
import gakusapo.android.itsu.entity.Train;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrainDBService {

    private TrainDBHelper helper;
    private SQLiteDatabase db;

    public TrainDBService(Context context) {
        helper = new TrainDBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void addTrain(String company, String name) {
        if (getTrains().containsKey(name)) return;
        ContentValues values = new ContentValues();
        values.put("company", company);
        values.put("name", name);
        db.insert(TrainDBHelper.DB_TABLENAME, null, values);
    }

    public Map<String, Train> getTrains() {
        Map<String, Train> result = new LinkedHashMap<>();
        Cursor cursor = db.query(
                TrainDBHelper.DB_TABLENAME,
                new String[]{"company", "name"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            result.put(cursor.getString(1), new Train(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }

        return result;
    }
}
