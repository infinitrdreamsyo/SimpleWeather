package org.allpossible.simpleweather;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Preference Summary
 * 
 */
public class PreferencesFragmentSummary extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
		mContext = getActivity();
		getActivity().getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#FF00006A")));

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

		findPreference("timeformat").setDefaultValue(
				prefs.getString("timeformat", "24 Hr Format"));
		findPreference("temperatureformat").setDefaultValue(
				prefs.getString("temperatureformat", "\u2103"));
		findPreference("windpeedformat").setDefaultValue(
				prefs.getString("windpeedformat", "Kmph"));
		findPreference("timeformat").setSummary(
				prefs.getString("timeformat", "24 Hr Format"));
		findPreference("temperatureformat").setSummary(
				prefs.getString("temperatureformat", "\u2103"));
		findPreference("windpeedformat").setSummary(
				prefs.getString("windpeedformat", "Kmph"));

		Preference help = (Preference) getPreferenceScreen().findPreference(
				"help");
		help.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(mContext);
				LayoutInflater li = LayoutInflater.from(mContext);
				View layout = li.inflate(R.layout.help, null);
				dialog.setTitle(mContext.getResources()
						.getString(R.string.help));
				dialog.setContentView(layout);
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
				return true;
			}
		});

		Preference terms = (Preference) getPreferenceScreen().findPreference(
				"term_condition");
		terms.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				final Dialog dialog = new Dialog(mContext);
				LayoutInflater li = LayoutInflater.from(mContext);
				View layout = li.inflate(R.layout.terms_conditions, null);
				dialog.setTitle(mContext.getResources().getString(
						R.string.terms_condition_title));
				dialog.setContentView(layout);
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
				return true;
			}
		});

		Preference about = (Preference) getPreferenceScreen().findPreference(
				"about");
		about.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				final Dialog dialog = new Dialog(mContext);
				LayoutInflater li = LayoutInflater.from(mContext);
				View layout = li.inflate(R.layout.about_updated, null);
				Button rate_us = (Button) layout.findViewById(R.id.rate_us);
				TextView version = (TextView) layout
						.findViewById(R.id.app_version_label);
				version.setText(version.getText()
						+ " "
						+ mContext.getResources().getString(
								R.string.app_version));
				CustomListener customlistener = new CustomListener();
				rate_us.setOnClickListener(customlistener);
				TextView developer_email = (TextView) layout
						.findViewById(R.id.app_developer_mail);
				developer_email.setPaintFlags(developer_email.getPaintFlags()
						| Paint.UNDERLINE_TEXT_FLAG);
				developer_email.setOnClickListener(customlistener);
				dialog.setTitle(mContext.getResources().getString(
						R.string.about));
				dialog.setContentView(layout);
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
				return true;
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
		initSummary();
	}

	@Override
	public void onPause() {
		super.onPause();

		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		// Update summary
		updatePrefsSummary(sharedPreferences, findPreference(key));
	}

	/**
	 * Update summary
	 * 
	 * @param sharedPreferences
	 * @param pref
	 */
	protected void updatePrefsSummary(SharedPreferences sharedPreferences,
			Preference pref) {

		if (pref == null)
			return;

		if (pref instanceof ListPreference) {
			// List Preference
			ListPreference listPref = (ListPreference) pref;
			listPref.setSummary(listPref.getEntry());

		}
	}

	/*
	 * Init summary
	 */
	protected void initSummary() {
		int pcsCount = getPreferenceScreen().getPreferenceCount();
		for (int i = 0; i < pcsCount; i++) {
			initPrefsSummary(getPreferenceManager().getSharedPreferences(),
					getPreferenceScreen().getPreference(i));
		}
	}

	/*
	 * Init single Preference
	 */
	protected void initPrefsSummary(SharedPreferences sharedPreferences,
			Preference p) {
		if (p instanceof PreferenceCategory) {
			PreferenceCategory pCat = (PreferenceCategory) p;
			int pcCatCount = pCat.getPreferenceCount();
			for (int i = 0; i < pcCatCount; i++) {
				initPrefsSummary(sharedPreferences, pCat.getPreference(i));
			}
		}
	}

	class CustomListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			if (view != null) {

				switch (view.getId()) {
				case R.id.rate_us:
					mContext.startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("market://details?id=org.allpossible.simpleweather")));
					break;
				case R.id.app_developer_mail:
					Intent sendintent = new Intent(Intent.ACTION_VIEW);
					sendintent.setClassName("com.google.android.gm",
							"com.google.android.gm.ComposeActivityGmail");
					sendintent.putExtra(Intent.EXTRA_EMAIL,
							new String[] { "infinitedreamsyo@gmail.com" });
					sendintent.setData(Uri.parse("infinitedreamsyo@gmail.com"));
					sendintent.putExtra(Intent.EXTRA_SUBJECT, "Comments:");
					sendintent.setType("plain/texy");
					sendintent.putExtra(Intent.EXTRA_TEXT, "your comments");
					startActivity(sendintent);
					break;

				}

			}

		}

	}
}