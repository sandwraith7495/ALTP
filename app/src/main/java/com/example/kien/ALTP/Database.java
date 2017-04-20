package com.example.kien.ALTP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kien.ALTP.Object.ObjectHighScore;
import com.example.kien.ALTP.Object.ObjectQuestion;

import java.util.ArrayList;

/**
 * Created by KIEN on 5/15/2016.
 */
public class Database  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Database";

    private final String TABLE_NAME_SCORE = "tbl_score";
    private final String KEY_ID = "id";
    private final String KEY_NAME = "name";
    private final String KEY_SCORE = "score";

    private static final String TABLE_NAME_QUESTION = "tbl_question";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_TRUE = "t";
    private static final String KEY_FALSE_1 = "f1";
    private static final String KEY_FALSE_2 = "f2";
    private static final String KEY_FALSE_3 = "f3";
    private static final String KEY_LEVEL = "level";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME_SCORE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_SCORE + " INTEGER, "
                + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

        CREATE_TABLE = "CREATE TABLE " + TABLE_NAME_QUESTION + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUESTION + " TEXT,"
                + KEY_TRUE + " TEXT,"
                + KEY_FALSE_1 + " TEXT, "
                + KEY_FALSE_2 + " TEXT, "
                + KEY_FALSE_3 + " TEXT, "
                + KEY_LEVEL + " INTERGER"
                + " )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SCORE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_QUESTION);
        onCreate(db);
    }

    public long addHighScore(ObjectHighScore o) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, o.getScore());
        values.put(KEY_NAME, o.getName());
        long id = db.insert(TABLE_NAME_SCORE, null, values);
        db.close();
        return id;
    }

    public ArrayList<ObjectHighScore> getAllHighScore() {
        ArrayList<ObjectHighScore> arr = new ArrayList<ObjectHighScore>();
        String selectQuery = "SELECT  "
                + KEY_SCORE + ", "
                + KEY_NAME
                + " FROM " + TABLE_NAME_SCORE + " ORDER BY " + KEY_SCORE + " DESC, " + KEY_ID + " DESC LIMIT 10 OFFSET 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ObjectHighScore o = null;
        if (cursor.moveToFirst())
            do {
                o = new ObjectHighScore(cursor.getInt(0), cursor.getString(1));
                arr.add(o);
            } while (cursor.moveToNext());
        db.close();
        return arr;
    }

    public boolean checkBeforeAdd(ObjectHighScore o) {
        String selectQuery = "SELECT COUNT( " + KEY_ID + " ) FROM " + TABLE_NAME_SCORE + " WHERE " + KEY_SCORE + " >= " + o.getScore();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            int a = cursor.getInt(0);
            if (cursor.getInt(0) <= 10) {
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }

    public long addQuestion(ObjectQuestion o) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, o.getQuestion());
        values.put(KEY_TRUE, o.getT());
        values.put(KEY_FALSE_1, o.getF1());
        values.put(KEY_FALSE_2, o.getF2());
        values.put(KEY_FALSE_3, o.getF3());
        values.put(KEY_LEVEL, o.getLevel());
        long id = db.insert(TABLE_NAME_QUESTION, null, values);
        db.close();
        return id;
    }

    public ArrayList<ObjectQuestion> getQuestion(int level, int number, int[] ls) {
        ArrayList<ObjectQuestion> arr = new ArrayList<ObjectQuestion>();
        String selectQuery = "";
        if (ls == null)
            selectQuery = "SELECT  * FROM " + TABLE_NAME_QUESTION + " WHERE " + KEY_LEVEL + " = " + level;
        else {
            selectQuery = "SELECT  * FROM " + TABLE_NAME_QUESTION + " WHERE " + KEY_LEVEL + " = " + level + " AND " + KEY_ID + " NOT IN ( ";
            for(int i=0;i<ls.length;i++){
                if(i==ls.length-1) {
                    selectQuery += "'" + ls[i] + "' ) ";
                }
                else{
                    selectQuery+="'"+ls[i]+"' , ";
                }
            }
        }
        selectQuery+=" ORDER BY RANDOM() LIMIT " + number + " OFFSET 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ObjectQuestion o = null;
        if (cursor.moveToFirst())
            do {
                o = new ObjectQuestion(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getInt(6));
                arr.add(o);
            } while (cursor.moveToNext());
        db.close();
        return arr;
    }

}
