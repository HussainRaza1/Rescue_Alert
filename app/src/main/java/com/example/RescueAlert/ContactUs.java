package com.example.RescueAlert;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ContactUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText etSub, etMessage;
    Button btSend;
    DrawerLayout et_drawerLayout;
    NavigationView et_navigationView;
    Toolbar et_toolbar;
    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        etSub = findViewById(R.id.et_subject);
        etMessage = findViewById(R.id.et_message);
        btSend = findViewById(R.id.bt_send);

        /*------navBar-------*/
        et_drawerLayout = findViewById(R.id.drawer_layout1);
        et_navigationView = findViewById(R.id.nav_view1);
        et_toolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(et_toolbar);

        et_navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(ContactUs.this, et_drawerLayout, et_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        et_drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        et_navigationView.setNavigationItemSelectedListener(this);
        et_navigationView.setCheckedItem(R.id.nav_contact);

        /*-----------send email--------*/

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + etSub.getText().toString() + "&body=" + etMessage.getText().toString() + "&to=" + "leonraza5@mail.com");
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (et_drawerLayout.isDrawerOpen(GravityCompat.START)) {
            et_drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.nav_emergency) {
            Intent e = new Intent(ContactUs.this, Dashboard1.class);
            startActivity(e);
        }
        if (id == R.id.nav_fam) {
            Intent f = new Intent(ContactUs.this, CloseContacts.class);
            startActivity(f);
        }

        if (id == R.id.nav_circle) {
            Intent c = new Intent(ContactUs.this, Family.class);
            startActivity(c);
        }

        if (id == R.id.nav_track) {
            //do tracking
            Intent t = new Intent(ContactUs.this, Tracking.class);
            startActivity(t);
        }

        if (id == R.id.nav_setting) {
            Intent i = new Intent(ContactUs.this, Settings.class);
            startActivity(i);
        }

        if (id == R.id.nav_invite) {
            Intent n = new Intent(ContactUs.this, Invite_activity.class);
            startActivity(n);
        }


        if (id == R.id.nav_contact) {
            Intent o = new Intent(ContactUs.this, ContactUs.class);
            startActivity(o);
        }


        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(ContactUs.this, " Sign out!", Toast.LENGTH_SHORT).show();
            Intent l = new Intent(ContactUs.this, Signup.class);
            startActivity(l);
            finish();
        }

        et_drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}