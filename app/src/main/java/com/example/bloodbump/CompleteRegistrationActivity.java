package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CompleteRegistrationActivity extends AppCompatActivity {
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDatabase;
    private DatabaseReference reference;
    private Spinner gender_spinner;
    private Spinner bloodgroupSpinner;
    private Spinner donorTypeSpinner;
    private DatePickerDialog datePickerDialog;
    private Button datePicker_button;
    private Button continueButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);
        Bundle extars = getIntent().getExtras();
        gender_spinner = (Spinner) findViewById(R.id.sex_spinner);
        bloodgroupSpinner = (Spinner) findViewById(R.id.bloodgroupSpinner);
        datePicker_button = (Button) findViewById(R.id.datePicker_button);
        continueButton = (Button) findViewById(R.id.continue_button);
        donorTypeSpinner = (Spinner) findViewById(R.id.donorTypeSpinner);
        initDatePicker();
        // Adding Sex Options With Array Adapter
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this, R.array.Sex, R.layout.spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(sexAdapter);
        // Adding Blood Group Options With Array Adapter;
        ArrayAdapter<CharSequence> bloodgroupAdapter = ArrayAdapter.createFromResource(this, R.array.bloodgroup, R.layout.spinner_item);
        bloodgroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodgroupSpinner.setAdapter(bloodgroupAdapter);
        // Adding Donor Type Options With Array Adapter
        ArrayAdapter<CharSequence> donorTypeAdapter = ArrayAdapter.createFromResource(this, R.array.donorType,R.layout.spinner_item);
        donorTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donorTypeSpinner.setAdapter(donorTypeAdapter);
        continueButton.setOnClickListener(view -> {
            // Getting Data From Spinner
            String gender = gender_spinner.getSelectedItem().toString();
            String dob = datePicker_button.getText().toString();
            String bloodgroup = bloodgroupSpinner.getSelectedItem().toString();
            String donorType = donorTypeSpinner.getSelectedItem().toString();
            // Calculating Age
            int age = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = dtf.parse(dob);
                    LocalDate birth = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate curDate = LocalDate.now();
                    age = Period.between(birth, curDate).getYears();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
            // Using Hashmap To Write Data on Firebase Database
            HashMap<String, String> User = (HashMap<String, String>) extars.get("list");
            User.put("Gender", gender);
            User.put("Date Of Birth", dob);
            User.put("Age", String.valueOf(age));
            User.put("Donor Type", donorType);
            userAuth = FirebaseAuth.getInstance();
            String UID = userAuth.getCurrentUser().getUid();
            userDatabase = FirebaseDatabase.getInstance();
            reference = userDatabase.getReference("User");
            reference.child(UID).setValue(User).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    HashMap<String, String> blood = new HashMap<>();
                    blood.put("Blood Group", bloodgroup);
                    reference  = userDatabase.getReference("Blood");
                    reference.child(UID).setValue(blood);
                    Toast.makeText(CompleteRegistrationActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CompleteRegistrationActivity.this, picAddActivity.class));
                }else
                    Toast.makeText(CompleteRegistrationActivity.this, "Failed To Register", Toast.LENGTH_SHORT).show();
            });


        });
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
                String DOB = makeDateString(year, month, day);
                datePicker_button.setText(DOB);
        };
        final Calendar mCalendar = Calendar.getInstance();
        final int maxDay = 31;
        final int maxMonth = 12;
        final int maxYear = 2005;
        mCalendar.set(maxYear, maxMonth
                -1, maxDay);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style,dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(mCalendar.getTimeInMillis());
        final int minDay = 01;
        final int minMonth = 01;
        final int minYear = 1900;
        mCalendar.set(minYear, minMonth - 1, minDay);
        datePickerDialog.getDatePicker().setMinDate(mCalendar.getTimeInMillis());
    }

    private String makeDateString(int year, int month, int day) {
        return day + "/" + month + "/" + year;
    }
    private String getMonthFormat(int month) {
            if(month == 0)
                return "JAN";
            if(month == 1)
                return "FEB";
            if (month == 2)
                return "MAR";
            if (month == 3)
                return "APR";
            if (month == 4)
                return "MAY";
            if (month == 5)
                return "JUN";
            if (month == 6)
                return "JUL";
            if (month == 7)
                return "AUG";
            if (month == 8)
                return "SEP";
            if (month == 9)
                return "OCT";
            if (month == 10)
                return "NOV";
            if (month == 11)
                return "DEC";
        return null;
    }
    public void OpenDatePicker(View view) {
        datePickerDialog.show();
    }
}