package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class DateSelectionActivity extends AppCompatActivity {
    private TextView venueText;
    private ImageView dayButton1, dayButton2, dayButton3, dayButton4, dayButton5, dayButton6, dayButton7;
    private TextView day1 ,day2, day3, day4, day5, day6, day7;
    private TextView morning1, morning2, morning3, morning4, morning5, morning6, morning7;
    private TextView evening1, evening2, evening3, evening4, evening5, evening6, evening7;
    private Button bookButton;
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private String RequestDate;
    private  String RequestTime;
    private int max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateselection);
        venueText = (TextView) findViewById(R.id.venueText);
        Intent intent = getIntent();
        String venue = intent.getStringExtra("venue");
        Toast.makeText(this, venue, Toast.LENGTH_SHORT).show();
        venueText.setText(venue);
        bookButton = findViewById(R.id.bookButton);
        bookButton.setOnClickListener(this::Book);
        // Declaring Views
        dayButton1 = findViewById(R.id.dayButton1);
        dayButton2 = findViewById(R.id.dayButton2);
        dayButton3 = findViewById(R.id.dayButton3);
        dayButton4 = findViewById(R.id.dayButton4);
        dayButton5 = findViewById(R.id.dayButton5);
        dayButton6 = findViewById(R.id.dayButton6);
        dayButton7 = findViewById(R.id.dayButton7);
        // Setting Thier OnClickers
        dayButton1.setOnClickListener(this::onClick);
        dayButton2.setOnClickListener(this::onClick);
        dayButton3.setOnClickListener(this::onClick);
        dayButton4.setOnClickListener(this::onClick);
        dayButton5.setOnClickListener(this::onClick);
        dayButton6.setOnClickListener(this::onClick);
        dayButton7.setOnClickListener(this::onClick);
        //

        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);
        day5 = findViewById(R.id.day5);
        day6 = findViewById(R.id.day6);
        day7 = findViewById(R.id.day7);

        morning1 = findViewById(R.id.morning1);
        morning2 = findViewById(R.id.morning2);
        morning3 = findViewById(R.id.morning3);
        morning4 = findViewById(R.id.morning4);
        morning5 = findViewById(R.id.morning5);
        morning6 = findViewById(R.id.morning6);
        morning7 = findViewById(R.id.morning7);

        evening1 = findViewById(R.id.evening1);
        evening2 = findViewById(R.id.evening2);
        evening3 = findViewById(R.id.evening3);
        evening4 = findViewById(R.id.evening4);
        evening5 = findViewById(R.id.evening5);
        evening6 = findViewById(R.id.evening6);
        evening7 = findViewById(R.id.evening7);

        bookButton = findViewById(R.id.bookButton);
        //
        userAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
        reference = userDB.getReference("Schedule");
        reference.child(venue).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(DateSelectionActivity.this, "Getting Data", Toast.LENGTH_SHORT).show();
                        DataSnapshot data = task.getResult();
                        long iter = data.getChildrenCount();
                        int iteration = (int) iter;
                        String[] Days = new String[iteration];
                        String[] time = new String[iteration];
                        String[] morning = new String[iteration];
                        String[] evening  =new String[iteration];
                        final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                        for(int i = 0; i < iteration; i++){
                            Days[i] = iterator.next().getKey();
                            morning[i] = String.valueOf(data.child(Days[i]).child("morning").getValue());
                            evening[i] = String.valueOf(data.child(Days[i]).child("evening").getValue());
                            //max = Integer.parseInt(data.child(Days[i]).child("max").getValue().toString());
                            //time[i] = String.valueOf(data.child(Days[i]).getValue());

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
            }
        });
    }
    public void onClick(View view){
       /* switch(view.getId()){
            case R.id.dayButton1:
                if(timing1.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setClickable(true);
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    dayButton1.setBackground(getDrawable(R.drawable.field_selected));
                    RequestDate = day1.getText().toString().trim();
                    RequestTime = timing1.getText().toString().trim();
                }
                break;
            case R.id.dayButton2:
                if(timing2.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    dayButton2.setBackground(getDrawable(R.drawable.field_selected));
                    RequestDate = day2.getText().toString().trim();
                    RequestTime = timing2.getText().toString().trim();
                }
                break;
            case R.id.dayButton3:
                if(timing3.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    dayButton3.setBackground(getDrawable(R.drawable.field_selected));
                    RequestDate = day3.getText().toString().trim();
                    RequestTime = timing3.getText().toString().trim();
                }
                break;
            case R.id.dayButton4:
                if(timing4.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    dayButton4.setBackground(getDrawable(R.drawable.field_selected));
                    RequestDate = day4.getText().toString().trim();
                    RequestTime = timing4.getText().toString().trim();
                }
                break;
            case R.id.dayButton5:
                if(timing5.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    dayButton5.setBackground(getDrawable(R.drawable.field_selected));
                    RequestDate = day5.getText().toString().trim();
                    RequestTime = timing5.getText().toString().trim();
                }
                break;
            case R.id.dayButton6:
                if(timing6.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    dayButton6.setBackground(getDrawable(R.drawable.field_selected));
                    RequestDate = day6.getText().toString().trim();
                    RequestTime = timing6.getText().toString().trim();
                }
                break;
            case R.id.dayButton7:
                if(timing7.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    dayButton7.setBackground(getDrawable(R.drawable.field_selected));
                    RequestDate = day7.getText().toString().trim();
                    RequestTime = timing7.getText().toString().trim();
                }
                break;
        }*/
    }
    public void Book(View view){
        reference = userDB.getReference("Schedule");
        reference.child(RequestDate).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){
                    DataSnapshot data = task.getResult();
                    int max = Integer.parseInt(data.child("max").getValue().toString());
                    if(max < 1){
                        Toast.makeText(DateSelectionActivity.this, "You Can't Book On This Day", Toast.LENGTH_SHORT).show();
                    }else{
                        String UID = userAuth.getCurrentUser().getUid();
                        reference = userDB.getReference("User");
                        reference.child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if(task.getResult().exists()){
                                    DataSnapshot data = task.getResult();
                                    String first_name = String.valueOf(data.child("first_name").getValue());
                                    String last_name = String.valueOf(data.child("last_name").getValue());
                                    String key = reference.child(UID).push().getKey();
                                    int iter = (int) data.child(key).getChildrenCount();
                                    String[] info = new String[iter];
                                    String[] value = new String[iter];
                                    final Iterator<DataSnapshot> iterator = data.child(key).getChildren().iterator();
                                    for(int i = 0; i< iter; i++){
                                        info[i] = iterator.next().getKey();
                                        value[i] = String.valueOf(data.child(info[i]).getValue());
                                    }
                                    String fullname = first_name + " " + last_name;
                                    HashMap<String, String> requestMap = new HashMap<String, String>();
                                    requestMap.put("firstname", first_name);
                                    requestMap.put("lastname", last_name);
                                    requestMap.put("DonationDate", RequestDate);
                                    requestMap.put("DonationTime", RequestTime);
                                    for(int i = 0; i < info.length; i++){
                                        requestMap.put(info[i], value[i]);
                                    }
                                    reference = userDB.getReference("Request");
                                    reference.child(fullname).setValue(requestMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });

    }
}