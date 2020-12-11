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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tracking extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    LinearLayout liv_loc, re_loc;
    Button back;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);
        liv_loc = findViewById(R.id.live_loc);
        re_loc = findViewById(R.id.req_loc);
        back = findViewById(R.id.track_back);

        /*----------------- toolbar--------------*/


        setSupportActionBar(toolbar);


        /*--------------------Navigation Drawer Menu--------------*/

        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(Tracking.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_track);

        liv_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent live = new Intent(Tracking.this, LiveLocationActivity.class);
                startActivity(live);
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });

        re_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent req = new Intent(Tracking.this, RequestedUser.class);
                startActivity(req);
            }
        });

    }

    private void home() {
        Intent h = new Intent(Tracking.this, Dashboard1.class);
        startActivity(h);
        finish();
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
            Intent e = new Intent(Tracking.this, Dashboard1.class);
            startActivity(e);
        }
        if (id == R.id.nav_fam) {
            Intent f = new Intent(Tracking.this, AddContacts.class);
            startActivity(f);
        }

        if (id == R.id.nav_circle) {
            Intent c = new Intent(Tracking.this, Circle.class);
            startActivity(c);
        }

        if (id == R.id.nav_track) {
            //do tracking
            Intent t = new Intent(Tracking.this, Tracking.class);
            startActivity(t);
        }

        if (id == R.id.nav_setting) {
            Intent i = new Intent(Tracking.this, Settings.class);
            startActivity(i);
        }

        if (id == R.id.nav_invite) {
            Intent n = new Intent(Tracking.this, Invite_activity.class);
            startActivity(n);
        }


        if (id == R.id.nav_contact) {
            Intent o = new Intent(Tracking.this, ContactUs.class);
            startActivity(o);
        }


        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Tracking.this, " Sign out!", Toast.LENGTH_SHORT).show();
            Intent l = new Intent(Tracking.this, Signup.class);
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