package com.example.RescueAlert;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class CircleRecyler extends RecyclerView.Adapter<CircleRecyler.ViewHolder> {
    Context mContext;
    CircleContact circleContact;
    private ArrayList<UserHelperClass> mUser;

    public CircleRecyler(ArrayList<UserHelperClass> mUser, Context mContext) {
        this.mUser = mUser;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.circle_contact_item, parent, false);
        return new CircleRecyler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserHelperClass user = mUser.get(position);

        holder.user_phone.setText(user.getMobileNumber());
        holder.user_name.setText(user.getName());

        holder.circle_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_circle(user.getMobileNumber());
                Intent i = new Intent(mContext, Circle.class);
                // i.putExtra("user_phone", user.getNumber());
                mContext.startActivity(i);
            }
        });

    }

    public void setList(ArrayList<UserHelperClass> newList) {
        this.mUser = newList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public void add_circle(final String numbr) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String num = firebaseUser.getPhoneNumber();

        FirebaseDatabase.getInstance().getReference("circle").child("circle_number").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FirebaseDatabase.getInstance().getReference("users").child(num).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("CircleRecycler", String.valueOf(dataSnapshot.getChildrenCount()));

                        final String user = dataSnapshot.child(num).getRef().getKey();

                            circleContact = new CircleContact(numbr, user);
                            FirebaseDatabase.getInstance().getReference("circle").push().setValue(circleContact);
                            Toast.makeText(mContext, "Circle Contact added!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_phone, user_name;
        Button circle_Add;

        public ViewHolder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.circle_contact_name);
            user_phone = itemView.findViewById(R.id.circle_contact_phone);
            circle_Add = itemView.findViewById(R.id.circle_add);
        }
    }


}