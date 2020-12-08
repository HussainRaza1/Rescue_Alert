package com.example.RescueAlert;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {
    public static final String
            SETTINGS_KEY = "Settings";
    public static final String
            KEY_PREF_EXAMPLE_SWITCH = "example_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_base, new com.example.RescueAlert.Preference())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public class SettingsFragment extends PreferenceFragmentCompat {
        SharedPreferences sharedPreferences;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        public SettingsFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings, rootKey);
           /* getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            final EditTextPreference template = findPreference("template_preference");
            final SwitchPreference message = findPreference("send_message");

            if (template != null) {
                template.setSummaryProvider(new Preference.SummaryProvider<EditTextPreference>() {
                    @Override
                    public CharSequence provideSummary(EditTextPreference preference) {
                        String text = preference.getText();
                        if (TextUtils.isEmpty(text)) {
                            return "Not set";
                        } else {
                            FirebaseDatabase.getInstance().getReference("templates").child("templateText").setValue(text);
                            return text;
                        }
                    }
                });
            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }*/
        }
    }
}
