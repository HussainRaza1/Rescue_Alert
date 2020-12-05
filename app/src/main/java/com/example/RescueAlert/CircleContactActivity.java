package com.example.RescueAlert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

    public class CircleContactActivity extends AppCompatActivity {
        String Tag = "ContactsActivity";
        private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
        private ArrayList<UserHelperClass> mUsers = new ArrayList<UserHelperClass>();
        private ArrayList<Contacts> contactList = new ArrayList<>();
        CircleAdapter viewAdapter;
        RecyclerView recyclerView;
        Cursor contacts;
        Contacts mContact;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.circle_contact);
            LinearLayoutManager layoutManager = new LinearLayoutManager(CircleContactActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerView = findViewById(R.id.circle_contact_list);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);


            int permissionCheck = ContextCompat.checkSelfPermission(CircleContactActivity.this, Manifest.permission.READ_CONTACTS);
            if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                readContactList();
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContactList();
                }
            } else {
                Toast.makeText(this, "Grant permission to read contacts", Toast.LENGTH_LONG).show();
            }
        }

        public  void readContactList(){
            String ISOPrefix = getCountryISO();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                contacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            }
            while(contacts.moveToNext()) {
                String name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));/*
                phone = phone.replace(" ", "");
                phone = phone.replace("-", "");
                phone = phone.replace("(", "");
                phone = phone.replace(")", "");

                if(String.valueOf(phone.charAt(0)).equals("0"))
                {
                    phone = phone.replaceFirst("0","");
                }

                if (!String.valueOf(phone.charAt(0)).equals("+")) {
                    phone = ISOPrefix + phone;
                }*/
                mContact = new Contacts(phone, name);
                contactList.add(mContact);
                Log.d(Tag, "contacts: " + contactList);
                readUsers(mContact);
                Log.d(Tag, "current contact" +mContact.toString());
            }
            Log.d(Tag, "Inside contact list method");
        }

        private String getCountryISO() {
            String iso = null;
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(CircleContactActivity.this.TELEPHONY_SERVICE);
            if(telephonyManager.getNetworkCountryIso()!=null){
                if (!telephonyManager.getNetworkCountryIso().toString().equals("")){
                    iso = telephonyManager.getNetworkCountryIso().toString();
                }
            }

            return CountryToPhonePrefix.getPhone(iso.toUpperCase());
        }

        private boolean userExists(ArrayList<UserHelperClass> usersList, String phone) {
            for(UserHelperClass user : usersList) {
                if (user.getMobileNumber().equals(phone)) {
                    return true;
                }
            }
            return false;
        }

        public void readUsers(final Contacts mContact){
            final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            Query query = databaseReference.orderByChild("mobileNumber").equalTo(mContact.getNumber());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if(snapshot.child("mobileNumber").getValue().equals(mContact.getNumber())) {
                            Log.d(Tag, "Inside read users");
                            UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                            if (!user.getMobileNumber().equals(firebaseUser.getPhoneNumber())) {
                                if(!userExists(mUsers, mContact.getNumber())) {
                                    mUsers.add(user);
                                }
                            }
                        }
                    }
                    viewAdapter = new CircleAdapter(mUsers, CircleContactActivity.this);
                    recyclerView.setAdapter(viewAdapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("ContactsActivity", "cancelled");
                }
            });
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
