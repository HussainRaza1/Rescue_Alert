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

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;


public class Family extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final static String Tag = "Family";
    FirebaseListAdapter<CircleContact> adapter;
    ListView circle_view;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView circle_number, circle_username, text2;
    ActionBarDrawerToggle toggle;
    private Button save, circ;
    private Button add1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        /*Hooks*/
        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view2);
        toolbar = findViewById(R.id.toolbar2);
        save = findViewById(R.id.add_save);
        add1 = findViewById(R.id.adding_circle);
        circle_view = findViewById(R.id.circle_list);
        circ = findViewById(R.id.circle_location);
        text2 = findViewById(R.id.circle_text_2);


        add1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openContacts();
            }
        });
        display();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDashboard();
            }
        });

        circ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenClose();
            }
        });

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(Family.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_circle);

    }


    public void display() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = firebaseUser.getPhoneNumber();
        Query query = FirebaseDatabase.getInstance().getReference("family").orderByChild("user_number").equalTo(current_user);
        FirebaseListOptions<CircleContact> options = new FirebaseListOptions.Builder<CircleContact>().setQuery(query, CircleContact.class).setLayout(android.R.layout.list_content).build();
        adapter = new FirebaseListAdapter<CircleContact>(options) {

            @Override
            public View getView(int position, View view, @NotNull ViewGroup viewGroup) {
                View rowView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.circle_list_layout, viewGroup, false);
                CircleContact model = (CircleContact) getItem(position);
                populateView(rowView, model, position);
                return rowView;
            }

            @Override
            protected void populateView(@NonNull View v, @NonNull CircleContact model, int position) {
                circle_number = (TextView) v.findViewById(R.id.circle_text);
                circle_username = (TextView) v.findViewById(R.id.circle_contact_name);
                circle_number.setText(model.getCircle_number());
                circle_username.setText(model.getCircle_user_name());
                Log.d(Tag, "circle number " + model.getCircle_number());
            }
        };

        circle_view.setAdapter(adapter);
        Log.e(Tag, "Inside display circle method");
    }

    public void saveDashboard() {
        Intent intent = new Intent(this, Dashboard1.class);
        startActivity(intent);
        finish();
    }


    private void OpenClose() {
        Intent intent = new Intent(this, CloseContacts.class);
        startActivity(intent);
        finish();
    }

    public void openContacts() {
        Intent intent = new Intent(this, CircleContacts.class);
        startActivity(intent);
    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }*/

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
            Intent e = new Intent(Family.this, Dashboard1.class);
            startActivity(e);
        }
        if (id == R.id.nav_fam) {
            Intent f = new Intent(Family.this, CloseContacts.class);
            startActivity(f);
        }

        if (id == R.id.nav_circle) {
            Intent c = new Intent(Family.this, Family.class);
            startActivity(c);
        }

        if (id == R.id.nav_track) {
            //do tracking
            Intent t = new Intent(Family.this, Tracking.class);

            startActivity(t);
        }

        if (id == R.id.nav_setting) {
            Intent i = new Intent(Family.this, Settings.class);
            startActivity(i);
        }

        if (id == R.id.nav_invite) {
            Intent n = new Intent(Family.this, Invite_activity.class);
            startActivity(n);
        }


        if (id == R.id.nav_contact) {
            Intent o = new Intent(Family.this, ContactUs.class);
            startActivity(o);
        }


        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Family.this, " Sign out!", Toast.LENGTH_SHORT).show();
            Intent l = new Intent(Family.this, Signup.class);
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