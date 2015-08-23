package org.allpossible.simpleweather;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserPreference extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content,
			       new PreferencesFragmentSummary()).commit();
	}
}
