package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {

    EditText login_text;
    Button login_button;
    TextInputLayout login_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        login_text = findViewById(R.id.Login_entered);
        login_button = findViewById(R.id.signin);
        login_input = findViewById(R.id.code_entered);


    }

    private Boolean validateText() {
        String val = login_input.getEditText().getText().toString();
        if (val.isEmpty()) {
            login_input.setError(("Field cannot be empty"));
            return false;
        } else {
            login_input.setError(null);
            login_input.setErrorEnabled(false);
            return true;
        }

    }

    public void loginUser(View view) {
        if (!validateText()) {
            return;
        } else {
            isUser();
        }

    }

    private void isUser() {

        final String EnterPhone = login_input.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("mobileNumber").equalTo(EnterPhone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    login_input.setError(null);
                    login_input.setErrorEnabled(false);

                    /*String phoneFromDB = snapshot.child(EnterPhone).child("mobileNumber").getValue(String.class);
                    if (phoneFromDB.equals(EnterPhone)) {*/
                    Intent intent = new Intent(Signin.this, Dashboard1.class);
                    startActivity(intent);
                    finish();
                } else {
                    login_input.setError("No such user exists");
                    login_input.requestFocus();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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