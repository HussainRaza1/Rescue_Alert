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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Circle extends AppCompatActivity {

    final static String Tag = "Circle";
    private Button save;
    private Button add1;

  /*  private Button add2;
    private Button add3;*/

    FirebaseListAdapter<CircleContact> adapter;
    ListView circle_view;
    TextView circle_number, text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        /*Hooks*/
        save = findViewById(R.id.add_save);
        add1 = findViewById(R.id.adding_circle);
        circle_view = findViewById(R.id.circle_list);
        text1 = findViewById(R.id.circle_text_1);
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
    }

    public void display() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = firebaseUser.getPhoneNumber();
        Query query = FirebaseDatabase.getInstance().getReference("circle").orderByChild("user_number").equalTo(current_user);
        FirebaseListOptions<CircleContact> options = new FirebaseListOptions.Builder<CircleContact>().setQuery(query, CircleContact.class).setLayout(android.R.layout.list_content).build();
        adapter = new FirebaseListAdapter<CircleContact>(options) {

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                View rowView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.circle_list_layout, viewGroup, false);
                CircleContact model = (CircleContact) getItem(position);

                populateView(rowView, model, position);

                return rowView;
            }

            @Override
            protected void populateView(@NonNull View v, @NonNull CircleContact model, int position) {
                circle_number = (TextView) v.findViewById(R.id.circle_text);
                circle_number.setText(model.getCircle_number());
                Log.d(Tag, "circle number " + model.getCircle_number());
            }

        };
        circle_view.setAdapter(adapter);

        Log.e(Tag, "Inside display circle method");
    }

    public void saveDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    public void openContacts() {
        Intent intent = new Intent(this, CircleContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
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
}