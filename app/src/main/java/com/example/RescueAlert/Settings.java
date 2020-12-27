package com.example.RescueAlert;

import android.app.assist.AssistStructure;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.TwoStatePreference;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {
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

    public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
        SharedPreferences sharedPreferences;
        SwitchPreference swtich1;

        public SettingsFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings, rootKey);
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            swtich1 = findPreference("eme1");
            final SwitchPreference swtich2 = findPreference("eme2");
            final EditTextPreference template = findPreference("template_tex");



     /*       if (template != null) {
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
            }*/
        }
        @Override
        public void onStart() {
            super.onStart();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onStop() {
            super.onStop();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        }
    }
}
