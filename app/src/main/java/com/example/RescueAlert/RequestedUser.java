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

import com.firebase.client.annotations.NotNull;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.example.RescueAlert.Circle.Tag;

public class RequestedUser extends AppCompatActivity {
    FirebaseListAdapter adapter;
    TextView req_number;
    Button back_btn, fam_btn;
    ListView req_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_user);


        back_btn = findViewById(R.id.back_req);
        fam_btn = findViewById(R.id.fam_req);
        req_user = findViewById(R.id.req_users);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backReq();
            }
        });
        fam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famReq();
            }
        });

        Display();
    }

    private void Display() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = firebaseUser.getPhoneNumber();
        Query query = FirebaseDatabase.getInstance().getReference("circle").orderByChild("circle_number").equalTo(current_user);
        FirebaseListOptions<CircleContact> options = new FirebaseListOptions.Builder<CircleContact>().setQuery(query, CircleContact.class).setLayout(android.R.layout.list_content).build();
        adapter = new FirebaseListAdapter<CircleContact>(options) {

            @Override
            protected void populateView(@NotNull View v, @NotNull final CircleContact model, int position) {
                req_number = (TextView) v.findViewById(R.id.req_list);
                req_number.setText(model.getUser_number());
               // Log.d(Tag, "number " + model.getUser_number());

                final String circle_user_number = model.getUser_number();
                req_number.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), RequestedLocation.class);
                        intent.putExtra("circle_number", circle_user_number);
                        startActivity(intent);
                    }
                });
            }




            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                View rowView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.requestedusers_list, viewGroup, false);
                CircleContact model = (CircleContact) getItem(position);

                populateView(rowView, model, position);


                return rowView;
            }

        };
        req_user.setAdapter(adapter);

        Log.e(Tag, "Inside display comment method");
    }


    private void famReq() {
        Intent intent = new Intent(this, Circle.class);
        startActivity(intent);
        finish();
    }


    private void backReq() {

        Intent intent = new Intent(this, Tracking.class);
        startActivity(intent);
        finish();

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