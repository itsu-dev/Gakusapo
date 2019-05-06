package dev.itsu.gakusapo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import dev.itsu.gakusapo.MainApplication;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_data.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TIMETABLE = "timetable";
    public static final String TABLE_DATEEVENT = "dateevent";
    public static final String TABLE_TRAIN = "train";

    private static final String SQL_TIMETABLE =
            "CREATE TABLE " + TABLE_TIMETABLE + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "data TEXT NOT NULL, " +
                    "dayType INTEGER NOT NULL, " +
                    "timeType INTEGER NOT NULL)";

    private static final String SQL_DATEEVENT =
            "CREATE TABLE " + TABLE_DATEEVENT + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "date TEXT NOT NULL, " +
                    "memo TEXT NOT NULL, " +
                    "homeworks TEXT NOT NULL, " +
                    "submissions TEXT NOT NULL, " +
                    "tests TEXT NOT NULL, " +
                    "classes TEXT NOT NULL, " +
                    "events TEXT NOT NULL, " +
                    "reminders TEXT NOT NULL)";

    private static final String SQL_TRAIN =
            "CREATE TABLE " + TABLE_TRAIN + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "company TEXT NOT NULL, " +
                    "name TEXT NOT NULL)";

    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ";

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TIMETABLE);
        db.execSQL(SQL_DATEEVENT);
        db.execSQL(SQL_TRAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE + TABLE_TIMETABLE);
        db.execSQL(SQL_DROP_TABLE + TABLE_DATEEVENT);
        db.execSQL(SQL_DROP_TABLE + TABLE_TRAIN);
        onCreate(db);
    }

    static class DatabaseHelperFactory {
        private static DatabaseHelper instance;

        static synchronized DatabaseHelper getInstance() {
            if (instance == null) instance = new DatabaseHelper(MainApplication.getContext(), DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION);
            return instance;
        }
    }

}
