package com.example.RescueAlert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;


public class Dashboard extends AppCompatActivity {

    private Button add_family;
    private Button your_circle;
    private Button dash_info;
    private Button r_btn;
    private Button l_btn;
    private static final int REQUEST_PHONE_CALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        add_family = (Button) findViewById(R.id.family);
        your_circle = (Button) findViewById(R.id.Circle);
        dash_info = findViewById(R.id.info);
        r_btn = (Button) findViewById(R.id.red_button);
        l_btn = (Button) findViewById(R.id.LogOut);


        dash_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopUpWindow();
            }
        });
        add_family.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openContacts();
            }
        });
        your_circle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCircle();
            }
        });


        r_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "+923004005053";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Dashboard.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }
                return true;
            }
        });


        l_btn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Dashboard.this, " Sign out!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Signup.class);
                startActivity(i);
            }
        }));


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            }
        } else {
            Toast.makeText(this, "Grant permission to read contacts", Toast.LENGTH_LONG).show();
        }
    }

    public void makeCall() {
        Intent myIntent = new Intent(Intent.ACTION_CALL);
        String phNum = "tel:" + "+923004005053";
        myIntent.setData(Uri.parse(phNum));
        startActivity(myIntent);
    }


    private void openPopUpWindow() {

        Intent intent = new Intent(this, popup.class);
        startActivity(intent);
    }

    public void openContacts() {

        Intent intent = new Intent(this, AddContacts.class);
        startActivity(intent);
    }

    public void openCircle() {
        Intent intent = new Intent(this, Circle.class);
        startActivity(intent);
    }

}