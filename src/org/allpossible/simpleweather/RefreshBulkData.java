package org.allpossible.simpleweather;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

public class RefreshBulkData extends
		AsyncTask<String, Void, HashMap<String, ContentValues>> {
	
	Context mContext;
	public RefreshBulkData(Context c){
		mContext = c;
	}

	@Override
	protected HashMap<String, ContentValues> doInBackground(String... urls) {
		// TODO Auto-generated method stub

		//HashMap<String, HashMap<String, String>> refrehsed_data = new HashMap<String, HashMap<String,String>>();
		HashMap<String, ContentValues> refrehsed_data = new HashMap<String, ContentValues>();
		try {
			HttpGet httppost = new HttpGet(urls[0]);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httppost);
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity);
				JSONObject jsono = new JSONObject(data);
				JSONArray jarray = jsono.getJSONArray("list");
				for (int i = 0; i < jarray.length(); i++) {
					//HashMap<String, String> city_data = new HashMap<String, String>();
					ContentValues city_data = new ContentValues();
					JSONObject object = jarray.getJSONObject(i);
					JSONObject sys = object.getJSONObject("sys");
					city_data.put(DBhelper.CITY_SUNRISE, String.valueOf(Long.parseLong(sys.getString("sunrise")) * 1000));
					city_data.put(DBhelper.CITY_SUNSET, String.valueOf(Long.parseLong(sys.getString("sunset")) * 1000));
					JSONArray weather = object.getJSONArray("weather");
					JSONObject t = weather.getJSONObject(0);
					city_data.put(DBhelper.WEATHER_DESCRIPTION, t.getString("description"));
					city_data.put(DBhelper.WEATHER_ICON_ID, t.getString("icon"));
					city_data.put(DBhelper.WEATHER_ID, t.getString("id"));

					JSONObject main = object.getJSONObject("main");
					city_data.put(DBhelper.CITY_TEMPERATURE, main.getString("temp"));
					city_data.put(DBhelper.CITY_TEMP_MAX, main.getString("temp_max"));
					city_data.put(DBhelper.CITY_TEMP_MIN, main.getString("temp_min"));

					city_data.put(DBhelper.CITY_PRESSURE, main.getString("pressure"));
					city_data.put(DBhelper.CITY_HUMIDITY, main.getString("humidity"));

					JSONObject wind = object.getJSONObject("wind");
					city_data.put(DBhelper.CITY_WIND_SPEED, wind.getString("speed"));
					
					city_data.put(DBhelper.CITY_REFRESH_TIME, System.currentTimeMillis());					
					
					refrehsed_data.put(object.getString("id"), city_data);

				}
			}

		} catch (Exception e) {
		}

		return refrehsed_data;
	}
	@Override
	protected void onPostExecute(HashMap<String, ContentValues> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		SQLController sql = new SQLController(mContext);
		Iterator it = result.entrySet().iterator();
		int count=0;
		 while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		         count = count+sql.updateData(pairs.getKey().toString(), (ContentValues)pairs.getValue());
		    }
		 if(count != 0){
			 MainActivity.displayToast(R.string.refresh_done, R.drawable.toast_sucess);
			 
			 Message msg = Message.obtain();
			msg.arg1 = MainActivity.DATA_REFRESHED;
			MainActivity.myHandler.sendMessage(msg);
		 }
		 
		
	}
	
	@Override
	protected void onPreExecute() {
		MainActivity.displayToast(R.string.fetching_data, R.drawable.refresh_data);
	};



	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

}
