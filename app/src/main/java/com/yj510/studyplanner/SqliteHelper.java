package com.yj510.studyplanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

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
        db.execSQL("CREATE TABLE TodoList (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, " +
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

    public void UpdateData(String title, String _class, String date, String content, int complete, int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET title='"+title+"', _class='"+_class+"', content='"+content +"', date='" +
                date+"', complete='"+complete+"'"+ "WHERE id='"+_id+"';");
    }

    public void change_completed(int complete, int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET complete='"+complete+"'"+ "WHERE id='"+_id+"';");
    }

    public void DeleteData(int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE id ='"+_id+"';");
    }

    public ArrayList<TodoItemInfo> getTodoListContentData(int _id){
        ArrayList<TodoItemInfo> temp = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM TodoList WHERE id = '"+_id+"';",null);
        //cursor.moveToFirst();

        if (cursor.getCount() > 0){
            while( cursor.moveToNext()) {
                int idx;
                idx = cursor.getColumnIndex("id");
                int id = cursor.getInt(idx);
                idx = cursor.getColumnIndex("title");
                String title = cursor.getString(idx);
                idx = cursor.getColumnIndex("_class");
                String _class = cursor.getString(idx);
                idx = cursor.getColumnIndex("date");
                String date = cursor.getString(idx);
                idx = cursor.getColumnIndex("content");
                String content = cursor.getString(idx);
                idx = cursor.getColumnIndex("complete");
                int complete = cursor.getInt(idx);
                temp.add(new TodoItemInfo(id, title, date, _class, content, complete));

            }

        }
        cursor.close();
        return temp;
    }

}
