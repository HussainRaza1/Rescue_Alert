package com.example.RescueAlert;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MyService extends Service implements LocationListener {


    private LocationManager locationManager;

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0, this);

        createNotificationChannel();


        Intent intent1 = new Intent(this, LiveLocationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        Notification notification = new NotificationCompat.Builder(this, "ChannelID1")
                .setContentTitle("ALERT!")
                .setContentText("You location is being tracked")
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentIntent(pendingIntent).build();

        startForeground(1, notification);
        return START_STICKY;
    }

    private void createNotificationChannel() {

        ///we have to check if the OS is oreo or above

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    "ChannelID1", "Foreground notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

        stopForeground(true);
        stopSelf();

        super.onDestroy();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());
        //Log.d("mylocation", String.valueOf(lat) + "   " + String.valueOf(lon));

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String num = firebaseUser.getPhoneNumber();

        FirebaseDatabase.getInstance().getReference("users").child(num).child("latitude").setValue(lat);
        FirebaseDatabase.getInstance().getReference("users").child(num).child("longitude").setValue(lon);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
