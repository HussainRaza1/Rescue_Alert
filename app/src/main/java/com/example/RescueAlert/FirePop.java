package com.example.RescueAlert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirePop extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 1;
    ArrayList<String> phoneNumber = new ArrayList();
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fire_popup);
    /*    //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popFire = inflater.inflate(R.layout.fire_popup, null);


        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popFire, width, height, true);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler

        TextView pop_text = findViewById(R.id.fire_pop);
        pop_text.setText("Pressing call will send a message to the people in your Close Contacts, informing them of the fire");

        Button buttonEdit = findViewById(R.id.fireButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/
                //Call
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "16";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(FirePop.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FirePop.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }

                //Message

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String current_user = firebaseUser.getPhoneNumber();
                Query query = FirebaseDatabase.getInstance().getReference("family").orderByChild("user_ref").equalTo(current_user);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String num = snapshot.child("number").getValue().toString();
                            phoneNumber.add(num);
                        }
                        String message = "There is an Fire at my place. I'm Calling the Fire brigade" + "\n\n\n" + "Sent by RESCUE ALERT APP";
                        SmsManager smsManager = SmsManager.getDefault();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            smsManager.sendTextMessage(phoneNumber.get(i), null, message, null, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

               /* popFire.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //Close the window when clicked
                        popupWindow.dismiss();
                        return true;
                    }
                });*/
            }
        }