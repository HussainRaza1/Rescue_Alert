package com.example.RescueAlert;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Dashboard1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_PHONE_CALL = 1;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private final int LocationPermissionRequestCode = 1234;
    //variable
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button r_btn, e_btn;
    LinearLayout emergency, fire, police, med;
    ActionBarDrawerToggle toggle;
    Dialog epicDialog;
    ArrayList<String> phoneNumber = new ArrayList();
    Switch switchPref;
    Switch switchPref1;
    Boolean mLocationPermissionGranted = false;
    private SharedPreferences sharedPref;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdash);


        sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);


        final boolean switchPref1 = sharedPref.getBoolean
                ("eme2", false);

        final String MessagePref = sharedPref.getString
                ("template_text", "null");




        /*------------------------HOOKS---------------------*/
        epicDialog = new Dialog(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);
        emergency = findViewById(R.id.emergency_layout);
        r_btn = findViewById(R.id.red_button);
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


        r_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent red = new Intent(Dashboard1.this, LocationActivity.class);
                if (ContextCompat.checkSelfPermission(Dashboard1.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Dashboard1.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(red);
                }
                return true;
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmergencyPopup();
            }
        });
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirePopup();
            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PolicePopup();
            }
        });
        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedPopup();
            }
        });
        /*setupSharedPreferences();*/

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


    private void EmergencyPopup() {
        epicDialog.setContentView(R.layout.emergency_popup);
        e_btn = (Button) epicDialog.findViewById(R.id.emeButton);
        TextView eme_text = (TextView) epicDialog.findViewById(R.id.emergency_pop);

        eme_text.setText("Pressing call will send a message to the people in your Close Contacts, informing them of an emergency");

        e_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "1122";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(Dashboard1.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Dashboard1.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }

                //Message

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                String current_user = firebaseUser.getPhoneNumber();
                Query query = FirebaseDatabase.getInstance().getReference("family").orderByChild("user_ref").equalTo(current_user);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String num = snapshot.child("number").getValue().toString();
                            phoneNumber.add(num);
                        }
                        String message = "There is an Emergency at my place. I'm Calling 1122" + "\n\n\n" + "Sent by RESCUE ALERT APP";
                        SmsManager smsManager = SmsManager.getDefault();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            smsManager.sendTextMessage(phoneNumber.get(i), null, message, null, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    private void FirePopup() {
        epicDialog.setContentView(R.layout.fire_popup);
        e_btn = (Button) epicDialog.findViewById(R.id.fireButton);
        TextView fire_text = (TextView) epicDialog.findViewById(R.id.fire_pop);

        fire_text.setText("Pressing call will send a message to the people in your Close Contacts, informing them of the fire");

        e_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "16";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(Dashboard1.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Dashboard1.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }

                //Message

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                String current_user = firebaseUser.getPhoneNumber();
                Query query = FirebaseDatabase.getInstance().getReference("family").orderByChild("user_ref").equalTo(current_user);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String num = snapshot.child("number").getValue().toString();
                            phoneNumber.add(num);
                        }
                        String message = "There is a fire at my place. I'm Calling 16" + "\n\n\n" + "Sent by RESCUE ALERT APP";
                        SmsManager smsManager = SmsManager.getDefault();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            smsManager.sendTextMessage(phoneNumber.get(i), null, message, null, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    private void PolicePopup() {
        epicDialog.setContentView(R.layout.police_popup);
        e_btn = (Button) epicDialog.findViewById(R.id.policeButton);
        TextView police_text = (TextView) epicDialog.findViewById(R.id.police_pop);

        police_text.setText("Pressing call will send a message to the people in your Close Contacts, informing them of the crime");

        e_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "15";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(Dashboard1.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Dashboard1.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }

                //Message

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                String current_user = firebaseUser.getPhoneNumber();
                Query query = FirebaseDatabase.getInstance().getReference("family").orderByChild("user_ref").equalTo(current_user);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String num = snapshot.child("number").getValue().toString();
                            phoneNumber.add(num);
                        }
                        String message = "There is an Emergency at my place. I'm Calling 15" + "\n\n\n" + "Sent by RESCUE ALERT APP";
                        SmsManager smsManager = SmsManager.getDefault();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            smsManager.sendTextMessage(phoneNumber.get(i), null, message, null, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    private void MedPopup() {
        epicDialog.setContentView(R.layout.med_popup);
        e_btn = (Button) epicDialog.findViewById(R.id.medButton);
        TextView med_text = (TextView) epicDialog.findViewById(R.id.med_pop);

        med_text.setText("Calling Medical Assistance will get you in contact with medical specialists who will help you with your condition.");

        e_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "1166";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(Dashboard1.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Dashboard1.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }
/*
                //Message

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                String current_user = firebaseUser.getPhoneNumber();
                Query query = FirebaseDatabase.getInstance().getReference("family").orderByChild("user_ref").equalTo(current_user);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String num = snapshot.child("number").getValue().toString();
                            phoneNumber.add(num);
                        }
                        String message = "There is an Medical Emergency at my place. I'm Calling 1166" + "\n\n\n" + "Sent by RESCUE ALERT APP";
                        SmsManager smsManager = SmsManager.getDefault();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            smsManager.sendTextMessage(phoneNumber.get(i), null, message, null, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
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
            Intent e = new Intent(Dashboard1.this, Dashboard1.class);
            startActivity(e);
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
            Intent t = new Intent(Dashboard1.this, Tracking.class);

            startActivity(t);
        }

        if (id == R.id.nav_setting) {
            Intent i = new Intent(Dashboard1.this, MainSettings.class);
            startActivity(i);
        }

        if (id == R.id.nav_invite) {
            Intent n = new Intent(Dashboard1.this, Invite_activity.class);
            startActivity(n);
        }


        if (id == R.id.nav_contact) {
            Intent o = new Intent(Dashboard1.this, ContactUs.class);
            startActivity(o);
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

    /*private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }*/

    /*@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key == ) {
            Toast.makeText(Dashboard1.this, "Works",Toast.LENGTH_SHORT).show();

            *//*final boolean switchPref = sharedPref.getBoolean
                    ("eme1", false);
            if (!switchPref) {
                Intent intent = new Intent (this, ContactUs.class);
                Log.d("Dashboard1.this","It works");
                startActivity(intent);*//*
            } else {
                Toast.makeText(Dashboard1.this, "SwitchPref doesn't works", Toast.LENGTH_SHORT).show();
            }
        }*/
}