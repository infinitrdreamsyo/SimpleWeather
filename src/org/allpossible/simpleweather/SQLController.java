package org.allpossible.simpleweather;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;

public class SQLController {

    private DBhelper dbhelper;
    private static SQLiteDatabase database;
    private Context ourcontext;
    Message msg;

    public SQLController(Context c) {
        ourcontext = c;
    }

    public SQLController open() throws SQLException {
        dbhelper = new DBhelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;

    }

    public void close() {
        dbhelper.close();
    }

    // We need to check_End

    // 1. Insert Data

    public void insertData(ContentValues cv) {

        try {
            database.insertOrThrow(DBhelper.TABLE_NAME, null, cv);
        } catch (SQLException e) {

            msg = Message.obtain();
            msg.arg1 = MainActivity.SQL_DUPLICATE_ENTRY;
            MainActivity.myHandler.sendMessage(msg);
            e.printStackTrace();
        }

    }

    // 2. Read Data

    public Cursor readData() {
        Cursor c = database.query(DBhelper.TABLE_NAME, null, null, null, null,
                null, DBhelper.CITY_SYSTEM_TIME + " DESC");
        if (c != null) {
            c.moveToNext();
        }
        return c;
    }

    public Cursor readData(boolean refresh) {
        Cursor c = database.query(DBhelper.TABLE_NAME, null, null, null, null,
                null, DBhelper.CITY_SYSTEM_TIME + " DESC");
        if (c != null) {
            c.moveToNext();
        }
        return c;
    }

    public static HashMap<String, String> readData(String row) {
        ArrayList<String> list = new ArrayList<String>();
        HashMap<String, String> dataList = new HashMap<String, String>();
        String selection = " _id = ? ";
        String[] selectionArgs = { row };
        Cursor c = database.query(DBhelper.TABLE_NAME, null, selection,
                selectionArgs, null, null, DBhelper.CITY_SYSTEM_TIME + " DESC");
        if (c != null) {
            c.moveToNext();
            dataList.put("weather_id",
                    c.getString(c.getColumnIndex("weather_id")));
            dataList.put("name", c.getString(c.getColumnIndex("name")));
            dataList.put("country", c.getString(c.getColumnIndex("country")));
            dataList.put("time", c.getString(c.getColumnIndex("time")));
            dataList.put("date", c.getString(c.getColumnIndex("date")));
            dataList.put("temperature",
                    c.getString(c.getColumnIndex("temperature")));

            dataList.put("temp_min", c.getString(c.getColumnIndex("temp_min")));
            dataList.put("temp_max", c.getString(c.getColumnIndex("temp_max")));

            dataList.put("weather_icon",
                    c.getString(c.getColumnIndex("weather_icon")));
            dataList.put("weather_main",
                    c.getString(c.getColumnIndex("weather_main")));
            dataList.put("weather_desc",
                    c.getString(c.getColumnIndex("weather_desc")));

            dataList.put("humidity", c.getString(c.getColumnIndex("humidity")));
            dataList.put("pressure", c.getString(c.getColumnIndex("pressure")));
            dataList.put("windspeed",
                    c.getString(c.getColumnIndex("windspeed")));

            dataList.put("sunrise", c.getString(c.getColumnIndex("sunrise")));
            dataList.put("sunset", c.getString(c.getColumnIndex("sunset")));

            dataList.put("long", c.getString(c.getColumnIndex("long")));
            dataList.put("lati", c.getString(c.getColumnIndex("lati")));
            dataList.put("systemtime",
                    c.getString(c.getColumnIndex("systemtime")));

        }
        return dataList;
    }

    // 3. UpdateData

    public int updateData(String row, ContentValues cvValues) {
        String selection = " _id = ? ";
        String[] selectionArgs = { row };

        int i = database.update(DBhelper.TABLE_NAME, cvValues, selection,
                selectionArgs);
        return i;
    }

    // 4. Delete Data

    public void deleteData(long cityId) {

        int count = database.delete(DBhelper.TABLE_NAME, DBhelper.CITY_ID + "="
                + cityId, null);

    }

    public void deleteDataMulti(ArrayList<String> rows) {
        int size = rows.size();
        String[] clause = new String[size];
        int i = 0;
        for (String s : rows) {
            clause[i] = s;
            i++;
        }

        database.delete(DBhelper.TABLE_NAME, DBhelper.CITY_ID + "=?", clause);

    }

    public long getMaxValuefromDb(String columnname) {
        long maxValue = 0;
        Cursor c = database.rawQuery("SELECT MAX(" + columnname + ") from "
                + DBhelper.TABLE_NAME, null);
        if (c != null) {
            c.moveToFirst();
            maxValue = c.getLong(0);
        }
        return maxValue;
    }
}
