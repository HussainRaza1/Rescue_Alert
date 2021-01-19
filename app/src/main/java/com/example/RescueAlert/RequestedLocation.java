package com.example.RescueAlert;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.firebase.client.annotations.NotNull;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.RescueAlert.Family.Tag;

public class RequestedLocation extends FragmentActivity implements OnMapReadyCallback {

    String circle_number;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_location);


        circle_number = getIntent().getStringExtra("circle_number");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("mobileNumber").equalTo(circle_number);
        Log.d(Tag, "number " + circle_number);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("mobileNumber").getValue().equals(circle_number)) {
                        //Log.d(Tag, "Inside live location");
                        UserHelperClass user = snapshot.getValue(UserHelperClass.class);
                        //get crash long lang
                        String lat = user.lat;
                        String lon = user.lon;
                        // Log.d(Tag, "Location " + lat + lon);
                        LatLng loc = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
                        mMap.clear();
                        mMap.addMarker(new
                                MarkerOptions().position(loc).title("User location"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 17));
                    }

                    // show map

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}