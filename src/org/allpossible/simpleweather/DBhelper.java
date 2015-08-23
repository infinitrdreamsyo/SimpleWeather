package org.allpossible.simpleweather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "WeatherCityTable";

	public static final String CITY_ID = "_id";
	public static final String CITY_NAME = "name";
	public static final String CITY_COUNTRY = "country";
	public static final String CITY_LONGTITUDE = "long";
	public static final String CITY_LATITUDE = "lati";
	public static final String CITY_SUNRISE = "sunrise";
	public static final String CITY_SUNSET = "sunset";
	public static final String CITY_TEMPERATURE = "temperature";
	public static final String CITY_TEMP_MIN = "temp_min";
	public static final String CITY_TEMP_MAX = "temp_max";
	public static final String CITY_HUMIDITY = "humidity";
	public static final String CITY_PRESSURE = "pressure";
	public static final String CITY_SYSTEM_TIME = "systemtime";
	public static final String CITY_REFRESH_TIME = "refreshtime";
	public static final String CITY_WIND_SPEED = "windspeed";
	public static final String WEATHER_ID = "weather_id";
	public static final String WEATHER_MAIN = "weather_main";
	public static final String WEATHER_DESCRIPTION = "weather_desc";
	public static final String WEATHER_ICON_ID = "weather_icon";
	public static final String DATE = "date";
	public static final String TIME = "time";

	// DataBase Information

	static final String DATABASE_NAME = "WeatherData.db";
	static final int DATABASE_VERSION = 1;

	// table Creation Statement

	private static final String CREATE_TABLE = "create table " + TABLE_NAME
			+ "(" + CITY_ID + " INTEGER PRIMARY KEY UNIQUE, " + CITY_NAME
			+ " TEXT NOT NULL," + CITY_COUNTRY + " TEXT," + CITY_LONGTITUDE
			+ " TEXT," + CITY_LATITUDE + " TEXT," + CITY_SUNRISE + " TEXT,"
			+ CITY_SUNSET + " TEXT," + CITY_TEMPERATURE + " TEXT NOT NULL,"
			+ CITY_TEMP_MIN + " TEXT," + CITY_TEMP_MAX + " TEXT,"
			+ CITY_HUMIDITY + " TEXT," + CITY_PRESSURE + " TEXT,"
			+ CITY_SYSTEM_TIME + " TEXT," + CITY_REFRESH_TIME + " TEXT," + CITY_WIND_SPEED + " TEXT,"
			+ WEATHER_ID + " TEXT," + WEATHER_MAIN + " TEXT,"
			+ WEATHER_DESCRIPTION + " TEXT NOT NULL," + WEATHER_ICON_ID
			+ " TEXT," + DATE + " TEXT," + TIME + " TEXT " + ");";

	public DBhelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(db);
	}
}
