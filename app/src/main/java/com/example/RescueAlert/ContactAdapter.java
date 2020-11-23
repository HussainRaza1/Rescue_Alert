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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    Context mContext;
    private ArrayList<Contacts> mUser;
    CircleContact circleContact;
    FamilyContact contact;
    int count = 0;

    public ContactAdapter(ArrayList<Contacts> mUser, Context mContext) {
        this.mUser = mUser;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contact_item, parent, false);
        return new ContactAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Contacts user = mUser.get(position);

        holder.user_phone.setText(user.getNumber());
        holder.user_name.setText(user.getName());

        holder.contact_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_family(user.getNumber());
                Intent i = new Intent(mContext, AddContacts.class);
                //i.putExtra("user_phone", user.getNumber());
                mContext.startActivity(i);
            }
        });

        holder.circle_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_circle(user.getNumber());
                Intent i = new Intent(mContext, Circle.class);
               // i.putExtra("user_phone", user.getNumber());
                mContext.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_phone, user_name;
        Button contact_Add, circle_Add;

        public ViewHolder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.contact_name);
            user_phone = itemView.findViewById(R.id.contact_phone);
            contact_Add = itemView.findViewById(R.id.add_contact);
            circle_Add = itemView.findViewById(R.id.circle_add);
        }
    }

    public void add_family(final String numbr) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String num = firebaseUser.getPhoneNumber();

        FirebaseDatabase.getInstance().getReference().child("family").child(num).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    count = (int) dataSnapshot.getChildrenCount();
                    Log.d("ContactAdapter", String.valueOf(count));

                    FirebaseDatabase.getInstance().getReference("users").child(num).addValueEventListener(new ValueEventListener() {


                        ///*count*///
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String user = dataSnapshot.child(num).getRef().getKey();
                            Log.d("AddContacts", user);
                            if(count < 3){
                                contact = new FamilyContact(numbr, user);
                                FirebaseDatabase.getInstance().getReference("family").push().setValue(contact);
                                Toast.makeText(mContext, "Family Contact added!", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(mContext, "Can not add more than three contacts!", Toast.LENGTH_LONG).show();
                            }
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

    public void add_circle(final String numbr) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String num = firebaseUser.getPhoneNumber();

        FirebaseDatabase.getInstance().getReference("circle").child("circle_number").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = (int)dataSnapshot.getChildrenCount();
                Log.d("ContactAdapter", String.valueOf(count));

                FirebaseDatabase.getInstance().getReference("users").child(num).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("CircleAdatper", String.valueOf(dataSnapshot.getChildrenCount()));

                        final String user = dataSnapshot.child(num).getRef().getKey();
                        Log.d("AddContacts", user);
                        if (count < 3) {
                            Log.d("ContactAdapter", String.valueOf(count));
                            circleContact = new CircleContact(numbr, user);
                            FirebaseDatabase.getInstance().getReference("circle").push().setValue(circleContact);
                            Toast.makeText(mContext, "Circle Contact added!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mContext, "Can not add more than three contacts!", Toast.LENGTH_LONG).show();
                        }
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


}
