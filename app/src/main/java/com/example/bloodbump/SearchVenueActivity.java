package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.SupportMapFragment;

public class SearchVenueActivity extends AppCompatActivity {
    private double currentLat;
    private double currentLong;
    private FusedLocationProviderClient client;
    private SupportMapFragment supportMapFragment;
    private RadioGroup venueGroup;
    private RadioButton hospitalRadio;
    private RadioButton centreRadio;
    private SeekBar RadiusSeekBar;
    private TextView radiusText;
    public static int RadiusValue;
    private String predecessor_activity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        predecessor_activity = intent.getStringExtra("predecessor_activity");

        setContentView(R.layout.activity_search_venue);
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

    public void onBackButton(View view) {
        if(predecessor_activity.equals("AppointmentActivity")){
            startActivity(new Intent(SearchVenueActivity.this, SelectVenueActivity.class));
        }else if(predecessor_activity.equals("HomeActivity")){
            startActivity(new Intent(SearchVenueActivity.this, HomeActivity.class));
        }
    }
}