package com.example.RescueAlert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variable
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button l_btn;
    LinearLayout emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdash);

        emergency = findViewById(R.id.emergency_layout);
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view);
            }
        });

/*        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);


        final Boolean switchPref = sharedPref.getBoolean
                (Settings.KEY_PREF_EXAMPLE_SWITCH, false);*/



        /*------------------------HOOKS---------------------*/

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);
        l_btn = findViewById(R.id.nav_logout);


        /*----------------- toolbar--------------*/


        setSupportActionBar(toolbar);


        /*--------------------Navigation Drawer Menu--------------*/

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Dashboard1.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_emergency);

      /*  sharedPref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key == "send_message"){
                    if()
                    //don't show popup
                }
                else {
                    switchPref.toString();
                }

            }
        });*/


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
        if (id==R.id.nav_emergency) {
            Toast.makeText(Dashboard1.this,"NavEmergency clicked",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.nav_fam){
            Intent f = new Intent(Dashboard1.this, AddContacts.class);
            startActivity(f);
        }

        if(id== R.id.nav_circle){
            Intent c = new Intent(Dashboard1.this, Circle.class);
            startActivity(c);
        }

        if(id== R.id.nav_track){
               //do tracking
            Toast.makeText(Dashboard1.this,"NavTrack clicked",Toast.LENGTH_SHORT).show();
        }

        if(id== R.id.nav_setting){
            Intent i = new Intent(Dashboard1.this, Settings.class);
            startActivity(i);
        }

        if(id == R.id.nav_contact){
            Intent t = new Intent(Dashboard1.this, ContactUs.class);
            startActivity(t);
        }


            if(id==R.id.nav_logout){
                l_btn.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(Dashboard1.this, " Sign out!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), Signup.class);
                        startActivity(i);
                        finish();
                    }
                }));
            }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}