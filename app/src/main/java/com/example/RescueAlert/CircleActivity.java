package com.example.RescueAlert;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CircleActivity extends AppCompatActivity {

    final static String Tag ="CircleActivity";

    RecyclerView recyclerView;
    CircleContact contact;
    String numb;
    ArrayList userlist = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CircleActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.contact_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Toast.makeText(this, "Circle activity", Toast.LENGTH_LONG).show();

       // numb = getIntent().getStringExtra("user_phone");
       // userExists(numb);

    }

    /*private boolean userExists(ArrayList<UserHelperClass> users, String phone) {
        for(UserHelperClass contact : users) {
            if (contact.getMobileNumber().equals(phone)) {
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
                            if(!userExists(user, mContact.getNumber())) {
                                mUsers.add(user);
                            }
                        }
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ContactsActivity", "cancelled");
            }
        });
    }
    public void add_family(String circle,final String numbr) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String num = firebaseUser.getPhoneNumber();


        FirebaseDatabase.getInstance().getReference("users").child(num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String user = dataSnapshot.child(num).getRef().getKey();
                Log.d("AddContacts", user);
                if(!userExists()) {
                    contact = new CircleContact(numbr, user);
                    FirebaseDatabase.getInstance().getReference("family").push().setValue(contact);
                    Toast.makeText(CircleActivity.this, "Contact added!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
*/
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
