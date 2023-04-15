package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DateSelectionActivity extends AppCompatActivity {
    private TextView venueText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateselection);
        venueText = (TextView) findViewById(R.id.venueText);
        Intent intent = getIntent();
        String venue = intent.getStringExtra("venue");
        venueText.setText(venue);
    }
}