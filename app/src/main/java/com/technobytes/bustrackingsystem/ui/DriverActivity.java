package com.technobytes.bustrackingsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.technobytes.bustrackingsystem.R;
import com.technobytes.bustrackingsystem.data.Driver;

public class DriverActivity extends AppCompatActivity {

    private static final String TAG = "DriverActivity";
    DatabaseReference driversReference;

    EditText edtBusNo, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        FirebaseApp.initializeApp(this);
        driversReference = FirebaseDatabase.getInstance().getReference();

        edtBusNo = findViewById(R.id.edtBusNo);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtBusNo = edtBusNo.getText().toString();
                String txtPassword = edtPassword.getText().toString();

                if (isValid(txtBusNo, txtPassword)) {
                    loginDriver(txtBusNo, txtPassword);
                }
            }
        });
    }

    private boolean isValid(String txtBusNo, String txtPassword) {
        if (txtBusNo.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please Enter Bus Number...", Toast.LENGTH_LONG).show();
            return false;
        } else if (txtPassword.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please Enter Password...", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void loginDriver(final String busNo, final String password) {
        ValueEventListener driverInfoListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Driver driver = new Driver(busNo, password);
                    Driver firebaseDriver = dataSnapshot.getValue(Driver.class);

                    if (driver.getBusNo().equals(firebaseDriver.getBusNo()) && driver.getPassword().equals(firebaseDriver.getPassword())) {
                        Toast.makeText(DriverActivity.this, "Driver Logged In...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DriverActivity.this, DriverInfoActivity.class);
                        intent.putExtra("bus_no", busNo);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DriverActivity.this, "Authentication Failed...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DriverActivity.this, "Bus Route Does Not Exist...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DriverActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "Bus Details Not Found...");

            }
        };

        driversReference.child("drivers").child(busNo).addValueEventListener(driverInfoListener);
    }
}
