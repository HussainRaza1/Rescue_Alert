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

import org.jetbrains.annotations.NotNull;

public class Invite_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout in_drawerLayout;
    NavigationView in_navigationView;
    Toolbar in_toolbar;
    Button inv_send;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_activity);

        inv_send = findViewById(R.id.send_invite);
        in_drawerLayout = findViewById(R.id.drawer_layout);
        in_navigationView = findViewById(R.id.nav_view);
        in_toolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(in_toolbar);

        in_navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(Invite_activity.this, in_drawerLayout, in_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        in_drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        in_navigationView.setNavigationItemSelectedListener(this);
        in_navigationView.setCheckedItem(R.id.nav_invite);


        inv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "share app");
                    String shareMessage = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(intent, "Share"));
                } catch (Exception e) {
                    Toast.makeText(Invite_activity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (in_drawerLayout.isDrawerOpen(GravityCompat.START)) {
            in_drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.nav_emergency) {
            Intent e = new Intent(Invite_activity.this, Dashboard1.class);
            startActivity(e);
        }
        if (id == R.id.nav_fam) {
            Intent f = new Intent(Invite_activity.this, CloseContacts.class);
            startActivity(f);
        }

        if (id == R.id.nav_circle) {
            Intent c = new Intent(Invite_activity.this, Family.class);
            startActivity(c);
        }

        if (id == R.id.nav_track) {
            //do tracking
            Intent t = new Intent(Invite_activity.this, Tracking.class);

            startActivity(t);
        }

        if (id == R.id.nav_setting) {
            Intent i = new Intent(Invite_activity.this, Settings.class);
            startActivity(i);
        }
        if (id == R.id.nav_invite) {
            Intent n = new Intent(Invite_activity.this, Invite_activity.class);
            startActivity(n);
        }


        if (id == R.id.nav_contact) {
            Intent o = new Intent(Invite_activity.this, ContactUs.class);
            startActivity(o);
        }


        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Invite_activity.this, " Sign out!", Toast.LENGTH_SHORT).show();
            Intent l = new Intent(Invite_activity.this, Signup.class);
            startActivity(l);
            finish();
        }

        in_drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}