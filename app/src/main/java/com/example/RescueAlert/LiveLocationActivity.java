package com.example.RescueAlert;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LiveLocationActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    GoogleMap mmap;
    private LocationListener locationListener;
    private LocationManager locationManager;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_location);

        ///foreground service
        Intent intent = new Intent(this, MyService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            startForegroundService(intent);
        } else {
            startService(intent);
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.live_map);
        assert mapFragment != null;
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

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mmap = googleMap;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //locationManager.removeUpdates(this);
        if (mmap != null) {

            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            mmap.clear();
            mmap.addMarker(new
                    MarkerOptions().position(latlng).title("Current location"));
            mmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,17));


            String lat = String.valueOf(location.getLatitude());
            String lon = String.valueOf(location.getLongitude());
            //Log.d("mylocation", String.valueOf(lat) + "   " + String.valueOf(lon));

            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String num = firebaseUser.getPhoneNumber();

            FirebaseDatabase.getInstance().getReference("users").child(num).child("lat").setValue(lat);
            FirebaseDatabase.getInstance().getReference("users").child(num).child("lon").setValue(lon);

        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}

// Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/