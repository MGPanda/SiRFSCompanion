package com.example.sirfscompanion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class MyDB {

    private MyDatabaseHelper dbHelper;

    private static SQLiteDatabase database;

    public final static String CHAR_TABLE = "characters"; // name of table

    public final static String CHAR_ID = "_id";
    public final static String CHAR_NAME = "name";
    public final static String CHAR_IMG = "img";
    public final static String CHAR_CRDATE = "crdate";
    public final static String CHAR_LEVEL = "level";
    public final static String CHAR_RACE = "race";
    public final static String CHAR_CLASS = "class";
    public final static String CHAR_FUE = "fue";
    public final static String CHAR_DES = "des";
    public final static String CHAR_PUN = "pun";
    public final static String CHAR_INT = "int";
    public final static String CHAR_SAB = "sab";
    public final static String CHAR_AGI = "agi";
    public final static String CHAR_VOL = "vol";
    public final static String CHAR_PV = "pv";
    public final static String CHAR_MAXPV = "maxpv";
    public final static String CHAR_PE = "pe";
    public final static String CHAR_MAXPE = "maxpe";
    public final static String CHAR_ARMOR = "armor";
    public final static String CHAR_MARMOR = "marmor";
    public final static String CHAR_CRITBONUS = "critbonus";
    public final static String CHAR_CRITDMGBONUS = "critdmgbonus";
    public final static String CHAR_SPELLBONUS = "spellbonus";
    public final static String CHAR_WEAPONS = "weapons";
    public final static String CHAR_EQUIP = "equip";
    public final static String CHAR_RELICS = "relics";
    public final static String CHAR_GOLD = "gold";
    public final static String CHAR_INVENTORY = "inventory";

    /**
     * @param context
     */
    public MyDB(Context context) {
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.onCreate(database);
    }

    public static long createChar(Char c) {
        ContentValues values = new ContentValues();
        values.put(CHAR_ID, nextId());
        values.put(CHAR_NAME, c.getCharName());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        c.getCharImg().compress(Bitmap.CompressFormat.JPEG, 0, baos);
        values.put(CHAR_IMG, baos.toByteArray());
        values.put(CHAR_CRDATE, c.getCharDate());
        values.put(CHAR_LEVEL, c.getCharLevel());
        values.put(CHAR_RACE, c.getCharRace());
        values.put(CHAR_CLASS, c.getCharClass());
        values.put(CHAR_FUE, c.getCharFue());
        values.put(CHAR_DES, c.getCharDes());
        values.put(CHAR_PUN, c.getCharPun());
        values.put(CHAR_INT, c.getCharInt());
        values.put(CHAR_SAB, c.getCharSab());
        values.put(CHAR_AGI, c.getCharAgi());
        values.put(CHAR_VOL, c.getCharVol());
        values.put(CHAR_PV, c.getCharPv());
        values.put(CHAR_MAXPV, c.getCharMaxpv());
        values.put(CHAR_PE, c.getCharPe());
        values.put(CHAR_MAXPE, c.getCharMaxpe());
        values.put(CHAR_ARMOR, c.getCharArmor());
        values.put(CHAR_MARMOR, c.getCharMarmor());
        values.put(CHAR_CRITBONUS, c.getCharCritbonus());
        values.put(CHAR_CRITDMGBONUS, c.getCharCritdmgbonus());
        values.put(CHAR_SPELLBONUS, c.getCharSpellbonus());
        values.put(CHAR_WEAPONS, c.getCharWeapons());
        values.put(CHAR_EQUIP, c.getCharEquip());
        values.put(CHAR_RELICS, c.getCharRelics());
        values.put(CHAR_GOLD, c.getCharGold());
        values.put(CHAR_INVENTORY, c.getCharInventory());
        return database.insert(CHAR_TABLE, null, values);
    }

    public static Cursor selectAll() {
        try {
            String[] cols = new String[]{CHAR_ID, CHAR_NAME, CHAR_IMG, CHAR_CRDATE, CHAR_LEVEL, CHAR_RACE, CHAR_CLASS, CHAR_FUE, CHAR_DES, CHAR_PUN, CHAR_INT, CHAR_SAB, CHAR_AGI, CHAR_VOL, CHAR_PV,
                    CHAR_MAXPV, CHAR_PE, CHAR_MAXPE, CHAR_ARMOR, CHAR_MARMOR, CHAR_CRITBONUS, CHAR_CRITDMGBONUS, CHAR_SPELLBONUS, CHAR_WEAPONS, CHAR_EQUIP, CHAR_RELICS, CHAR_GOLD, CHAR_INVENTORY};
            Cursor mCursor;
            mCursor = database.query(true, CHAR_TABLE, cols, null
                    , null, null, null, "_id asc", null);
            if (mCursor.getCount() == 0) return null;
            mCursor.moveToFirst();
            return mCursor;
        } catch (Exception e) {
            return null;
        }
    }

    public static int nextId() {
        try {
            String[] cols = new String[]{CHAR_ID};
            Cursor mCursor = database.query(true, CHAR_TABLE, cols, null
                    , null, null, null, "_id desc", null);
            mCursor.moveToFirst();
            int id = mCursor.getInt(0) + 1;
            return id;
        } catch (Exception e) {
            return 0;
        }

    }

    public void insertTest() {
        Char c = new Char(0,"Toni Taronges", BitmapFactory.decodeResource(MainActivity.get_ma().getResources(), R.drawable.hero),"2020-03-01",1,"Enano","Bárbaro",3,0,-2,-2,-1,-1,1,14,26,18,20,10,0,3,1,0,"","","",100,"");
        createChar(c);
    }

    /*public static long createRecords(int id, String name, String date, String isComplete) {
        if (id == 0) {
            id = nextId();
        }
        ContentValues values = new ContentValues();
        values.put(TODO_ID, id);
        values.put(TODO_NAME, name);
        values.put(TODO_DATE, date);
        values.put(TODO_ISCOMPLETE, isComplete);
        return database.insert(TODO_TABLE, null, values);
    }

    public static void insertTODO() {
        Cursor c = database.rawQuery("SELECT COUNT(*) FROM todolist", null);
        c.moveToFirst();
        int i = c.getInt(0);
        if (i == 0) {
            createRecords(1, "Fill Gas", "09/02/2017 23:02", "false");
            createRecords(2, "Call John", "09/02/2017 23:01", "false");
        }
    }
    public static int nextId() {
        String[] cols = new String[]{TODO_ID};
        Cursor mCursor = database.query(true, TODO_TABLE, cols, null
                , null, null, null, "_id desc", null);
        mCursor.moveToFirst();
        int id = mCursor.getInt(0)+1;
        return id;
    }
    public static Cursor selectRecords(boolean b) {
        String[] cols = new String[]{TODO_ID, TODO_NAME, TODO_DATE, TODO_ISCOMPLETE};
        Cursor mCursor;
        if (b) {
            mCursor = database.query(true, TODO_TABLE, cols, null
                    , null, null, null, "date asc", null);
        } else {
            mCursor = database.query(true, TODO_TABLE, cols, "iscomplete = ?"
                    , new String[]{"false"}, null, null, "date asc", null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    public static void deleteItem(int id) {
        database.delete(TODO_TABLE, "_id = ?", new String[]{String.valueOf(id)});
    }

    public static long completeItem(int id, String name, String date, String isComplete) {
        ContentValues values = new ContentValues();
        values.put(TODO_NAME, name);
        values.put(TODO_DATE, date);
        values.put(TODO_ISCOMPLETE, isComplete);
        return database.update(TODO_TABLE, values, "_id = ?", new String[]{String.valueOf(id)});
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }*/
}