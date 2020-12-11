package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Signin extends AppCompatActivity {

    String codeVerification;
    String verificationCodeBySystem;
    EditText login_text;
    Button login_button;
    TextInputLayout login_input;
    EditText codeField;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);



        login_text = findViewById(R.id.Login_entered);
        login_button = findViewById(R.id.signin);
        login_input = findViewById(R.id.code_entered);
        codeField = findViewById(R.id.code_verify_field);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private Boolean validateText() {
        String val = login_text.getText().toString();
        if (val.isEmpty()) {
            login_text.setError(("Field cannot be empty"));
            return false;
        } else {
            return true;
        }

    }

    public void loginUser() {
        if (!validateText()) {
            return;
        } else {
            sendVerificationCodeToUser(login_text.getText().toString());
        }
    }


   /* private void isUser() {

        final String EnterPhone = login_text.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("mobileNumber").equalTo(EnterPhone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                 *//*   if (dataSnapshot.exists()) {
                        login_input.setError(null);
                        login_input.setErrorEnabled(false);*//*

                    String phoneFromDB = dataSnapshot.child("mobileNumber").getValue(String.class);
                    if (phoneFromDB.equals(EnterPhone)) {

                        String phone = dataSnapshot.child(EnterPhone).child("mobileNumber").getValue(String.class);
                        Intent p = new Intent(getApplicationContext(), Authenticate.class);
                        p.putExtra("mobile_number", phone);
                        startActivity(p);
                        finish();
                    } else {
                        login_input.setError("No such user exists");
                        login_input.requestFocus();
                    }
                    // }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationCodeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        codeField.setText(code);
                        progressBar.setVisibility(View.VISIBLE);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(Signin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void sendVerificationCodeToUser(String phoneNo) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)                        // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)       // Timeout and unit
                        .setActivity(this)                              // Activity (for callback binding)
                        .setCallbacks(mCallbacks)                       // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Signin.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Signin.this, "Your Account has been created successfully!", Toast.LENGTH_SHORT).show();

                            //Perform Your required action here to either let the user sign In or do something required

                            Intent intent = new Intent(Signin.this, Dashboard1.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                        } else {
                            Toast.makeText(Signin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
