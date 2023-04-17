package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.jar.Attributes;

public class AdminHomeActivity extends AppCompatActivity {
    private FirebaseAuth adminAuth;
    private TextView first_day;
    private FirebaseDatabase adminDB;
    private DatabaseReference admin_Ref;
    private TextView hospitalText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        adminAuth = FirebaseAuth.getInstance();
        adminDB = FirebaseDatabase.getInstance();
        String EMAIL = adminAuth.getCurrentUser().getEmail();
        hospitalText = (TextView) findViewById(R.id.hospitalText);
        // Getting Hospital Name
        String UID = adminAuth.getCurrentUser().getUid();
        admin_Ref = adminDB.getReference("Admin");
        admin_Ref.child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot data = task.getResult();
                    String Name = String.valueOf(data.child("name").getValue());
                    hospitalText.setText(Name);
                }
            }
        });
    }

    public void JumpSchuduleFrag(View view) {
        startActivity(new Intent(AdminHomeActivity.this, AdminScheduleActivity.class));
    }
}