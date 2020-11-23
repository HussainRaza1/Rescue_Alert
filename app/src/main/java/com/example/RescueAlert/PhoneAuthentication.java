package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("RedundantCast")
public class PhoneAuthentication extends AppCompatActivity {

    private static final String TAG = "PhoneAuthentication";
    EditText number, register_code;
    Button verifyCode, send_code;
    ProgressBar number_bar, code_bar;
    //TextView login;

    String phoneNumber;
    FirebaseAuth firebaseAuth;

    private String phone_verificationId;
    private String codeVerification;
    private PhoneAuthProvider.ForceResendingToken ResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            // Set View to register.xml
            setContentView(R.layout.phone_number);

            number = findViewById(R.id.register_number);
            register_code = findViewById(R.id.register_code);

            number_bar = findViewById(R.id.number_progress_bar);
            code_bar = findViewById(R.id.code_progress_bar);

            send_code = findViewById(R.id.send_code);
            verifyCode = findViewById(R.id.code_verify);

            send_code.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    phoneNumber = number.getText().toString();

                    if (phoneNumber.isEmpty()) {
                        Toast.makeText(PhoneAuthentication.this, "Field Empty", Toast.LENGTH_SHORT).show();
                    } else if (phoneNumber.length() < 10) {
                        Toast.makeText(PhoneAuthentication.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                    } else {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                phoneNumber,
                                60,
                                TimeUnit.SECONDS,
                                PhoneAuthentication.this,
                                mCallbacks);
                    }

                }
            });

            verifyCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    codeVerification = register_code.getText().toString();
                    if (TextUtils.isEmpty(codeVerification)) {
                        Toast.makeText(PhoneAuthentication.this, "Field Empty", Toast.LENGTH_SHORT).show();
                    } else {

                        codeVerification = register_code.getText().toString();
                        code_bar.setVisibility(View.VISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(phone_verificationId, codeVerification);
                        signInWithPhoneAuthCredentials(phoneAuthCredential);

                    }
                }
            });

            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    number_bar.setVisibility(View.VISIBLE);
                    register_code.setText(code);
                    signInWithPhoneAuthCredentials(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        Log.d(TAG, "Invalid Credential: " + e.getLocalizedMessage());
                    }

                }

                @Override
                public void onCodeSent(String verificationID, PhoneAuthProvider.ForceResendingToken forceToken) {
                    //  super.onCodeSent(verificationID, forceToken);

                    phone_verificationId = verificationID;
                    ResendToken = forceToken;

                    Toast.makeText(PhoneAuthentication.this, "Code has been sent", Toast.LENGTH_SHORT).show();
                }
            };
        }


    public void signInWithPhoneAuthCredentials(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            Intent i = new Intent(PhoneAuthentication.this, Dashboard.class);
//                            i.putExtra("phonenumber", phoneNumber);
                            startActivity(i);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(PhoneAuthentication.this, "verification code was invalid", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                });
    }
}

