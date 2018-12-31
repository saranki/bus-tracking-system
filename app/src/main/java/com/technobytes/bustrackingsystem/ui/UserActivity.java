package com.technobytes.bustrackingsystem.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.technobytes.bustrackingsystem.R;

public class UserActivity extends AppCompatActivity implements AdapterView
        .OnItemSelectedListener, View.OnClickListener {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Spinner spinner=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.districts,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button = findViewById(R.id.btnSearch);
        button.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        final String selectedDestination=adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(),selectedDestination,Toast.LENGTH_SHORT).show();

        if(!selectedDestination.equals(null)){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UserActivity.this, SchedulerActivity.class);
                    intent.putExtra("destination", selectedDestination);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        // Toast.makeText(UserActivity.this, "clicked search", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(UserActivity.this, MapsActivity.class);
        Intent intent = new Intent(UserActivity.this, SchedulerActivity.class);
        startActivity(intent);
    }
}
