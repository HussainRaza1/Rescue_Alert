package com.example.RescueAlert;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback, SharedPreferences.OnSharedPreferenceChangeListener {

    private final int LocationPermissionRequestCode = 1234;
    private final float defaultZoom = 15f;
    String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    Boolean mLocationPermissionGranted = false;
    ArrayList<String> phoneNumber = new ArrayList();
    double longitude1;
    double latitude1;
    String template;
    String message;
    private SharedPreferences sharedPref;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);

        template = sharedPref.getString("template_text", "");

        getLocationPermission();
        setupSharedPreferences();

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    double lat = mMap.getCameraPosition().target.latitude;
                    double lng = mMap.getCameraPosition().target.longitude;
                    longitude1 = lng;
                    latitude1 = lat;
                    send_message(longitude1, longitude1);
                }
            });

        }
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), defaultZoom);
                        } else {

                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }

    }

    private void moveCamera(LatLng latLng, Float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(
                this.getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(
                    this.getApplicationContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, LocationPermissionRequestCode);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LocationPermissionRequestCode);
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LocationPermissionRequestCode: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private boolean userExists(ArrayList<String> num, String phone) {
        for (String number : num) {
            if (number.equals(phone)) {
                return true;
            }
        }
        return false;
    }

    private void send_message(final double myLatitude, final double myLongitude) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = firebaseUser.getPhoneNumber();
        Query query = FirebaseDatabase.getInstance().getReference("close_contacts").orderByChild("user_ref").equalTo(current_user);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String num = snapshot.child("close_number").getValue().toString();
                    if (!userExists(phoneNumber, num)) {
                        phoneNumber.add(num);
                    }
                }
                //Message
                if (template.equals("")) {
                    message = "Can you pick me from " + "http://maps.google.com/maps?q=" + String.valueOf(myLatitude) + "," + String.valueOf(myLongitude);

                } else {
                    message = template + "http://maps.google.com/maps?q=" + String.valueOf(myLatitude) + "," + String.valueOf(myLongitude);
                }

                SmsManager smsManager = SmsManager.getDefault();

                for (String number : phoneNumber) {
                    smsManager.sendTextMessage(number, null, message, null, null);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}