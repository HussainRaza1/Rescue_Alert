package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class AddContacts extends AppCompatActivity {

    final static String Tag="AddContacts";

    private Button save;
    private Button add1;
   // private Button add2;
   // private Button add3;
    private Button message;

    TextView number_text, family_number;
    FamilyContact contact;
    ListView family_view;
    FirebaseListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        /* Hooks*/
        save = findViewById(R.id.add_save);
        add1 = findViewById(R.id.adding);
      //  add2 = findViewById(R.id.adding1);
      //  add3 = findViewById(R.id.adding2);
        number_text = findViewById(R.id.user_family_number);
        family_view= findViewById(R.id.family_list);
        message = findViewById(R.id.add_custom);

       // number = getIntent().getStringExtra("user_phone");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDashboard();
            }
        });

        add1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openContacts();
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocation();
            }
        });

      /*  add2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openContacts();

            }
        });*/

     /*   add3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openContacts();
            }
        });*/


        display();
    }

    public void display(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = firebaseUser.getPhoneNumber();
        Query query = FirebaseDatabase.getInstance().getReference("family").orderByChild("user_ref").equalTo(current_user);
        FirebaseListOptions<FamilyContact> options = new FirebaseListOptions.Builder<FamilyContact>().setQuery(query, FamilyContact.class).setLayout(android.R.layout.list_content).build();
        adapter = new FirebaseListAdapter<FamilyContact>(options) {
            @Override
            protected void populateView(View v, final FamilyContact model, int position) {
                family_number = (TextView) v.findViewById(R.id.family_user);
                family_number.setText(model.getNumber());
                Log.d(Tag, "number " + model.getNumber());
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                View rowView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.family_contact_list, viewGroup, false);
                FamilyContact model = (FamilyContact) getItem(position);

                populateView(rowView,model,position);

                return rowView;
            }

        };
        family_view.setAdapter(adapter);

        Log.e(Tag, "Inside display comment method");
    }
/*
    public void display(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String bleh = firebaseUser.getPhoneNumber();
       FirebaseDatabase.getInstance().getReference("family").orderByChild("number").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               String num = snapshot.child("number").getValue().toString();
               number_text.setText(num);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }*/



   /* public void add_family(final String numbr){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String num = firebaseUser.getPhoneNumber(); //tera yeh hai, maine apne variables use kiye, oh it doesnt matter yahan konse likho

        Log.d("AddContacts", num);

        FirebaseDatabase.getInstance().getReference("users").child(num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String user = dataSnapshot.child(num).getRef().getKey();
                String no = numbr;
                Log.d("AddContacts", user);
                contact = new FamilyContact(no, user);
                FirebaseDatabase.getInstance().getReference("family").push().setValue(contact);
                Toast.makeText(AddContacts.this, "Contact added!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
    public void openContacts() {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }


    public void saveDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }

    public void sendLocation(){

        Intent i = new Intent (this, LocationActivity.class);
        startActivity(i);

    }

}