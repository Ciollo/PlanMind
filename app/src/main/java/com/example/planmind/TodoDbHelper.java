package com.example.planmind;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Todo.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + "Todo" + " (" +
                    "id" + " INTEGER PRIMARY KEY," +
                    "completed" + " INTEGER," +
                    "description" + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + "Todo";

    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void updateTodoItem(int id, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        System.out.println(completed);
        if (completed) {
            values.put("completed", 1);
            System.out.println("Completed");
        } else {
            values.put("completed", 0);
            System.out.println(" no Completed");
        }

        String selection = "id" + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.update(
                "todo",
                values,
                selection,
                selectionArgs);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void deleteTodoItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        db.delete("todo", whereClause, whereArgs);
    }
}