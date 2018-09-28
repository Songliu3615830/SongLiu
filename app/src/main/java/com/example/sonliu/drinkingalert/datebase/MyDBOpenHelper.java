package com.example.sonliu.drinkingalert.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库构建类
 */
public class MyDBOpenHelper extends SQLiteOpenHelper {

    public MyDBOpenHelper(Context context) {
        super(context, "Drinking.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user_plan(" +
                "id INTEGER primary key autoincrement," +
                "type INTEGER," +
                "icon INTEGER," +
                "name VARCHAR(20)," +
                "day_drink INTEGER," +
                "is_alert INTEGER," +
                "type_alert INTEGER)"
        );

        db.execSQL("create table drink_record(" +
                "id INTEGER primary key autoincrement," +
                "uid INTEGER," +
                "num INTEGER," +
                "time INTEGER)");

        db.execSQL("create table alarm(" +
                "id INTEGER primary key autoincrement," +
                "uid INTEGER," +
                "time INTEGER)"
        );

        db.execSQL("create table cup(" +
                "id INTEGER primary key autoincrement," +
                "uid INTEGER," +
                "img INTEGER," +
                "num INTEGER)"
        );
        //默认有一条成人用户
        db.execSQL("insert into user_plan values(" +
                "null, 0, 0, null, 1800, 1, 3)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
