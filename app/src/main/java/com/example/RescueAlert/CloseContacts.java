package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.client.annotations.NotNull;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CloseContacts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final static String Tag = "CloseContacts";
    TextView number_text, close_num, close_nam;
    ListView family_view;
    FirebaseListAdapter adapter;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button l_btn, del;
    ActionBarDrawerToggle toggle;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        /* Hooks*/
        Button save = findViewById(R.id.add_save);
        Button add1 = findViewById(R.id.adding);
        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view2);
        toolbar = findViewById(R.id.toolbar2);
        l_btn = findViewById(R.id.nav_logout);
        number_text = findViewById(R.id.user_family_number);
        family_view = findViewById(R.id.family_list);
        Button message = findViewById(R.id.add_custom);


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
                sendCircle();
            }
        });
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(CloseContacts.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_fam);

        display();

    }

    public void display() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = firebaseUser.getPhoneNumber();
        Query query = FirebaseDatabase.getInstance().getReference("close_contacts").orderByChild("user_ref").equalTo(current_user);
        FirebaseListOptions<FamilyContact> options = new FirebaseListOptions.Builder<FamilyContact>().setQuery(query, FamilyContact.class).setLayout(android.R.layout.list_content).build();
        adapter = new FirebaseListAdapter<FamilyContact>(options) {

            @Override
            protected void populateView(@NotNull View v, @NotNull final FamilyContact model, int position) {

                close_num = (TextView) v.findViewById(R.id.close_number);
                close_nam = (TextView) v.findViewById(R.id.close_name);
                close_num.setText(model.getClose_number());
                close_nam.setText(model.getClose_name());

                del = (Button) v.findViewById(R.id.delete_button);
                delete_number(model.getClose_number(), del);

            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                View rowView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.family_contact_list, viewGroup, false);
                FamilyContact model = (FamilyContact) getItem(position);
                populateView(rowView, model, position);

                return rowView;
            }

        };
        family_view.setAdapter(adapter);
    }

    public void delete_number(final String close_num, final Button del) {
        mDatabase = FirebaseDatabase.getInstance().getReference("close_contacts");
        mDatabase.orderByChild("close_number").equalTo(close_num).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                            Toast.makeText(CloseContacts.this, "Number deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openContacts() {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }


    public void saveDashboard() {
        Intent intent = new Intent(this, Dashboard1.class);
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

/*    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Dashboard1.class);
        startActivity(i);
    }*/

    public void sendCircle() {

        Intent i = new Intent(this, Family.class);
        startActivity(i);

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.nav_emergency) {
            Intent e = new Intent(CloseContacts.this, Dashboard1.class);
            startActivity(e);
        }
        if (id == R.id.nav_fam) {
            Intent f = new Intent(CloseContacts.this, CloseContacts.class);
            startActivity(f);
        }

        if (id == R.id.nav_circle) {
            Intent c = new Intent(CloseContacts.this, Family.class);
            startActivity(c);
        }

        if (id == R.id.nav_track) {
            //do tracking
            Intent t = new Intent(CloseContacts.this, Tracking.class);

            startActivity(t);
        }

        if (id == R.id.nav_setting) {
            Intent i = new Intent(CloseContacts.this, Settings.class);
            startActivity(i);
        }

        if (id == R.id.nav_invite) {
            Intent n = new Intent(CloseContacts.this, Invite_activity.class);
            startActivity(n);
        }


        if (id == R.id.nav_contact) {
            Intent o = new Intent(CloseContacts.this, ContactUs.class);
            startActivity(o);
        }


        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(CloseContacts.this, " Sign out!", Toast.LENGTH_SHORT).show();
            Intent l = new Intent(CloseContacts.this, Signup.class);
            startActivity(l);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}