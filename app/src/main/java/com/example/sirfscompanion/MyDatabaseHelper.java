package com.example.sirfscompanion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDB";

    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "characters";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "+TABLE_NAME+"( _id integer primary key,img blob, name text, level integer, race text, class text, fue integer, des integer, pun integer, int integer, sab integer, agi integer, vol integer, pv integer, maxpv integer, pe integer, maxpe integer, armor integer, marmor integer, critbonus int, critdmgbonus int, spellbonus int, weapons text, equip text, relics text, rules text, gold text, inventory text);";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        database.execSQL(DATABASE_CREATE);

    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(database);
    }
}