package com.technobytes.bustrackingsystem.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    FirebaseDatabase database;
    DatabaseReference drivers;

    EditText edtBusNo,edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        drivers = database.getReference("drivers");

        edtBusNo=findViewById(R.id.edtBusNo);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(edtBusNo.toString(),edtPassword.toString());
            }
        });


    }

    // Login method body
    private void driverAuthenticate(final String busNo, final String password){
        Toast.makeText(DriverActivity.this,"Entered",Toast.LENGTH_SHORT).show();
        drivers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(DriverActivity.this,"Data change",Toast.LENGTH_SHORT).show();
                if(dataSnapshot.child(busNo).exists()){
                    Toast.makeText(DriverActivity.this,"Exists",Toast.LENGTH_SHORT).show();
                    if((!busNo.isEmpty())&&(!password.isEmpty())){
                        Toast.makeText(DriverActivity.this,"Pass and No",Toast.LENGTH_SHORT).show();
                        Toast.makeText(DriverActivity.this,password+ " "+busNo+" "+"from auth",Toast.LENGTH_SHORT).show();
                        Driver driverCredentials = dataSnapshot.child(busNo).getValue(Driver.class);
                        if(driverCredentials.getPassword().equals(password)){
                            Toast.makeText(DriverActivity.this,"Credentials"+driverCredentials.getPassword().toString(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(DriverActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                            Log.d("Message",busNo+" "+password);
                        }
                    }
                    else{
                        Toast.makeText(DriverActivity.this,"Password is wrong",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(DriverActivity.this,"Bus No is not Registered",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void login(final String busNo, final String password){
        Toast.makeText(DriverActivity.this,"Entered",Toast.LENGTH_SHORT).show();

        ValueEventListener driverListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(DriverActivity.this,"2nd Entered",Toast.LENGTH_SHORT).show();

                Driver driver = dataSnapshot.getValue(Driver.class);
                if((driver.getBusNo().equals(busNo))&&(driver.getPassword().equals(password))){
                    Toast.makeText(DriverActivity.this,"Logged in",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(DriverActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        drivers.addValueEventListener(driverListener);
    }
}
