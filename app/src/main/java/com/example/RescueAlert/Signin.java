package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {

    EditText login_text;
    Button login_button;
    private CirclePhone mContact;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        login_text = findViewById(R.id.Login_entered);
        login_button = findViewById(R.id.signin);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*checkNumber();*/
                checksum();
            }
        });


    }

    private void checksum() {

        Intent intent = new Intent (this, Dashboard1.class);
        startActivity(intent);
        finish();

        }


    }

    /*public void checkNumber() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(Tag, "Inside read users");
                    UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                    if (user.getMobileNumber().equals(login_text.getText())) {

                        Toast.makeText(Signin.this, "Login Successful", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(Signin.this, Dashboard1.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(Signin.this, "Login Failed", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(Signin.this, Signup.class);
                        startActivity(intent);
                    }
                    finish();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }*/