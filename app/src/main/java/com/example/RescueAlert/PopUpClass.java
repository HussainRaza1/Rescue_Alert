package com.example.RescueAlert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PopUpClass extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 1;
    ArrayList<String> phoneNumber = new ArrayList();

    public void showPopupWindow(View view) {
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popEme = inflater.inflate(R.layout.emergency_popup, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popEme, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler

        TextView pop_text = popEme.findViewById(R.id.emergency_pop);
        pop_text.setText("Pressing call will send a message to the people in your Close Contacts, informing them of an emergency");

        Button buttonEdit = popEme.findViewById(R.id.emeButton);

        popEme.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "1122";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(PopUpClass.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PopUpClass.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
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
                        String message = "There is an Emergency at my place. I'm Calling an Ambulance" + "\n\n\n" + "Sent by RESCUE ALERT APP";
                        SmsManager smsManager = SmsManager.getDefault();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            smsManager.sendTextMessage(phoneNumber.get(i), null, message, null, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    public void showPopupWindowFire(final View view) {
        //Create a View object yourself through inflater
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

        TextView pop_text = popFire.findViewById(R.id.fire_pop);
        pop_text.setText("Pressing call will send a message to the people in your Close Contacts, informing them of the fire");

        Button buttonEdit = popFire.findViewById(R.id.fireButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent fire = new Intent(getApplicationContext(), FirePop.class);
                startActivity(fire);

                popFire.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //Close the window when clicked
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
    }

    public void showPopupWindowPolice(final View view) {
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popPolice = inflater.inflate(R.layout.police_popup, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popPolice, width, height, true);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler

        TextView pop_text = popPolice.findViewById(R.id.police_pop);
        pop_text.setText("Pressing call will send a message to the people in your Close Contacts, informing them of the Emergency");

        Button buttonEdit = popPolice.findViewById(R.id.policeButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "15";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(PopUpClass.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PopUpClass.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
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
                        String message = "There is an Emergency at my place. I'm Calling the police" + "\n\n\n" + "Sent by RESCUE ALERT APP";
                        SmsManager smsManager = SmsManager.getDefault();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            smsManager.sendTextMessage(phoneNumber.get(i), null, message, null, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                popPolice.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //Close the window when clicked
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
    }

    public void showPopupWindowMedical(final View view) {
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popMed = inflater.inflate(R.layout.med_popup, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popMed, width, height, true);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler

        TextView pop_text = popMed.findViewById(R.id.med_pop);
        pop_text.setText("Pressing call will send a message to the people in your Close Contacts, informing them of emergency");

        Button buttonEdit = popMed.findViewById(R.id.medButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "16";
                myIntent.setData(Uri.parse(phNum));
                if (ContextCompat.checkSelfPermission(PopUpClass.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PopUpClass.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(myIntent);
                }

                //Message

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String current_user = firebaseUser.getPhoneNumber();
                Query query = FirebaseDatabase.getInstance().getReference("family").orderByChild("user_ref").equalTo(current_user);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String num = snapshot.child("number").getValue().toString();
                            phoneNumber.add(num);
                        }
                        String message = "There is an emergency at my place. I'm Calling Medical Assistance" + "\n\n\n" + "Sent by RESCUE ALERT APP";
                        SmsManager smsManager = SmsManager.getDefault();
                        for (int i = 0; i < phoneNumber.size(); i++) {
                            smsManager.sendTextMessage(phoneNumber.get(i), null, message, null, null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                popMed.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //Close the window when clicked
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        });
    }
}