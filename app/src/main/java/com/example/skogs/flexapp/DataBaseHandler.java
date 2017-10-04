package com.example.skogs.flexapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by skogs on 2017-08-20.
 */
public class DataBaseHandler extends SQLiteOpenHelper{
    private SimpleDateFormat df;
    private SimpleDateFormat tf;
    private SimpleDateFormat mf;
    private SQLiteDatabase db = this.getWritableDatabase();
    private static final String DATABASE_NAME = "Flex.db";
    private static final int DATABASE_VERSION = 1;
    //flex table
    private static final String FLEX_TABLE = "Flex";
    private static final String DATE = "date";
    private static final String MONTH = "month";
    private static final String IN_TIME = "inTime";
    private static final String OUT_TIME = "outTime";
    private static final String LUNCH_TIME = "lunchTime";
    private static final String WORKING_TIME = "workingTime";
    private static final String SICKNESS = "sickness";
    private static final String FLEX = "flex";//flex for the day

    private static final String FLEX_TABLE_CREATE =
            "CREATE TABLE " + FLEX_TABLE + " (" +
                    DATE + " TEXT PRIMARY KEY, " + MONTH + " INTEGER, " + IN_TIME + " TEXT, " +
                    OUT_TIME + " TEXT, " + LUNCH_TIME + " TEXT, " + WORKING_TIME + " TEXT, " +
                    SICKNESS + "INTEGER," + FLEX + " INTEGER, " + ")";

    public DataBaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        df = new SimpleDateFormat("yyyy-MM-dd");
        tf = new SimpleDateFormat("HH:mm");
        mf = new SimpleDateFormat("yyyyMM");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(FLEX_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //used for updating the database
        db.execSQL("DROP TABLE IF EXISTS " + FLEX_TABLE);
        onCreate(db);
    }

    public boolean insertDay(Date flexDate, Date inTime, Date outTime, int lunchTime, Date workingTime, int sickness, int flex){
        boolean isInserted = false;
        //insert in flextable
        ContentValues cv = new ContentValues();
        cv.put(DATE, df.format(flexDate));
        cv.put(MONTH, Integer.valueOf(mf.format(flexDate)));
        cv.put(IN_TIME, tf.format(inTime));
        cv.put(OUT_TIME, tf.format(outTime));
        cv.put(LUNCH_TIME, lunchTime);
        cv.put(WORKING_TIME, tf.format(workingTime));
        cv.put(SICKNESS, sickness);
        cv.put(FLEX, flex); //uträknad flex för den dagen
        //long diffInMillies = date2.getTime() - date1.getTime();
        //return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS)
        Cursor cursor = null;
        String sql ="SELECT DATE FROM "+FLEX_TABLE+" WHERE DATE="+df.format(flexDate);
        cursor= db.rawQuery(sql,null);
        if(cursor.getCount()>0){
            int updateResult = db.update(FLEX_TABLE, cv,"DATE=" + df.format(flexDate), null);
            if(updateResult > 0){
                isInserted = true;
            }
        }else{
            long result = db.insert(FLEX_TABLE, null, cv);
            if(result != -1){
                isInserted = true;
            }
        }
        cursor.close();
        return isInserted;
    }

    private Cursor getTimeList(String startTime, String endTime){
        Cursor cursor = db.rawQuery("SELECT * FROM "+ FLEX_TABLE +
                " WHERE " + DATE + " BETWEEN '" + startTime + "' AND '" + endTime + "'", null);
        return cursor;
    }

    protected void emptyTables(){
        db.delete(FLEX_TABLE, null, null);
    }

    protected void closeDB(){
        db.close();
    }
}
