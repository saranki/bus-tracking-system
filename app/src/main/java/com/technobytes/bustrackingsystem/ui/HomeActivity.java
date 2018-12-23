package com.technobytes.bustrackingsystem.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.technobytes.bustrackingsystem.R;

public class HomeActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Driver
        button = findViewById(R.id.btnDriver);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Welcome to Login Driver!",Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(HomeActivity.this, DriverActivity.class);
                startActivity(intent);
            }
        });

        //User
        button = findViewById(R.id.btnUser);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Welcome to get time schedule!",Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }
}
