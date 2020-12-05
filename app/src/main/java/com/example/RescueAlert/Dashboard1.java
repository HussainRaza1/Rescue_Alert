package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variable
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button l_btn;
    LinearLayout emergency, fire, police, med;
    ActionBarDrawerToggle toggle;

    PopUpClass popUpClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdash);

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
        emergency = findViewById(R.id.emergency_layout);
        fire = findViewById(R.id.fire_layout);
        police = findViewById(R.id.police_layout);
        med = findViewById(R.id.medical_layout);


        /*----------------- toolbar--------------*/


        setSupportActionBar(toolbar);


        /*--------------------Navigation Drawer Menu--------------*/

        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(Dashboard1.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_emergency);


        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpClass.showPopupWindow(view);

            }
        });
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpClass.showPopupWindowfire(view);
            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpClass.showPopupWindowpolice(view);
            }
        });
        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpClass.showPopupWindowmedical(view);
            }
        });

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
        if (id == R.id.nav_emergency) {
            Toast.makeText(Dashboard1.this, "NavEmergency clicked", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.nav_fam) {
            Intent f = new Intent(Dashboard1.this, AddContacts.class);
            startActivity(f);
        }

        if (id == R.id.nav_circle) {
            Intent c = new Intent(Dashboard1.this, Circle.class);
            startActivity(c);
        }

        if (id == R.id.nav_track) {
            //do tracking
            Toast.makeText(Dashboard1.this, "NavTrack clicked", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.nav_setting) {
            Intent i = new Intent(Dashboard1.this, Settings.class);
            startActivity(i);
        }

        if (id == R.id.nav_contact) {
            Intent t = new Intent(Dashboard1.this, ContactUs.class);
            startActivity(t);
        }


        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Dashboard1.this, " Sign out!", Toast.LENGTH_SHORT).show();
            Intent l = new Intent(Dashboard1.this, Signup.class);
            startActivity(l);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}