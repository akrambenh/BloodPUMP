package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.function.Consumer;

public class AdminScheduleActivity extends AppCompatActivity {
    private TextView dayOne, dayTwo, dayThree, dayFour, dayFive, daySix, daySeven;
    private EditText timing1, timing2, timing3, timing4, timing5, timing6, timing7;
    private FirebaseAuth adminAuth;
    private FirebaseDatabase adminDB;
    private DatabaseReference reference;
    private Button addSchedule_button;
    private TextView addSchedule_text;
    public String[] time = new String[8];
    public int iterTime = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule);
        adminAuth = FirebaseAuth.getInstance();
        adminDB = FirebaseDatabase.getInstance();
        addSchedule_button = (Button) findViewById(R.id.addSchedule_button);
        addSchedule_text = (TextView) findViewById(R.id.addSchedule_text);
        //
        dayOne = (TextView) findViewById(R.id.day1);
        dayTwo = (TextView) findViewById(R.id.day2);
        dayThree = (TextView) findViewById(R.id.day3);
        dayFour = (TextView) findViewById(R.id.day4);
        dayFive = (TextView) findViewById(R.id.day5);
        daySix = (TextView) findViewById(R.id.day6);
        daySeven = (TextView) findViewById(R.id.day7);
        //
        timing1 = (EditText) findViewById(R.id.timing1);
        timing2 = (EditText) findViewById(R.id.timing2);
        timing3 = (EditText) findViewById(R.id.timing3);
        timing4 = (EditText) findViewById(R.id.timing4);
        timing5 = (EditText) findViewById(R.id.timing5);
        timing6 = (EditText) findViewById(R.id.timing6);
        timing7 = (EditText) findViewById(R.id.timing7);
        String UID = adminAuth.getCurrentUser().getUid();
        reference = adminDB.getReference("Admin");
        reference.child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot data = task.getResult();
                        String Name = String.valueOf(data.child("name").getValue());
                        reference = adminDB.getReference("Schedule");
                        reference.child(Name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if(task.isSuccessful()){
                                    if(task.getResult().exists()){
                                        Toast.makeText(AdminScheduleActivity.this, "Schedule Exists", Toast.LENGTH_SHORT).show();
                                        // Reading Data
                                        DataSnapshot data = task.getResult();
                                        long iter = data.getChildrenCount();
                                        int iteration = (int) iter;
                                        String[] Days = new String[iteration];
                                        //String[] Opening = new String[iteration];
                                        String[] time = new String[iteration];
                                        final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                                        for(int i = 0; i < iteration; i++) {
                                            Days[i] = iterator.next().getKey();
                                            time[i] = String.valueOf(data.child(Days[i]).getValue());
                                        }
                                        dayOne.setText(Days[0]);
                                        dayTwo.setText(Days[1]);
                                        dayThree.setText(Days[2]);
                                        dayFour.setText(Days[3]);
                                        dayFive.setText(Days[4]);
                                        daySix.setText(Days[5]);
                                        daySeven.setText(Days[6]);
                                        //
                                        timing1.setText(time[0]);
                                        timing2.setText(time[1]);
                                        timing3.setText(time[2]);
                                        timing4.setText(time[3]);
                                        timing5.setText(time[4]);
                                        timing6.setText(time[5]);
                                        timing7.setText(time[6]);
                                        addSchedule_button.setClickable(true);
                                        addSchedule_button.setText("Save Changes");
                                        addSchedule_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String time1= timing1.getText().toString();
                                                String time2 = timing2.getText().toString();
                                                String time3 = timing3.getText().toString();
                                                String time4 = timing4.getText().toString();
                                                String time5 = timing5.getText().toString();
                                                String time6 = timing6.getText().toString();
                                                String time7 = timing7.getText().toString();
                                                HashMap<String, String> scheduleMap = new HashMap<String, String>();
                                                scheduleMap.put(giveDate(0), time1);
                                                scheduleMap.put(giveDate(1), time2);
                                                scheduleMap.put(giveDate(2), time3);
                                                scheduleMap.put(giveDate(3), time4);
                                                scheduleMap.put(giveDate(4), time5);
                                                scheduleMap.put(giveDate(5), time6);
                                                scheduleMap.put(giveDate(6), time7);
                                                reference = adminDB.getReference("Schedule");
                                                reference.child(Name).setValue(scheduleMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(AdminScheduleActivity.this, "Schedule Changed Successfully", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(AdminScheduleActivity.this, AdminHomeActivity.class));
                                                        }else
                                                            Toast.makeText(AdminScheduleActivity.this, "Failed To Change Schedule", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });
                                    }else {
                                        Toast.makeText(AdminScheduleActivity.this, "Schedule Doesn't Exists \n You Can Add Weekly Schedule", Toast.LENGTH_SHORT).show();
                                        addSchedule_text.setVisibility(View.VISIBLE);
                                        addSchedule_text.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                addSchedule_button.setClickable(true);
                                                dayOne.setText(giveDate(0));
                                                dayTwo.setText(giveDate(1));
                                                dayThree.setText(giveDate(2));
                                                dayFour.setText(giveDate(3));
                                                dayFive.setText(giveDate(4));
                                                daySix.setText(giveDate(5));
                                                daySeven.setText(giveDate(6));

                                            }
                                        });
                                        // Reading Opening Time
                                        addSchedule_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String time1= timing1.getText().toString();
                                                String time2 = timing2.getText().toString();
                                                String time3 = timing3.getText().toString();
                                                String time4 = timing4.getText().toString();
                                                String time5 = timing5.getText().toString();
                                                String time6 = timing6.getText().toString();
                                                String time7 = timing7.getText().toString();
                                                HashMap<String, String> scheduleMap = new HashMap<String, String>();
                                                scheduleMap.put(giveDate(0), time1);
                                                scheduleMap.put(giveDate(1), time2);
                                                scheduleMap.put(giveDate(2), time3);
                                                scheduleMap.put(giveDate(3), time4);
                                                scheduleMap.put(giveDate(4), time5);
                                                scheduleMap.put(giveDate(5), time6);
                                                scheduleMap.put(giveDate(6), time7);
                                                reference = adminDB.getReference("Schedule");
                                                reference.child(Name).setValue(scheduleMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(AdminScheduleActivity.this, "Schedule Added Successfully", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(AdminScheduleActivity.this, AdminHomeActivity.class));
                                                        }else
                                                            Toast.makeText(AdminScheduleActivity.this, "Failed To Add Schedule", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });


                                    }

                                }else{
                                    Toast.makeText(AdminScheduleActivity.this, "Failed To Read Schedule", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(AdminScheduleActivity.this, "Admin Data Doesn't Exist", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AdminScheduleActivity.this, "Failed To Read Admin Info's", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //IF Dates Are Not Available In Database(Not Set Yet By Admin)

    }


    public void backToHome(View view) {
        startActivity(new Intent(AdminScheduleActivity.this, AdminHomeActivity.class));
    }
    public String giveDate(int i) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, i);
        SimpleDateFormat inv = new SimpleDateFormat("d MMM, yyyy : EEE");
        return inv.format(cal.getTime());
    }
}