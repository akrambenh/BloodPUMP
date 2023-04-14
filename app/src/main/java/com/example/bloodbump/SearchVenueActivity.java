package com.example.bloodbump;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;

public class SearchVenueActivity extends AppCompatActivity {
    private double currentLat;
    private double currentLong;
    private FusedLocationProviderClient client;
    private SupportMapFragment supportMapFragment;
    private EditText searchEditText;
    private RadioGroup venueGroup;
    private RadioButton hospitalRadio;
    private RadioButton centreRadio;
    private SeekBar RadiusSeekBar;
    private TextView radiusText;
    public static int RadiusValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_venue);
        searchEditText = findViewById(R.id.searchEditText);
        venueGroup = findViewById(R.id.venueGroup);
        hospitalRadio = findViewById(R.id.hospitalRadio);
        centreRadio = findViewById(R.id.centreRadio);
        RadiusSeekBar = findViewById(R.id.RadiusSeekBar);
        radiusText = findViewById(R.id.radiusText);
        RadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    radiusText.setText(String.valueOf(i));
                    RadiusValue = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    public void backToAppointment(View view) {
        startActivity(new Intent(SearchVenueActivity.this, AppointmentActivity.class));
    }

    public void SearchNearby(View view) {
        Bundle bundle = new Bundle();
        int ChosenID = venueGroup.getCheckedRadioButtonId();
        RadioButton ChosenButton = findViewById(ChosenID);
        String venue = ChosenButton.getText().toString();
        bundle.putString("venue", venue);
        String radius  = radiusText.getText().toString();
        bundle.putString("radius", radius);
        PlacesFragemnt fragVenue = new PlacesFragemnt();
        fragVenue.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.searchResult_content, fragVenue).commit();
    }
}