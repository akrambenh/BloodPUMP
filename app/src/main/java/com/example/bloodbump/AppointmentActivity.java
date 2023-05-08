package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AppointmentActivity extends AppCompatActivity {
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private TextView appointment_date, appointment_time, appointment_type, appointment_venue;
    private TextView date_textview, time_textview, type_textview;
    private ImageView ImageView4, ImageView5, ImageView6, ImageView9;
    private TextView no_appointment_text;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap<String, String> bookingDetails;
        setContentView(R.layout.activity_appointment);
        Bundle bundle = this.getIntent().getExtras();
        appointment_venue = findViewById(R.id.appointment_venue);
        appointment_date = findViewById(R.id.appointment_date);
        appointment_time = findViewById(R.id.appointment_time);
        appointment_type = findViewById(R.id.appointment_type);
        ImageView9 = findViewById(R.id.imageView9);
        ImageView4 = findViewById(R.id.imageView4);
        ImageView5 = findViewById(R.id.imageView5);
        ImageView6 = findViewById(R.id.imageView6);
        date_textview = findViewById(R.id.date_textview);
        time_textview = findViewById(R.id.time_textview);
        type_textview = findViewById(R.id.type_textview);
        no_appointment_text = findViewById(R.id.no_appointment_text);
        // test if bundle is null it means this activity was accessed through clicking on appointment navigator
        //if its not null it means this was accessed through notification
        if(bundle == null) {
            // get appointment from Database
            userAuth = FirebaseAuth.getInstance();
            userDB = FirebaseDatabase.getInstance();
            String UID = userAuth.getCurrentUser().getUid();
            reference = userDB.getReference("Appointment");
            reference.child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.getResult().exists()) {
                        DataSnapshot data = task.getResult();
                        String date = String.valueOf(data.child("Donation Date").getValue());
                        String time = String.valueOf(data.child("Donation Time").getValue());
                        String venue = String.valueOf(data.child("Medical Establishment").getValue());
                        String type = String.valueOf(data.child("type").getValue());

                        appointment_venue.setText(venue);
                        appointment_date.setText(date);
                        appointment_time.setText(time);
                        appointment_type.setText(type);
                    }else {
                        appointment_date.setVisibility(View.GONE);
                        appointment_venue.setVisibility(View.GONE);
                        appointment_time.setVisibility(View.GONE);
                        appointment_type.setVisibility(View.GONE);
                        ImageView9.setVisibility(View.GONE);
                        ImageView4.setVisibility(View.GONE);
                        ImageView5.setVisibility(View.GONE);
                        ImageView6.setVisibility(View.GONE);
                        date_textview.setVisibility(View.GONE);
                        time_textview.setVisibility(View.GONE);
                        type_textview.setVisibility(View.GONE);
                        no_appointment_text.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        else {
            appointment_venue.setText(bundle.getString("venue"));
            appointment_date.setText(bundle.getString("date"));
            appointment_time.setText(bundle.getString("time"));
            appointment_type.setText(bundle.getString("type"));
        }
    }

    public void backToHome(View view) {
            startActivity(new Intent(AppointmentActivity.this, HomeActivity.class));
    }
}