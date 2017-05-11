package com.dim.ui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/7/12.
 * @author dim
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建数据库时调用
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table user(username varchar(20) not null , " +
                "password varchar(60) not null , " +
                "remember_state boolean not null);";
        sqLiteDatabase.execSQL(sql);
    }

    //版本更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //getReadableDatabase() 	创建或打开一个只读数据库
    //getWritableDatabase() 	创建或打开一个读写数据库
}
