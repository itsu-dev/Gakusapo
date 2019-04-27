package gakusapo.android.itsu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrainDBHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DB_NAME = "trains.db";
    public static final String DB_TABLENAME = "train";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + DB_TABLENAME + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "company TEXT NOT NULL, " +
            "name TEXT NOT NULL)";

    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + DB_TABLENAME;

    public TrainDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
