package com.example.RescueAlert;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new com.example.RescueAlert.Preference())
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

    public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
        SharedPreferences sharedPreferences;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        public SettingsFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            final SwitchPreference swtich1 = findPreference("eme1");
            final SwitchPreference swtich2 = findPreference("eme2");
            final EditTextPreference template = findPreference("template_text");

            /* sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
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
            if (key == "send_message"){
            }

        }*/
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }

        @Override
        public void onStart() {
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
            super.onStart();
        }

        @Override
        public void onStop() {
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
            super.onStop();
        }
    }
}
