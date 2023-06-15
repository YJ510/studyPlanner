package com.yj510.studyplanner;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "study_record.db";
    private static final int DATABASE_VERSION = 2;

    public SqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public SqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public SqliteHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, DATABASE_NAME, DATABASE_VERSION, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, " +
                "_class TEXT NOT NULL, date TEXT NOT NULL, content TEXT, complete INTEGER NOT NULL);");

        //db.execSQL("CREATE TABLE IF NOT EXISTS classList (name String PRIMARY KEY AUTOINCREMENT, cycle TEXT , color TEXT);");

        //db.execSQL("CREATE TABLE IF NOT EXISTS ReviewList (rw_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, rw_title TEXT NOT NULL, " +
                //"rw_class TEXT NOT NULL, rw_date TEXT NOT NULL,  rw_complete INTEGER NOT NULL, rw_question TEXT, rw_Answer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void InsertData(String title, String _class, String date, String content, int complete){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TodoList (title, _class, date, content, complete) "
                + "VALUES ("+ "'"+title+"','"+_class+"','"+date+"','"+content+"','" + complete+ "');");
    }
}
