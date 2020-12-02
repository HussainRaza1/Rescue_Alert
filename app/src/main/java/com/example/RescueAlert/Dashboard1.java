package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdash);


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

        switch (menuItem.getItemId()) {

            case R.id.nav_emergency:
                break;
            case R.id.nav_fam:
                Intent f = new Intent(Dashboard1.this, AddContacts.class);
                startActivity(f);
                break;
            case R.id.nav_circle:
                Intent c = new Intent(Dashboard1.this, Circle.class);
                startActivity(c);
                break;

            case R.id.nav_track:

            case R.id.nav_setting:

            case R.id.nav_contact:
                Intent t = new Intent(Dashboard1.this, ContactUs.class);
                startActivity(t);
                break;

            case R.id.nav_logout:
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
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}