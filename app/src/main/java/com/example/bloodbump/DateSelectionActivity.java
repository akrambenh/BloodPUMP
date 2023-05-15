package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class DateSelectionActivity extends AppCompatActivity {
    private TextView venueText;
    private TextView day1 ,day2, day3, day4, day5, day6, day7;
    private TextView morning1, morning2, morning3, morning4, morning5, morning6, morning7;
    private TextView evening1, evening2, evening3, evening4, evening5, evening6, evening7;
    private TextView wholeBloodText, plateletText;
    private Button bookButton, proceedButton;
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private String RequestDate;
    private  String RequestTime;
    private String bloodgroup = null;
    private String venue = null;
    String predecessor_activity = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateselection);
        venueText = findViewById(R.id.venueText);
        // Getting Predecessor Activity
        Intent intent = getIntent();
        venue = intent.getStringExtra("venue");
        predecessor_activity = intent.getStringExtra("predecessor_activity");
        venueText.setText(venue);
        //
        bookButton = findViewById(R.id.bookButton);
        bookButton.setOnClickListener(this::Book);
        // Declaring Views
        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);
        day5 = findViewById(R.id.day5);
        day6 = findViewById(R.id.day6);
        day7 = findViewById(R.id.day7);
        //
        morning1 = findViewById(R.id.morning1);
        morning2 = findViewById(R.id.morning2);
        morning3 = findViewById(R.id.morning3);
        morning4 = findViewById(R.id.morning4);
        morning5 = findViewById(R.id.morning5);
        morning6 = findViewById(R.id.morning6);
        morning7 = findViewById(R.id.morning7);
        // Creating Their OnClickers
        morning1.setOnClickListener(this::onClick);
        morning2.setOnClickListener(this::onClick);
        morning3.setOnClickListener(this::onClick);
        morning4.setOnClickListener(this::onClick);
        morning5.setOnClickListener(this::onClick);
        morning6.setOnClickListener(this::onClick);
        morning7.setOnClickListener(this::onClick);
        //
        evening1 = findViewById(R.id.evening1);
        evening2 = findViewById(R.id.evening2);
        evening3 = findViewById(R.id.evening3);
        evening4 = findViewById(R.id.evening4);
        evening5 = findViewById(R.id.evening5);
        evening6 = findViewById(R.id.evening6);
        evening7 = findViewById(R.id.evening7);
        // Setting Their OnClickers
        evening1.setOnClickListener(this::onClick);
        evening2.setOnClickListener(this::onClick);
        evening3.setOnClickListener(this::onClick);
        evening4.setOnClickListener(this::onClick);
        evening5.setOnClickListener(this::onClick);
        evening6.setOnClickListener(this::onClick);
        evening7.setOnClickListener(this::onClick);
        // Getting Schedule From Database
        userAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
        reference = userDB.getReference("Schedule");
        reference.child(venue).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                if(task.getResult().exists()){
                    DataSnapshot data = task.getResult();
                    int iteration = (int) data.getChildrenCount();
                    String[] Days = new String[iteration];
                    String[] morning = new String[iteration];
                    String[] evening  = new String[iteration];
                    final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                    for(int i = 0; i < iteration; i++){
                        Days[i] = iterator.next().getKey();
                        morning[i] = String.valueOf(data.child(Days[i]).child("morning").getValue());
                        evening[i] = String.valueOf(data.child(Days[i]).child("evening").getValue());
                    }
                    day1.setText(Days[0]);
                    day2.setText(Days[1]);
                    day3.setText(Days[2]);
                    day4.setText(Days[3]);
                    day5.setText(Days[4]);
                    day6.setText(Days[5]);
                    day7.setText(Days[6]);
                    //
                    morning1.setText(morning[0]);
                    morning2.setText(morning[1]);
                    morning3.setText(morning[2]);
                    morning4.setText(morning[3]);
                    morning5.setText(morning[4]);
                    morning6.setText(morning[5]);
                    morning7.setText(morning[6]);
                    //
                    evening1.setText(evening[0]);
                    evening2.setText(evening[1]);
                    evening3.setText(evening[2]);
                    evening4.setText(evening[3]);
                    evening5.setText(evening[4]);
                    evening6.setText(evening[5]);
                    evening7.setText(evening[6]);
                }
            }else
                Toast.makeText(DateSelectionActivity.this, venue + " Has No Schedule", Toast.LENGTH_SHORT).show();
        });
    }
    @SuppressLint({"UseCompatLoadingForDrawables", "NonConstantResourceId"})
    public void onClick(View view){
       switch(view.getId()){
           case R.id.morning1:
                if(morning1.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setClickable(true);
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning1.setBackground(getDrawable(R.drawable.field_selected));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day1.getText().toString();
                    RequestTime = morning1.getText().toString();
                }
                break;
           case R.id.morning2:
                if(morning2.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning2.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day2.getText().toString().trim();
                    RequestTime = morning2.getText().toString().trim();
                }
                break;
           case R.id.morning3:
                if(morning3.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning3.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day3.getText().toString().trim();
                    RequestTime = morning3.getText().toString().trim();
                }
                break;
           case R.id.morning4:
                if(morning4.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning4.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day4.getText().toString().trim();
                    RequestTime = morning4.getText().toString().trim();
                }
                break;
           case R.id.morning5:
                if(morning5.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning5.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day5.getText().toString().trim();
                    RequestTime = morning5.getText().toString().trim();
                }
                break;
           case R.id.morning6:
                if(morning6.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning6.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day6.getText().toString().trim();
                    RequestTime = morning6.getText().toString().trim();
                }
                break;
           case R.id.morning7:
                if(morning7.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning7.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day7.getText().toString().trim();
                    RequestTime = morning7.getText().toString().trim();
                }
                break;
           case R.id.evening1:
               if(evening1.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setClickable(true);
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening1.setBackground(getDrawable(R.drawable.field_selected));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day1.getText().toString().trim();
                   RequestTime = morning1.getText().toString().trim();
               }
               break;
           case R.id.evening2:
               if(evening2.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening2.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day2.getText().toString().trim();
                   RequestTime = evening2.getText().toString().trim();
               }
               break;
           case R.id.evening3:
               if(evening3.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening3.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day3.getText().toString().trim();
                   RequestTime = evening3.getText().toString().trim();
               }
               break;
           case R.id.evening4:
               if(evening4.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening4.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day4.getText().toString().trim();
                   RequestTime = evening4.getText().toString().trim();
               }
               break;
           case R.id.evening5:
               if(evening5.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening5.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day5.getText().toString().trim();
                   RequestTime = evening5.getText().toString().trim();
               }
               break;
           case R.id.evening6:
               if(evening6.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening6.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day6.getText().toString().trim();
                   RequestTime = evening6.getText().toString().trim();
               }
               break;
           case R.id.evening7:
               if(evening7.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening7.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day7.getText().toString().trim();
                   RequestTime = evening7.getText().toString().trim();
               }
               break;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void Book(View view) {
        Dialog dialog = new Dialog(DateSelectionActivity.this);
        dialog.setContentView(R.layout.dialog1_layout);
        dialog.getWindow();
        wholeBloodText = dialog.findViewById(R.id.wholeBloodText);
        plateletText = dialog.findViewById(R.id.plateletText);
        proceedButton = dialog.findViewById(R.id.proceedButton);
        dialog.show();
        HashMap<String, String> requestMap = new HashMap<>();
        wholeBloodText.setOnClickListener(v -> {
            wholeBloodText.setBackground(getResources().getDrawable(R.drawable.field_selected));
            wholeBloodText.setTextColor(getResources().getColor(R.color.white));
            plateletText.setBackground(getResources().getDrawable(R.drawable.field));
            plateletText.setTextColor(getResources().getColor(R.color.my_red));
            proceedButton.setClickable(true);
            requestMap.put("Donation Type", "Whole Blood");
        });
        plateletText.setOnClickListener(v -> {
            plateletText.setBackground(getResources().getDrawable(R.drawable.field_selected));
            plateletText.setTextColor(getResources().getColor(R.color.white));
            wholeBloodText.setBackground(getResources().getDrawable(R.drawable.field));
            wholeBloodText.setTextColor(getResources().getColor(R.color.my_red));
            proceedButton.setClickable(true);
            requestMap.put("Donation Type", "Platelet");
        });
        proceedButton.setOnClickListener(v -> {
                reference = userDB.getReference("Schedule");
                reference.child(venueText.getText().toString()).child(RequestDate).get().addOnCompleteListener(task -> {
                    if (task.getResult().exists()) {
                        DataSnapshot data = task.getResult();
                        int max = Integer.parseInt(data.child("max").getValue().toString());
                        if (max < 1) {
                            Toast.makeText(DateSelectionActivity.this, "You Can't Book On This Day", Toast.LENGTH_SHORT).show();
                        } else {
                            String UID = userAuth.getCurrentUser().getUid();
                            reference = userDB.getReference("User");
                            reference.child(UID).get().addOnCompleteListener(task13 -> {
                                if (task13.getResult().exists()) {
                                    DataSnapshot data1 = task13.getResult();
                                    //
                                    String first_name = String.valueOf(data1.child("first_name").getValue());
                                    String last_name = String.valueOf(data1.child("last_name").getValue());
                                    String dob = String.valueOf(data1.child("Date Of Birth").getValue());
                                    String donor_type = String.valueOf(data1.child("Donor Type").getValue());
                                    String gender = String.valueOf(data1.child("Gender").getValue());
                                    //
                                    reference = userDB.getReference("Blood");
                                    reference.child(UID).get().addOnCompleteListener(task12 -> {
                                        if (task12.getResult().exists()) {
                                            DataSnapshot item = task12.getResult();
                                            bloodgroup = String.valueOf(item.child("Blood Group").getValue());
                                            String fullname = first_name + " " + last_name;
                                            // Putting Data Into HashMap to be written in database
                                            requestMap.put("Fullname", fullname);
                                            requestMap.put("Medical Establishment", venue);
                                            requestMap.put("Blood Group", bloodgroup);
                                            requestMap.put("Date Of Birth", dob);
                                            requestMap.put("Donor Type", donor_type);
                                            requestMap.put("Gender", gender);
                                            requestMap.put("Donation Date", RequestDate);
                                            requestMap.put("Donation Time", RequestTime);
                                            requestMap.put("Status", "Pending");
                                            checkHealthCondition(UID ,requestMap);
                                        }
                                    });

                                }
                            });
                        }
                    } else
                        Toast.makeText(DateSelectionActivity.this, "Date Doesn't exist", Toast.LENGTH_SHORT).show();
                });
        });
    }
    private void checkHealthCondition(String uid, HashMap<String, String> requestMap) {
        reference = userDB.getReference("HealthReport");
        HashMap<String, Boolean> illness = new HashMap<>();
        reference.child(uid).get().addOnCompleteListener(task -> {
            if (task.getResult().exists()) {
                DataSnapshot data = task.getResult();
                String date = String.valueOf(data.child("Date").getValue());
                //checking if health report is older than 15 days
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dtformat = new SimpleDateFormat("dd/MM/yyyy");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    try {
                        Calendar cal = Calendar.getInstance();
                        String today = dtformat.format(cal.getTime());
                        cal.setTime(dtformat.parse(date));
                        cal.add(Calendar.DAY_OF_MONTH, 15);
                        String afterFifteen = dtformat.format(cal.getTime());
                        // Parsing this date strings into Date objects
                        Date todayDate = dtformat.parse(today);
                        Date end = dtformat.parse(afterFifteen);
                        if(end.compareTo(todayDate) >= 0){
                            Toast.makeText(DateSelectionActivity.this, "Date Is Valid", Toast.LENGTH_SHORT).show();
                        }else if(end.compareTo(todayDate) < 0){
                            Toast.makeText(DateSelectionActivity.this, "Date Is Invalid", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                boolean hiv = Boolean.parseBoolean(data.child("HIV").getValue().toString());
                boolean malaria = Boolean.parseBoolean(data.child("malaria").getValue().toString());
                illness.put("HIV", hiv);
                illness.put("Malaria", malaria);
                // Testing
                if(illness.get("HIV")){ // try using text View instead of Toast
                    Toast.makeText(DateSelectionActivity.this, "Can't Book The Donation \n Due To Having HIV", Toast.LENGTH_SHORT).show();
                }else if(illness.get("Malaria")){
                    Toast.makeText(DateSelectionActivity.this, "Can't Book The Donation \n Due To Having Malaria", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference reference1 = userDB.getReference("Request");
                    reference1.child(uid).setValue(requestMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(DateSelectionActivity.this, "Request Has Been Sent", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        }

    public void backToHome(View view) {
        if(predecessor_activity.equals("SelectVenueActivity")){
            startActivity(new Intent(DateSelectionActivity.this, SelectVenueActivity.class));
        }else if(predecessor_activity.equals("SearchVenueActivity")){
            startActivity(new Intent(DateSelectionActivity.this, SearchVenueActivity.class));
        }
    }
}