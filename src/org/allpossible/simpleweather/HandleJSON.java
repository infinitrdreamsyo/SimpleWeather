package org.allpossible.simpleweather;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Message;

public class HandleJSON {
    public static String country = "county";
    public static String temperature = "temperature";
    public static String tempMin = "min";
    public static String tempMax = "max";
    public static String humidity = "humidity";
    public static String pressure = "pressure";
    public static String windSpeed = "windspeed";
    public static String description = "description";
    public static String weather_main = "main";
    public static String iconId = "icon";
    public static String weatherId = "weatherId";
    public static String sunrise = "sunrice";
    public static String sunset = "sunset";
    public static String urlString = null;
    public static String city = "city";
    public static String latitude = "lat";
    public static String longitude = "long";
    public static String cityId = "cityId";
    public static Activity mContext;
    public static String time = "time";
    Message msg;

    public volatile boolean parsingComplete = true;
    public volatile boolean timeParsingComplete = true;

    public HandleJSON(String url, Activity context) {
        this.urlString = url;
        mContext = context;
    }

    public String getCountry() {
        return country;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getDescription() {
        return description;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) throws Exception {
        // try {
        JSONObject reader = new JSONObject(in);
        city = reader.getString("name");
        cityId = reader.getString("id");

        JSONObject coord = reader.getJSONObject("coord");
        longitude = coord.getString("lon");
        latitude = coord.getString("lat");

        getTime(latitude, longitude);

        JSONObject sys = reader.getJSONObject("sys");
        country = sys.getString("country");
        sunrise = String
                .valueOf(Long.parseLong(sys.getString("sunrise")) * 1000);
        sunset = String.valueOf(Long.parseLong(sys.getString("sunset")) * 1000);

        JSONArray weather = reader.getJSONArray("weather");

        JSONObject t = weather.getJSONObject(0);
        description = t.getString("description");
        iconId = t.getString("icon");
        weatherId = t.getString("id");
        weather_main = t.getString("main");

        JSONObject main = reader.getJSONObject("main");
        temperature = main.getString("temp");
        tempMax = main.getString("temp_max");
        tempMin = main.getString("temp_min");

        pressure = main.getString("pressure");
        humidity = main.getString("humidity");

        JSONObject wind = reader.getJSONObject("wind");
        windSpeed = wind.getString("speed");
        parsingComplete = false;

    }

    public void fetchJSON() {

        mContext.runOnUiThread(new Runnable() {
            public void run() {
                MainActivity.progress.show();
            }

        });

        Thread myThread = new Thread(new GetJSONdata());
        myThread.start();

    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private class GetJSONdata implements Runnable {
        InputStream stream = null;

        @Override
        public void run() {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                stream = conn.getInputStream();
                String data = convertStreamToString(stream);
                readAndParseJSON(data);
                stream.close();
                /*
                 * msg = Message.obtain(); msg.arg1 =
                 * MainActivity.JSON_CONNECTION_SUCCESS;
                 * MainActivity.myHandler.sendMessage(msg);
                 */

            } catch (JSONException e) {
                msg = Message.obtain();
                msg.arg1 = MainActivity.JSON_CONNECTION_FAIL;
                MainActivity.myHandler.sendMessage(msg);
                e.printStackTrace();
            } catch (IOException e) {
                msg = Message.obtain();
                msg.arg1 = MainActivity.HTTP_CONNECTION_FAIL;
                MainActivity.myHandler.sendMessage(msg);
                e.printStackTrace();
            } catch (Exception e) {
                msg = Message.obtain();
                msg.arg1 = MainActivity.JSON_CONNECTION_FAIL;
                MainActivity.myHandler.sendMessage(msg);
                e.printStackTrace();

            }
        }

    }

    private String getTime(String lat, String lon) {
        String url = "http://api.worldweatheronline.com/free/v1/tz.ashx?key=160dc398dfa65d37cfc29875f5d5a9d2ac067f1e&q="
                + lat + "," + lon + "&fx=no&format=json";
        HandleJSON JSONobj = new HandleJSON(url, mContext);
        JSONobj.fetchTime();
        return null;
    }

    public void fetchTime() {
        mContext.runOnUiThread(new Runnable() {
            public void run() {
                MainActivity.progress.show();
            }

        });
        Thread myThread = new Thread(new GetTimedata());
        myThread.start();
    }

    private class GetTimedata implements Runnable {

        InputStream stream = null;

        @Override
        public void run() {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                stream = conn.getInputStream();

                String time_data = convertStreamToString(stream);

                readAndParseTimeJSON(time_data);

                timeParsingComplete = false;
                stream.close();

                msg = Message.obtain();
                msg.arg1 = MainActivity.JSON_CONNECTION_SUCCESS;
                MainActivity.myHandler.sendMessage(msg);

            } catch (JSONException e) {
                msg = Message.obtain();
                msg.arg1 = MainActivity.JSON_CONNECTION_FAIL;
                MainActivity.myHandler.sendMessage(msg);
                e.printStackTrace();

            } catch (IOException e) {
                msg = Message.obtain();
                msg.arg1 = MainActivity.HTTP_CONNECTION_FAIL;
                MainActivity.myHandler.sendMessage(msg);
                e.printStackTrace();

            } catch (Exception e) {
                msg = Message.obtain();
                msg.arg1 = MainActivity.JSON_CONNECTION_FAIL;
                MainActivity.myHandler.sendMessage(msg);
                e.printStackTrace();

            }
        }

    }

    public void readAndParseTimeJSON(String in) throws Exception {
        int x = in.indexOf("localtime");
        time = in.substring(x + 13, x + 29);
    }
}
