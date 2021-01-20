package com.example.RescueAlert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class Settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);

        /*--------------------Navigation Drawer Menu--------------*/

        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(Settings.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_setting);


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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.nav_emergency) {
            Intent e = new Intent(Settings.this, Dashboard1.class);
            startActivity(e);
        }
        if (id == R.id.nav_fam) {
            Intent f = new Intent(Settings.this, CloseContacts.class);
            startActivity(f);
        }

        if (id == R.id.nav_circle) {
            Intent c = new Intent(Settings.this, Family.class);
            startActivity(c);
        }

        if (id == R.id.nav_track) {
            //do tracking
            Intent t = new Intent(Settings.this, Tracking.class);

            startActivity(t);
        }

        if (id == R.id.nav_setting) {
            Intent i = new Intent(Settings.this, Settings.class);
            startActivity(i);
        }

        if (id == R.id.nav_invite) {
            Intent n = new Intent(Settings.this, Invite_activity.class);
            startActivity(n);
        }


        if (id == R.id.nav_contact) {
            Intent o = new Intent(Settings.this, ContactUs.class);
            startActivity(o);
        }


        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Settings.this, " Sign out!", Toast.LENGTH_SHORT).show();
            Intent l = new Intent(Settings.this, Signup.class);
            startActivity(l);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
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
