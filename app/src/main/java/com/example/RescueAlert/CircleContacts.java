package com.example.RescueAlert;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class CircleContacts extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    String Tag = "ContactsActivity";
    CircleRecyler viewAdapter;
    RecyclerView recyclerView;
    Cursor contacts;
    CirclePhone mContact;
    private ArrayList<UserHelperClass> mUsers = new ArrayList<UserHelperClass>();
    private ArrayList<CirclePhone> contactList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_contact);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CircleContacts.this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.circle_contact_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        mUsers = new ArrayList<>();
        viewAdapter = new CircleRecyler(mUsers, CircleContacts.this);
        recyclerView.setAdapter(viewAdapter);


        int permissionCheck = ContextCompat.checkSelfPermission(CircleContacts.this, Manifest.permission.READ_CONTACTS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            readContactList();
        } else {
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

    public void readContactList() {
        String ISOPrefix = getCountryISO();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            contacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        }
        while (contacts.moveToNext()) {
            String name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phone = phone.replace(" ", "");
            phone = phone.replace("-", "");
            phone = phone.replace("(", "");
            phone = phone.replace(")", "");

            if (String.valueOf(phone.charAt(0)).equals("0")) {
                phone = phone.replaceFirst("0", "");
            }

            if (!String.valueOf(phone.charAt(0)).equals("+")) {
                phone = ISOPrefix + phone;
            }
            mContact = new CirclePhone(phone, name);
            contactList.add(mContact);
            Log.d(Tag, "contacts: " + contactList);
            readUsers(mContact);
            Log.d(Tag, "current contact" + mContact.toString());
        }
        Log.d(Tag, "Inside contact list method");
    }

    private String getCountryISO() {
        String iso = null;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(CircleContacts.this.TELEPHONY_SERVICE);
        if (telephonyManager.getNetworkCountryIso() != null) {
            if (!telephonyManager.getNetworkCountryIso().toString().equals("")) {
                iso = telephonyManager.getNetworkCountryIso().toString();
            }
        }

        return CountryToPhonePrefix.getPhone(iso.toUpperCase());
    }

    private boolean userExists(ArrayList<UserHelperClass> usersList, String phone) {
        for (UserHelperClass user : usersList) {
            if (user.getMobileNumber().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    private boolean familyUserExists(ArrayList<FamilyContact> usersList, String phone) {
        for (FamilyContact user : usersList) {
            if (user.getNumber().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    public void readUsers(final CirclePhone mContact) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query query = databaseReference.orderByChild("mobileNumber").equalTo(mContact.getNumber());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("mobileNumber").getValue().equals(mContact.getNumber())) {
                        Log.d(Tag, "Inside read users");
                        final UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                        if (!user.getMobileNumber().equals(firebaseUser.getPhoneNumber())) {
                            if (!userExists(mUsers, mContact.getNumber())) {
                                mUsers.add(user);
                            }
                        }
                    }
                }
                ///mUsers.add(new UserHelperClass("123456789","1234","gmail.com"));
                viewAdapter.setList(mUsers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ContactsActivity", "cancelled");
            }
        });
    }
/*
    public void setFamilyUsers(final String number){
        FirebaseDatabase.getInstance().getReference("family").orderByChild("number").equalTo(number)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            final FamilyContact family_user = dataSnapshot.getValue(FamilyContact.class);
                            if (snapshot.child("number").getValue().equals(number)){
                                if (!familyUserExists(familyUsers, number)) {
                                    familyUsers.add(family_user);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }*/

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
