package com.example.RescueAlert;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AddContacts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final static String Tag = "AddContacts";
    TextView number_text, family_number;
    FamilyContact contact;
    ListView family_view;
    FirebaseListAdapter adapter;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button l_btn;

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AddContacts.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_emergency);

        display();
    }

    public void display() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String current_user = firebaseUser.getPhoneNumber();
        Query query = FirebaseDatabase.getInstance().getReference("family").orderByChild("user_ref").equalTo(current_user);
        FirebaseListOptions<FamilyContact> options = new FirebaseListOptions.Builder<FamilyContact>().setQuery(query, FamilyContact.class).setLayout(android.R.layout.list_content).build();
        adapter = new FirebaseListAdapter<FamilyContact>(options) {

            @Override
            protected void populateView(@NotNull View v, @NotNull final FamilyContact model, int position) {
                family_number = (TextView) v.findViewById(R.id.family_user);
                family_number.setText(model.getNumber());
                Log.d(Tag, "number " + model.getNumber());
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

        Log.e(Tag, "Inside display comment method");
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

        Intent i = new Intent(this, Circle.class);
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

        switch (menuItem.getItemId()) {

            case R.id.nav_emergency:
                Intent e = new Intent(AddContacts.this, Dashboard1.class);
                startActivity(e);
                break;
            case R.id.nav_fam:
                break;
            case R.id.nav_circle:
                Intent c = new Intent(AddContacts.this, Circle.class);
                startActivity(c);
                break;
            case R.id.nav_track:
                Intent t = new Intent(AddContacts.this, Tracking.class);
                startActivity(t);
            case R.id.nav_setting:
                Intent s = new Intent(AddContacts.this, Settings.class);
                startActivity(s);
            case R.id.nav_contact:
                Intent u = new Intent(AddContacts.this, ContactUs.class);
                startActivity(u);
                break;
            case R.id.nav_invite:
                Intent i = new Intent(AddContacts.this, Invite_activity.class);
                startActivity(i);
                break;
            case R.id.nav_logout:

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AddContacts.this, " Sign out!", Toast.LENGTH_SHORT).show();
                Intent l = new Intent(getApplicationContext(), Signup.class);
                startActivity(l);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}