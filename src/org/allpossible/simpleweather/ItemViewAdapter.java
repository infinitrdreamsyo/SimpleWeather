package org.allpossible.simpleweather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemViewAdapter extends android.widget.CursorAdapter {

    LayoutInflater inflater;
    ImageView weathericon;
    TextView cityname;
    TextView cityid;
    TextView currenttime;
    TextView temperature;
    TextView weather_status;
    CheckBox selection;
    RelativeLayout list_item;

    boolean mIsVisibleCheckBox = false;
    boolean mIsChecked = false;
    Context mContext;

    public static ArrayList<Long> selectionStatusList = new ArrayList<Long>();

    ArrayList<String> weather_id = new ArrayList<String>();

    public ItemViewAdapter(Context context, Cursor c) {
        super(context, c);
        mContext = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public void bindView(View view, Context arg1, Cursor cursor) {
        weathericon = (ImageView) view.findViewById(R.id.weather_icon);
        cityname = (TextView) view.findViewById(R.id.citycountryname);
        currenttime = (TextView) view.findViewById(R.id.currenttime);
        temperature = (TextView) view.findViewById(R.id.temperature);
        weather_status = (TextView) view.findViewById(R.id.weatherstatus);
        selection = (CheckBox) view.findViewById(R.id.checkBox);
        cityid = (TextView) view.findViewById(R.id.cityid);
        list_item = (RelativeLayout) view.findViewById(R.id.layout_list_item);

        try {

            AppResource res = AppResource.getInstance();
            // selectionStatusList.clear();
            int weather_id = cursor.getInt(cursor
                    .getColumnIndex(DBhelper.WEATHER_ID));
            long sun_rise = cursor.getLong(cursor
                    .getColumnIndex(DBhelper.CITY_SUNRISE));
            long sun_set = cursor.getLong(cursor
                    .getColumnIndex(DBhelper.CITY_SUNSET));
            long system_time = cursor.getLong(cursor
                    .getColumnIndex(DBhelper.CITY_SYSTEM_TIME));
            String time = cursor
                    .getString(cursor.getColumnIndex(DBhelper.TIME));
            String date = cursor
                    .getString(cursor.getColumnIndex(DBhelper.DATE));

            long time_diff = Math.abs(System.currentTimeMillis() - system_time);

            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss.SSS");
            String inputString = time + ":00.000";

            Date mDate = null;
            try {

                mDate = sdf.parse(date + " " + inputString);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            long updated_time = mDate.getTime() + time_diff;

            SimpleDateFormat date_format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss.SSS");
            Date dateTime = new Date(updated_time);
            String resultDateTime = date_format.format(dateTime);

            long updated_sunrise = sun_rise - (system_time - mDate.getTime());
            long updated_sunset = sun_set - (system_time - mDate.getTime());

            ArrayList<Integer> recievedResult = res.Weather_Icon(weather_id,
                    updated_sunrise, updated_sunset,
                    resultDateTime.substring(10, 16).trim(), resultDateTime
                            .substring(0, 10).trim());
            if (recievedResult.get(0) == 0) {
                int mBlackColor = Color.parseColor("#FF00006A");
                list_item.setBackgroundColor(mBlackColor);

            } else {
                int mBlueColor = Color.parseColor("#FF00ABFF");
                list_item.setBackgroundColor(mBlueColor);
            }
            weathericon.setImageResource(recievedResult.get(1));

            if (cursor.getString(cursor.getColumnIndex(DBhelper.CITY_COUNTRY))
                    .length() == 2) {
                cityname.setText(cursor.getString(cursor.getColumnIndex("name"))
                        + ", "
                        + res.getCountryName(cursor.getString(cursor
                                .getColumnIndex(DBhelper.CITY_COUNTRY))));
            } else {
                cityname.setText(cursor.getString(cursor.getColumnIndex("name"))
                        + ", "
                        + cursor.getString(cursor
                                .getColumnIndex(DBhelper.CITY_COUNTRY)));
            }
            cityid.setText(cursor.getString(cursor
                    .getColumnIndex(DBhelper.CITY_ID)));

            java.util.Date gettime = new java.util.Date(updated_time);
            currenttime.setText(res.getProperTime(String.valueOf(gettime)
                    .substring(10, 16).trim(), mContext));

            temperature.setText(res.getProperTemperature(
                    cursor.getString(cursor.getColumnIndex("temperature")),
                    mContext));
            weather_status.setText(mContext.getResources().getString(
                    AppResource.weather_desc.get(weather_id)));
            selection.setTag(cursor.getLong(cursor.getColumnIndex("_id")));
            selection.setChecked(false);

            selection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                        boolean isChecked) {
                    if (isChecked == true)
                        selectionStatusList.add((Long) buttonView.getTag());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mIsVisibleCheckBox) {
            selection.setVisibility(View.VISIBLE);
            if (selectionStatusList.contains(selection.getTag())) {
                selection.setChecked(true);
            } else {
                selection.setChecked(false);
            }
        } else {
            selection.setVisibility(View.GONE);
        }

    }

    @Override
    public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.list_weather_row, null);
    }

    public void showCheckBox() {
        mIsVisibleCheckBox = true;
    }

    public void hideCheckBox() {
        mIsVisibleCheckBox = false;
        selectionStatusList.clear();
    }

    public boolean getCheckBoxStatus() {
        return mIsVisibleCheckBox;
    }

}
