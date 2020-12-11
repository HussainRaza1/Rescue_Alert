package com.example.RescueAlert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            invokeFirebaseLoginUI();
    }


/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {

                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null){

                Intent i = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(i);
                }
            }

        } else {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Toast.makeText(Login.this, "User is not registered", Toast.LENGTH_LONG).show();
            if(user == null) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        }
    }*/




    private void invokeFirebaseLoginUI() {

        // Choose authentication providers
        final List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build());

         someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request code
                            Intent data = result.getData();
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build();
                        }
                    }
                });
        openSomeActivityForResult();


        // Create and launch sign-in intent
       /* startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);*/

    }

    public void openSomeActivityForResult() {
        Intent intent = new Intent(this, Dashboard1.class);
        someActivityResultLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in


                Intent i = new Intent(Login.this, Dashboard1.class);
                startActivity(i);
                //finish();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(Login.this, "User is not registered", Toast.LENGTH_LONG).show();
                if (user == null) {
                    Intent intent = new Intent(Login.this, Signup.class);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}