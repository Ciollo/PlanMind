package com.example.planmind;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AgendaDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agenda.db";
    private static final int DATABASE_VERSION = 1;

    public AgendaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_AGENDA_TABLE = "CREATE TABLE " +
                "agenda" + " (" +
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title" + " TEXT NOT NULL, " +
                "time" + " TEXT NOT NULL" +
                "); ";
        db.execSQL(SQL_CREATE_AGENDA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "agenda");
        onCreate(db);
    }


}