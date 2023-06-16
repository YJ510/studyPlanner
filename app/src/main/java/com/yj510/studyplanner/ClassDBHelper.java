package com.yj510.studyplanner;

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

public class ClassDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "class_record.db";
    private static final int DATABASE_VERSION = 2;

    public ClassDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public ClassDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public ClassDBHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, DATABASE_NAME, DATABASE_VERSION, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE ReviewList (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        //        "task_id INTEGER NOT NULL, name TEXT NOT NULL, date TEXT NOT NULL, " +
         //               "_class TEXT NOT NULL, question TEXT NOT NULL, answer TEXT NOT NULL);" );

        db.execSQL("CREATE TABLE classList (name TEXT PRIMARY KEY, cycle TEXT NOT NULL, hex_color TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void InsertClass(String name, String cycle, String hex_color){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO classList (name, cycle, hex_color) "
                + "VALUES ("+ "'"+name+"','"+cycle+"','"+hex_color+ "');");
    }

    public void UpdateClass(String name, String cycle, String hex_color){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE classList SET cycle='" + cycle+"', hex_color='"+ hex_color+"' WHERE name='"+name+"';");
    }

    public void DeleteClass(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM classList WHERE name ='"+name+"';");
    }

    public ArrayList<ClassInfo> getClassData(String name){
        ArrayList<ClassInfo> temp = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM classList WHERE name = '"+name+"';",null);
        //cursor.moveToFirst();

        if (cursor.getCount() > 0){
            while( cursor.moveToNext()) {
                int idx;
                idx = cursor.getColumnIndex("name");
                String _name = cursor.getString(idx);
                idx = cursor.getColumnIndex("cycle");
                String cycle = cursor.getString(idx);
                idx = cursor.getColumnIndex("hex_color");
                String hex_color = cursor.getString(idx);

                temp.add(new ClassInfo(_name, cycle, hex_color));
            }
        }else {
            temp.add(new ClassInfo("기본", "0", "#000000"));
        }

        cursor.close();
        return temp;
    }

    public ArrayList<ClassInfo> getAllClassData(){
        ArrayList<ClassInfo> temp = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM classList;",null);
        //cursor.moveToFirst();

        if (cursor.getCount() > 0){
            while( cursor.moveToNext()) {
                int idx;
                idx = cursor.getColumnIndex("name");
                String name = cursor.getString(idx);
                idx = cursor.getColumnIndex("cycle");
                String cycle = cursor.getString(idx);
                idx = cursor.getColumnIndex("hex_color");
                String hex_color = cursor.getString(idx);

                temp.add(new ClassInfo(name, cycle, hex_color));
            }

        }
        cursor.close();
        return temp;
    }

    public ArrayList<String> getAllClassNameData(){
        ArrayList<String> temp = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM classList;",null);
        //cursor.moveToFirst();

        if (cursor.getCount() > 0){
            while( cursor.moveToNext()) {
                int idx;
                idx = cursor.getColumnIndex("name");
                String name = cursor.getString(idx);

                temp.add(name);
            }

        }
        cursor.close();
        return temp;
    }

    public String getColor(String name){
        String color=null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT hex_color FROM classList WHERE name='"+name+"';",null);
        //cursor.moveToFirst();

        if (cursor.getCount() > 0){
            while( cursor.moveToNext()) {
                String hex_color = cursor.getString(0);
                color=hex_color;
            }
        }
        cursor.close();
        return color;
    }

}
