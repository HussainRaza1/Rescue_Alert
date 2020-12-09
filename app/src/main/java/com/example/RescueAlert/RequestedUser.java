package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RequestedUser extends AppCompatActivity {

    Button back_btn, fam_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_user);


        back_btn = findViewById(R.id.back_req);
        fam_btn = findViewById(R.id.fam_req);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backReq();
            }
        });

        fam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famReq();
            }
        });

    }


    private void famReq() {
        Intent intent = new Intent(this, Circle.class);
        startActivity(intent);
        finish();
    }

    private void backReq() {
        Intent intent = new Intent(this, Tracking.class);
        startActivity(intent);
        finish();
    }
}