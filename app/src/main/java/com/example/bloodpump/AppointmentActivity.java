package com.example.bloodpump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;

public class AppointmentActivity extends AppCompatActivity {
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private ArrayList<String> appointment_date, appointment_time, appointment_type, appointment_venue;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        appointment_venue = new ArrayList<>();
        appointment_date = new ArrayList<>();
        appointment_time = new ArrayList<>();
        appointment_type = new ArrayList<>();
        userAuth = FirebaseAuth.getInstance();
        String UID = userAuth.getCurrentUser().getUid();
        userDB = FirebaseDatabase.getInstance();
        reference = userDB.getReference("Appointment");
        reference.child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){
                    DataSnapshot data = task.getResult();
                    AppointmentAdapter appointmentAdapter;
                    RecyclerView appointment_view;
                    appointment_view = findViewById(R.id.appointment_view);
                    int iteration = (int) data.getChildrenCount();
                    String[] ID = new String[iteration];
                    final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                    for(int i = 0; i < iteration; i++){
                        ID[i] = iterator.next().getKey();
                        String venue = data.child(ID[i]).child("Medical Establishment").getValue().toString();
                        String date = data.child(ID[i]).child("Donation Date").getValue().toString();
                        String time = data.child(ID[i]).child("Donation Time").getValue().toString();
                        String type = data.child(ID[i]).child("Donation Type").getValue().toString();
                        appointment_venue.add(venue);
                        appointment_date.add(date);
                        appointment_time.add(time);
                        appointment_type.add(type);
                    }
                    appointmentAdapter = new AppointmentAdapter(getApplicationContext(), appointment_venue, appointment_date, appointment_time, appointment_type);
                    appointment_view.setAdapter(appointmentAdapter);
                    appointment_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }else {
                    TextView no_appointment_text = (TextView) findViewById(R.id.no_appointment_text);
                    no_appointment_text.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void backToHome(View view) {
            startActivity(new Intent(AppointmentActivity.this, HomeActivity.class));
    }
}