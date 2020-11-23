package com.example.RescueAlert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class popup extends AppCompatActivity {

    ListView lv;
    Button num1;
    Button num2;
    Button num3;
    Button num4;
    Button num5;
    Button num6;
    Button num7;
    private static final int REQUEST_PHONE_CALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        lv = (ListView) findViewById(R.id.lv);
        num1 = findViewById(R.id.E_num1);
        num2 = findViewById(R.id.E_num2);
        num3 = findViewById(R.id.E_num3);
        num4 = findViewById(R.id.E_num4);
        num5 = findViewById(R.id.E_num5);
        num6 = findViewById(R.id.E_num6);
        num7 = findViewById(R.id.E_num7);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Rescue Ambulance");
        arrayList.add("Police");
        arrayList.add("Fire Brigade");
        arrayList.add("Edhi Ambulance");
        arrayList.add("Medical Assistance");
        arrayList.add("Traffic Police");
        arrayList.add("Punjab Women helpline");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);


        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "1122";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(popup.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(popup.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }
            }
        });
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "15";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(popup.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(popup.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }
            }
        });

        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "16";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(popup.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(popup.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }
            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "115";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(popup.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(popup.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "1166";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(popup.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(popup.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "1915";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(popup.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(popup.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }
            }
        });
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "1043";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(popup.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(popup.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }
            }
        });
    }

}