package com.technobytes.bustrackingsystem.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technobytes.bustrackingsystem.R;

import java.io.IOException;
import java.util.List;

public class SchedulerActivity extends AppCompatActivity implements LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final String TAG = "TimeSchedule";
    TextView destination;
    TextView currentLocation;
    TextView busNo;
    TextView startTime;
    String retDestination;
    String route;
    Button map;
    DatabaseReference schedulerReference;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        FirebaseApp.initializeApp(this);
        schedulerReference = FirebaseDatabase.getInstance().getReference();

        destination = findViewById(R.id.txtDestination);
        currentLocation = findViewById(R.id.txtCurrentLocation);
        busNo = findViewById(R.id.txtBusNo);
        startTime = findViewById(R.id.txtTime);
        map = findViewById(R.id.btnCheck);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SchedulerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Log.d(TAG, "ACCESS_FINE_LOCATION permission granted...");
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
        String curLocation = findLocationName(location);

        Intent intent = getIntent();
        retDestination = intent.getStringExtra("destination");
        destination.setText(retDestination);
        Log.d(TAG, "Destination: " + retDestination);

        checkBusSchedule("Vavuniya", retDestination);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(SchedulerActivity.this, MapsActivity.class);
                startActivity(mapIntent);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d(TAG, "Lat: " + Double.toString(latitude));
        Log.d(TAG, "Long: " + Double.toString(longitude));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private String findLocationName(Location location) {
        String city = null;
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = null;
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            city = addresses.get(0).getLocality();
            Log.d(TAG, "City name: " + city);
            currentLocation.setText(city);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Fine location permission granted...");
                } else {
                    Log.d(TAG, "Error in granting permission...");
                }
            }
        }
    }

    private void checkBusSchedule(final String startPoint, final String endPoint) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            String routeName;
            String busNumber;
            String time;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot scheduler : dataSnapshot.getChildren()) {
                    routeName = scheduler.child("route").getValue().toString();
                    busNumber = scheduler.getKey();
                    time = scheduler.child("startTime").getValue().toString();

                    if(((routeName.split("-")[0].equals(endPoint)) || (routeName.split("-")[0].equals(startPoint)))
                            &&((routeName.split("-")[1].equals(endPoint)) || (routeName.split("-")[1].equals(startPoint)))){
                        busNo.setText(busNumber);
                        startTime.setText(time);
                    }
                    Log.d(TAG,"First----------> " + routeName.split("-")[0]);
                    Log.d(TAG,"Second----------> " + routeName.split("-")[1]);
                    Log.d(TAG, "Firebase---------> " + scheduler.child("route").getValue());
                    Log.d(TAG, "Firebase---------> " + busNumber);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Error message: " + databaseError);
            }
        };

        schedulerReference.child("locations").addValueEventListener(valueEventListener);
    }
}