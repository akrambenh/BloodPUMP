package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CompleteRegistrationActivity extends AppCompatActivity {
    private Spinner sex_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);
        sex_spinner = (Spinner) findViewById(R.id.sex_spinner);
        // Adding Sex Options With Array Adapter
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.Sex, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_spinner.setAdapter(adapter);


    }


}