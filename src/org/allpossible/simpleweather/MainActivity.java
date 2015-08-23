package org.allpossible.simpleweather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

    private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    private ListView lv;
    private SQLController dbcon;
    private TextView memID_tv, memName_tv;
    private ItemViewAdapter itemAdapter;
    private Cursor cursor;
    private HandleJSON JSONobj;
    private String finalUrl;
    private String name = "";
    private String country = "";
    private LinearLayout detailed_view;
    private Menu action_menu;
    boolean listvisible = true;
    boolean refresh = false;
    int refresh_cities = 0;
    ListView countryListView = null;
    public static ProgressDialog progress;
    public static Handler myHandler;
    static public final int JSON_CONNECTION_SUCCESS = 100;
    static public final int JSON_CONNECTION_FAIL = 101;
    static public final int HTTP_CONNECTION_FAIL = 102;
    static public final int SQL_DUPLICATE_ENTRY = 103;
    static public final int DATA_REFRESHED = 104;
    static private final int MAX_ENTRIES = 10;

    protected static final int RESULT_SPEECH_CITY = 1;
    protected static final int RESULT_SPEECH_COUNTRY = 2;
    private static final long MIN_REFRESH_TIME = 1800000L;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    final Context context = this;

    TextView m_city_name;
    ImageView flag;
    TextView m_country;
    TextView m_time;
    TextView m_date;
    TextView m_temperature;
    TextView m_min_max_temperature;
    ImageView m_icon;
    TextView m_weather;
    TextView m_weather_desc;
    TextView m_humidity;
    TextView m_pressure;
    TextView m_windspeed;
    TextView m_sun_title;
    TextView m_sun_rise;
    TextView m_sun_set;
    TextView m_cord_title;
    TextView m_latitude;
    TextView m_longtitude;
    TextView last_refreshed_time;
    ActionBar action_bar;

    SharedPreferences prefs;

    TextToSpeech ttobj;
    static String Speak_city_name;
    static String Speak_country_name;
    static boolean isFoundCity = false;
    String country_name_selected;
    static Context mContext;
    static android.content.res.Resources mAppResources;
    static View toast_layout;
    static TextView toast_message;

    static ImageView toast_icon;
    private final String VALID_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";

    AutoCompleteTextView country_auto_list;
    ArrayList<Integer> danger_weather_id = new ArrayList<Integer>() {
        {
            add(202);
            add(212);
            add(221);
            add(502);
            add(503);
            add(504);
            add(531);
            add(602);
            add(622);
            add(781);
            add(900);
            add(901);
            add(902);
            add(960);
            add(961);
            add(962);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = getLayoutInflater();
        toast_layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout));
        toast_message = (TextView) toast_layout
                .findViewById(R.id.message_toast);
        toast_icon = (ImageView) toast_layout.findViewById(R.id.toast_icon);

        try {
            action_bar = getActionBar();
            action_bar.setCustomView(R.layout.action_bar_layout);
            action_bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        } catch (Exception e) {

        }

        mContext = getApplicationContext();
        mAppResources = getResources();
        if (!isnetworkAvailable()) {
            displayToast(R.string.no_data_connection, R.drawable.oops);
        }
        dbcon = new SQLController(this);
        dbcon.open();

        try {
            long last_refreshed = dbcon
                    .getMaxValuefromDb(DBhelper.CITY_REFRESH_TIME);
            last_refreshed_time = (TextView) action_bar.getCustomView()
                    .findViewById(R.id.last_refreshed);
            last_refreshed_time.setText(getResources().getString(
                    R.string.last_refreshed)
                    + " " + formattedDateForAction(last_refreshed));

            SharedPreferences pref = PreferenceManager
                    .getDefaultSharedPreferences(this);
            if (pref.getBoolean("isfirstTime", true)) {
                last_refreshed_time.setVisibility(View.GONE);
            }

        } catch (Exception e) {

        }

        lv = (ListView) findViewById(R.id.memberList_id);
        detailed_view = (LinearLayout) findViewById(R.id.detailed_view);

        m_city_name = (TextView) findViewById(R.id.m_city_name);
        m_country = (TextView) findViewById(R.id.m_country);
        flag = (ImageView) findViewById(R.id.country_flag);
        m_time = (TextView) findViewById(R.id.m_time);
        m_date = (TextView) findViewById(R.id.m_date);
        m_temperature = (TextView) findViewById(R.id.m_temperature);
        m_min_max_temperature = (TextView) findViewById(R.id.m_min_max_temperature);
        m_icon = (ImageView) findViewById(R.id.m_icon);
        m_weather_desc = (TextView) findViewById(R.id.m_weather_desc);
        m_humidity = (TextView) findViewById(R.id.m_humidity);
        m_pressure = (TextView) findViewById(R.id.m_pressure);
        m_windspeed = (TextView) findViewById(R.id.m_windspeed);
        m_sun_rise = (TextView) findViewById(R.id.m_sun_rise);
        m_sun_set = (TextView) findViewById(R.id.m_sun_set);
        m_latitude = (TextView) findViewById(R.id.m_latitude);
        m_longtitude = (TextView) findViewById(R.id.m_longtitude);

        progress = new ProgressDialog(this);
        refreshAllData(0);

        myHandler = new Handler() {
            public void handleMessage(Message msg) {

                switch (msg.arg1) {
                case JSON_CONNECTION_SUCCESS:
                    progress.dismiss();

                    if (HandleJSON.city.length() == 0) {
                        displayToast(R.string.not_a_city, R.drawable.oops);
                    } else {
                        insertJSONData();
                    }
                    refreshList();
                    break;
                case JSON_CONNECTION_FAIL:
                    progress.dismiss();
                    displayToast(R.string.city_not_found, R.drawable.oops);
                    break;
                case HTTP_CONNECTION_FAIL:
                    progress.dismiss();
                    displayToast(R.string.no_data_connection, R.drawable.oops);
                    break;

                case SQL_DUPLICATE_ENTRY:
                    progress.dismiss();
                    displayToast(R.string.city_exists, R.drawable.oops);
                    break;

                case DATA_REFRESHED:
                    refreshList();
                    break;
                default:
                    break;

                }

            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbcon.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        action_menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        case R.id.action_add:
            if (itemAdapter != null) {
                itemAdapter.hideCheckBox();
                itemAdapter.notifyDataSetChanged();
            }
            if (isnetworkAvailable()) {
                if (itemAdapter.getCount() == MAX_ENTRIES)
                    displayToast(R.string.max_cities_error, R.drawable.oops);
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            context);
                    LayoutInflater li = LayoutInflater.from(context);
                    View textEntryView = li.inflate(
                            R.layout.enter_city_country, null);
                    builder.setView(textEntryView);

                    /*
                     * InputMethodManager imm =
                     * (InputMethodManager)getSystemService
                     * (Context.INPUT_METHOD_SERVICE); InputMethodSubtype ims =
                     * imm.getCurrentInputMethodSubtype();
                     * 
                     * String locale = ims.getLocale(); Locale l = new
                     * Locale(locale); String currentlan =
                     * l.getDefault().getDisplayLanguage();
                     * Toast.makeText(getApplicationContext(), currentlan,
                     * Toast.LENGTH_LONG).show();
                     */
                    
                    /*try{
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        InputMethodSubtype ims = imm.getCurrentInputMethodSubtype();
                        System.out.println(ims.getLocale());
                    } catch(Exception e){
                        e.printStackTrace();
                    }*/
                    final EditText city = (EditText) textEntryView
                            .findViewById(R.id.city_name);
                    city.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                int before, int count) {
                            try{
                                if(!VALID_CHAR.contains(Character.toString((s.charAt(start))))){
                                    city.setText(s.subSequence(0, start));
                                    city.setSelection(city.length());
                                    Toast.makeText(mContext, getString(R.string.language_not_supported), Toast.LENGTH_LONG).show();
                                }    
                            }
                            catch(Exception e){
                                
                            }
                        }

                        @Override
                        public void beforeTextChanged(CharSequence s,
                                int start, int count, int after) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            countryListView.setVisibility(View.VISIBLE);
                        }
                    });
                    final CountryListAdapter adapter = new CountryListAdapter(
                            mContext, mContext.getResources().getStringArray(
                                    R.array.country_array));
                    final EditText countryName = (EditText) textEntryView
                            .findViewById(R.id.country_name);
                    countryListView = (ListView) textEntryView
                            .findViewById(R.id.datalist);
                    countryListView.setVisibility(View.VISIBLE);
                    countryListView.setAdapter(adapter);
                    countryListView
                            .setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapter,
                                        View v, int position, long arg3) {
                                    country_name_selected = ((String) adapter
                                            .getItemAtPosition(position))
                                            .trim();
                                    countryName.setText(country_name_selected);
                                    countryListView.setVisibility(View.GONE);
                                }
                            });

                    countryName.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence s, int start,
                                int before, int count) {
                            String inputText = countryName.getText().toString()
                                    .toLowerCase(Locale.getDefault());
                            adapter.filter(inputText);
                            try{
                                if(!VALID_CHAR.contains(Character.toString((s.charAt(start))))){
                                    city.setText(s.subSequence(0, start));
                                    city.setSelection(city.length());
                                    Toast.makeText(mContext, getString(R.string.language_not_supported), Toast.LENGTH_LONG).show();
                                }    
                            }
                            catch(Exception e){
                                
                            }
                            
                        }

                        @Override
                        public void beforeTextChanged(CharSequence s,
                                int start, int count, int after) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            countryListView.setVisibility(View.VISIBLE);
                        }
                    });

                    builder.setCancelable(false).setPositiveButton(
                            getResources().getString(R.string.search),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int id) {
                                    dialog.dismiss();
                                    name = city.getText().toString().trim();
                                    if (country_name_selected == null)
                                        country_name_selected = countryName
                                                .getText().toString().trim();
                                    if (country_name_selected.contains(" ")) {
                                        country_name_selected = country_name_selected
                                                .replace(" ", "%20");
                                    }
                                    if (name.contains(" ")) {
                                        name = name
                                                .replace(" ", "%20");
                                    }
                                    if (!name.isEmpty()
                                            && !country_name_selected.isEmpty()) {
                                        finalUrl = url1 + name + ","
                                                + country_name_selected;
                                        progress.setMessage(getString(R.string.fetching_data));
                                        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                        progress.setIndeterminate(true);
                                        JSONobj = new HandleJSON(finalUrl,
                                                MainActivity.this);
                                        JSONobj.fetchJSON();
                                    } else {
                                        displayToast(
                                                R.string.enter_both_input_data_warning,
                                                R.drawable.oops);
                                    }
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.setTitle(this.getResources().getString(R.string.add));
                    alert.setOnKeyListener(new Dialog.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog,
                                int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    });
                    alert.show();
                }
            } else {
                displayToast(R.string.no_data_connection, R.drawable.oops);
            }
            return true;

        case R.id.action_refresh:

            refreshAllData(MIN_REFRESH_TIME);
            return true;
        case R.id.action_delete:
            if (cursor.getCount() != 0) {
                if (itemAdapter.getCheckBoxStatus()) {
                    if (ItemViewAdapter.selectionStatusList.size() > 0)
                        deleteListRows(ItemViewAdapter.selectionStatusList);
                    itemAdapter.hideCheckBox();
                } else {
                    itemAdapter.showCheckBox();
                }
                itemAdapter.notifyDataSetChanged();
            } else {
                displayToast(R.string.no_data_warning, R.drawable.oops);
            }
            return true;

        case R.id.action_settings:
            if (itemAdapter != null) {
                itemAdapter.hideCheckBox();
                itemAdapter.notifyDataSetChanged();
            }
            Intent i = new Intent(this, UserPreference.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            return true;

        case R.id.action_speak_search:
            if (itemAdapter != null) {
                itemAdapter.hideCheckBox();
                itemAdapter.notifyDataSetChanged();
            }
            if (isnetworkAvailable()) {

                if (itemAdapter.getCount() == MAX_ENTRIES)
                    displayToast(R.string.max_cities_error, R.drawable.oops);
                else {
                    // if (Locale.getDefault().getLanguage().equals("en")) {
                    if (itemAdapter.getCount() == MAX_ENTRIES)
                        displayToast(R.string.max_cities_error, R.drawable.oops);
                    else
                        ttobj.speak(
                                getResources().getString(
                                        R.string.tell_city_name),
                                TextToSpeech.QUEUE_FLUSH, null);
                    do {
                    } while (ttobj.isSpeaking());
                    Intent intent = new Intent(
                            RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_RESULTS, "en-US");
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                            "en-US");
                    try {
                        startActivityForResult(intent, RESULT_SPEECH_CITY);
                    } catch (ActivityNotFoundException a) {
                        displayToast(R.string.stt_not_supported,
                                R.drawable.oops);
                    }
                    /*
                     * } else {
                     * displayToast(R.string.locale_not_supported_voice,
                     * R.drawable.oops); }
                     */
                }
            } else {
                displayToast(R.string.no_data_connection, R.drawable.oops);
            }
            return true;

        default:
            return super.onOptionsItemSelected(item);

        }

    }

    private void refreshAllData(long i) {
        try {
            if (itemAdapter != null) {
                itemAdapter.hideCheckBox();
                itemAdapter.notifyDataSetChanged();
            }
            if (isnetworkAvailable()) {
                refresh_cities = 0;
                String refreshUrl = "http://api.openweathermap.org/data/2.5/group?id=";
                cursor = dbcon.readData();
                cursor.moveToFirst();
                SharedPreferences pref = PreferenceManager
                        .getDefaultSharedPreferences(this);
                if (pref.getBoolean("isfirstTime", true)) {

                    SharedPreferences.Editor e = pref.edit();
                    e.putBoolean("isfirstTime", false);
                    e.commit();
                }
                if (!pref.getBoolean("isfirstTime", false)) {
                    last_refreshed_time.setVisibility(View.VISIBLE);
                }
                do {
                    if (cursor != null && cursor.getCount() != 0) {
                        if (Math.abs(System.currentTimeMillis()
                                - (cursor.getLong(cursor
                                        .getColumnIndex(DBhelper.CITY_REFRESH_TIME)))) > i) {
                            refreshUrl = refreshUrl
                                    + (cursor.getString(cursor
                                            .getColumnIndex("_id"))) + ",";
                            refresh_cities++;
                        }
                    } else {
                        displayToast(R.string.no_data_warning, R.drawable.oops);
                    }

                } while (cursor.moveToNext());

                if (refresh_cities > 0) {
                    RefreshBulkData bulkdata = new RefreshBulkData(
                            getApplicationContext());
                    bulkdata.execute(refreshUrl);
                }
            } else {
                displayToast(R.string.no_data_connection, R.drawable.oops);
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.memberList_id) {
            getMenuInflater().inflate(R.menu.menu_list_context, menu);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        try {
            getActionBar().setBackgroundDrawable(
                    new ColorDrawable(Color.parseColor("#FF00006A")));
        } catch (Exception e) {

        }

        ttobj = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        // TODO Auto-generated method stub
                        if (status != TextToSpeech.ERROR) {
                            ttobj.setLanguage(Locale.US);
                        }
                    }
                });
        refreshList();
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void insertJSONData() {
        if (JSONobj != null) {
            while (JSONobj.parsingComplete)
                ;
            ContentValues values = new ContentValues();
            if (!HandleJSON.cityId.equals("0")) {
                values.put(DBhelper.CITY_ID, HandleJSON.cityId);
                values.put(DBhelper.CITY_NAME, HandleJSON.city);
                values.put(DBhelper.CITY_COUNTRY, HandleJSON.country);
                values.put(DBhelper.CITY_LONGTITUDE, HandleJSON.longitude);
                values.put(DBhelper.CITY_LATITUDE, HandleJSON.latitude);
                values.put(DBhelper.CITY_SUNRISE, HandleJSON.sunrise);
                values.put(DBhelper.CITY_SUNSET, HandleJSON.sunset);
                values.put(DBhelper.CITY_TEMPERATURE, HandleJSON.temperature);
                values.put(DBhelper.CITY_TEMP_MIN, HandleJSON.tempMin);
                values.put(DBhelper.CITY_TEMP_MAX, HandleJSON.tempMax);
                values.put(DBhelper.CITY_HUMIDITY, HandleJSON.humidity);
                values.put(DBhelper.CITY_PRESSURE, HandleJSON.pressure);
                values.put(DBhelper.CITY_WIND_SPEED, HandleJSON.windSpeed);
                values.put(DBhelper.WEATHER_MAIN, HandleJSON.weather_main);
                values.put(DBhelper.WEATHER_DESCRIPTION, HandleJSON.description);
                values.put(DBhelper.WEATHER_ICON_ID, HandleJSON.iconId);
                values.put(DBhelper.WEATHER_ID, HandleJSON.weatherId);

                String time_date[] = HandleJSON.time.split(" ");

                values.put(DBhelper.DATE, time_date[0]);
                values.put(DBhelper.TIME, time_date[1]);
                values.put(DBhelper.CITY_SYSTEM_TIME,
                        System.currentTimeMillis());
                values.put(DBhelper.CITY_REFRESH_TIME,
                        System.currentTimeMillis());
                dbcon.insertData(values);
            }

        }
    }

    private void refreshList() {
        cursor = dbcon.readData();
        itemAdapter = new ItemViewAdapter(MainActivity.this, cursor);
        lv.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();

        if (last_refreshed_time != null)
            last_refreshed_time.setText(getResources().getString(
                    R.string.last_refreshed)
                    + " "
                    + formattedDateForAction(dbcon
                            .getMaxValuefromDb(DBhelper.CITY_REFRESH_TIME)));

    }

    private void deleteListRows(ArrayList<Long> list) {
        for (Long l : list) {
            dbcon.deleteData(l);
        }
        refreshList();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

        // TODO Auto-generated method stub
        listvisible = false;
        detailed_view.setVisibility(View.VISIBLE);
        lv.setVisibility(View.GONE);
        action_menu.removeGroup(R.id.action_menu);
        AppResource res = AppResource.getInstance();

        TextView cityName = (TextView) view.findViewById(R.id.cityid);

        HashMap<String, String> dbData = SQLController.readData(cityName
                .getText().toString());

        m_city_name.setText(dbData.get("name"));
        m_city_name.setSelected(true);

        long time_diff = System.currentTimeMillis()
                - Long.parseLong(dbData.get(DBhelper.CITY_SYSTEM_TIME));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String inputString = dbData.get("time") + ":00.000";

        Date mDate = null;
        try {

            mDate = sdf.parse(dbData.get("date") + " " + inputString);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long updated_time = mDate.getTime() + time_diff;
        /*
         * SimpleDateFormat date_format = new SimpleDateFormat(
         * "dd-MM-yyyy HH:mm:ss.SSS");
         */
        Date dateTime = new Date(updated_time);
        SimpleDateFormat date_format = new SimpleDateFormat(
                "EEE MMM d, yyyy HH:mm:ss.SSS");
        String resultDateTime = date_format.format(dateTime);
        String[] timeSlice = resultDateTime.split(" ");

        m_country.setSelected(true);
        if (dbData.get("country").length() == 2) {
            m_country.setText(res.getCountryName(dbData.get("country")));
            flag.setImageResource(getFlag(res.getCountryName(dbData
                    .get("country"))));
        } else {
            m_country.setText(dbData.get("country"));
            flag.setImageResource(getFlag(dbData.get("country")));
        }
        m_time.setText(res.getProperTime(timeSlice[4].trim(),
                getApplicationContext()));
        m_date.setText(((timeSlice[0] + " " + timeSlice[1] + " " + timeSlice[2]
                + " " + timeSlice[3]).trim()));
        String temp_celcius = res.getProperTemperature(
                dbData.get("temperature"), getApplicationContext());
        String temp_celcius_min = res.getProperTemperature(
                dbData.get("temp_min"), getApplicationContext());
        String temp_celcius_max = res.getProperTemperature(
                dbData.get("temp_max"), getApplicationContext());

        m_temperature.setText(temp_celcius);
        m_min_max_temperature.setText(temp_celcius_min + " | "
                + temp_celcius_max);

        int weather_id = Integer.parseInt(dbData.get("weather_id"));

        long sunrise = Long.parseLong(dbData.get("sunrise"))
                - (Long.parseLong(dbData.get("systemtime")) - mDate.getTime());
        long sunset = Long.parseLong(dbData.get("sunset"))
                - (Long.parseLong(dbData.get("systemtime")) - mDate.getTime());

        date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        resultDateTime = date_format.format(dateTime);

        ArrayList<Integer> result = res.Weather_Icon(weather_id, sunrise,
                sunset, resultDateTime.substring(10, 16).trim(), resultDateTime
                        .substring(0, 10).trim());

        m_icon.setImageResource(result.get(1));

        Animation animFadein = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.zoom_in);
        m_icon.startAnimation(animFadein);
        m_weather_desc.setText(getResources().getString(
                AppResource.weather_desc.get(weather_id)));
        if (danger_weather_id.contains(weather_id)) {
            m_weather_desc.startAnimation(getBlinkAnimation());
            m_weather_desc.setTextColor(Color.RED);
            m_weather_desc.setTypeface(null, Typeface.BOLD);
        } else {
            m_weather_desc.setTextColor(Color.WHITE);
            m_weather_desc.setTypeface(null, Typeface.NORMAL);
        }
        m_humidity.setText(dbData.get("humidity")
                + getResources().getString(R.string.percent));
        m_pressure.setText(dbData.get("pressure") + " "
                + getResources().getString(R.string.hpa));

        m_windspeed.setText(res.getProperWindSpeed(dbData.get("windspeed"),
                getApplicationContext()));

        m_sun_rise.setText(res.getProperTime(
                formatDate(String.valueOf(sunrise)), getApplicationContext()));
        m_sun_set.setText(res.getProperTime(formatDate(String.valueOf(sunset)),
                getApplicationContext()));
        m_latitude.setText(dbData.get("long"));
        m_longtitude.setText(dbData.get("lati"));
    }

    private int getFlag(String string) {
        // TODO Auto-generated method stub
        int x = getResources().getIdentifier(
                string.toLowerCase().replaceAll(" ", "_"), "drawable",
                getApplicationInfo().packageName);
        return x;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        if (listvisible) {
            super.onBackPressed();
        } else {
            detailed_view.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
            action_menu.add(R.id.action_add);
            action_menu.add(R.id.action_speak_search);
            action_menu.add(R.id.action_refresh);
            action_menu.add(R.id.action_settings);
            action_menu.add(R.id.action_delete);
            invalidateOptionsMenu();
            listvisible = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
        case RESULT_SPEECH_CITY: {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> text = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Speak_city_name = text.get(0);
                ttobj.speak(getResources()
                        .getString(R.string.tell_country_name),
                        TextToSpeech.QUEUE_FLUSH, null);
                do {
                } while (ttobj.isSpeaking());
                Intent Countryintent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                Countryintent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
                Countryintent
                        .putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                Countryintent.putExtra(RecognizerIntent.EXTRA_RESULTS, "en-US");
                try {
                    startActivityForResult(Countryintent, RESULT_SPEECH_COUNTRY);
                } catch (ActivityNotFoundException a) {
                    displayToast(R.string.stt_not_supported, R.drawable.oops);
                }
            }
            break;
        }

        case RESULT_SPEECH_COUNTRY: {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> text = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Speak_country_name = text.get(0);
                progress.setMessage(getString(R.string.fetching_data));
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                finalUrl = url1 + Speak_city_name + "," + Speak_country_name;
                JSONobj = new HandleJSON(finalUrl, MainActivity.this);
                JSONobj.fetchJSON();
            }
        }
        }
    }

    private String formatDate(String value) {
        java.util.Date time = new java.util.Date(Long.parseLong(value));
        return time.getHours() + ":" + time.getMinutes();
    }

    public static void displayToast(int msg_id, int icon_id) {
        toast_message.setText(mAppResources.getString(msg_id));
        toast_icon.setImageResource(icon_id);
        final Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toast_layout);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);
    }

    private boolean isnetworkAvailable() {
        ConnectivityManager conn = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conn != null) {
            NetworkInfo[] info = conn.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Animation getBlinkAnimation() {
        Animation anim = new AlphaAnimation(1, 0.65f);
        anim.setDuration(300);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.REVERSE);
        anim.setRepeatMode(Animation.INFINITE);
        return anim;
    }

    private Animation getRotateAnimation() {
        RotateAnimation anim = new RotateAnimation(45f, 360f, 150f, 150f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(2);
        anim.setDuration(1000);
        return anim;
    }

    private String formattedDateForAction(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm", Locale.US);
        String date_text = sdf.format(date);
        String[] temp = date_text.split(",");
        String propertime = AppResource.getInstance().getProperTime(
                temp[1].trim(), mContext);
        return temp[0] + ", " + propertime;
    }

    class CountryListAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<String> countryList = new ArrayList<String>();
        ArrayList<String> countryListTemp = new ArrayList<String>();

        public CountryListAdapter(Context context, String[] resource) {
            this.mContext = context;
            // this.countryList = new
            // ArrayList<String>(Arrays.asList(resource));
            this.countryListTemp.addAll(Arrays.asList(resource));
        }

        @Override
        public int getCount() {
            return countryList.size();
        }

        @Override
        public Object getItem(int position) {
            return countryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View localView = null;

            localView = getLayoutInflater().inflate(R.layout.country_list_row,
                    null);
            TextView item = (TextView) localView
                    .findViewById(R.id.list_item_row_text);
            item.setText(countryList.get(position));

            return localView;
        }

        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            countryList.clear();
            if (charText.length() == 0) {
            } else {
                for (String temp : countryListTemp) {
                    if (temp.toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        countryList.add(temp);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }
}
