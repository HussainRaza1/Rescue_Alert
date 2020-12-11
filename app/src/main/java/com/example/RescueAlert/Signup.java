package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    TextInputLayout regNo, regPin, regName;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    LatLng regLat, regLon;
    private Button signButton;
    private Button reg_btn;
    private Button logButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
        } else {*/
        setContentView(R.layout.activity_signup);

        /*Hooks*/
        regNo = findViewById(R.id.MobileNo);
        regPin = findViewById(R.id.pin);
        regName = findViewById(R.id.Name);
        reg_btn = (Button) findViewById(R.id.register);
        logButton = (Button) findViewById(R.id.Login1);


        logButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openLogin();
            }
        });


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");


                String PIN = regPin.getEditText().getText().toString();
                String mobileNumber = regNo.getEditText().getText().toString();
                String name = regName.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(mobileNumber, PIN, name, null, null);
                reference.child(mobileNumber).setValue(helperClass);

                Intent i = new Intent(Signup.this, Authenticate.class);
                i.putExtra("mobileNumber", mobileNumber);
                startActivity(i);

            }

        });
    }

    /*private boolean validNumber() {

        String val = regNo.getEditText().getText().toString();
        if (val.isEmpty()) {

            regNo.setError("Field cannot be empty");
            return false;

        } else if (val.length() > 13) {

            regNo.setError("Number too long");
            return false;

        } else {

            regNo.setError(null);
            regNo.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validPIN() {

        String val = regPin.getEditText().getText().toString();
        if (val.isEmpty()) {

            regPin.setError("Field cannot be empty");
            return false;

        } else if (val.length() > 4) {

            regPin.setError("PIN too long");
            return false;

        } else {

            regPin.setError(null);
            regPin.setErrorEnabled(false);
            return true;
        }

    }


    private boolean validEmail() {

        String val = regEmail.getEditText().getText().toString();
        if (val.isEmpty()) {

            regEmail.setError("Field cannot be empty");
            return false;

        } else {

            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }

    }

    public void SignUser(View view) {

        if (!validNumber() | !validPIN() | !validEmail()) {

            return;
        }
    }
*/


    public void openLogin() {
        String mobileNumber = regNo.getEditText().getText().toString();
        Intent intent = new Intent(this, Authenticate.class);
        intent.putExtra("mobileNumber", mobileNumber);
        startActivity(intent);
        //finish();
    }
}
