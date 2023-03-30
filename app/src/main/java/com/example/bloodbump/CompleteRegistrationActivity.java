package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Calendar;

public class CompleteRegistrationActivity extends AppCompatActivity {
    private Spinner sex_spinner;
    private DatePickerDialog datePickerDialog;
    private Button datePicker_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);
        sex_spinner = (Spinner) findViewById(R.id.sex_spinner);
        datePicker_button = (Button) findViewById(R.id.datePicker_button);
        initDatePicker();
        // Adding Sex Options With Array Adapter
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.Sex, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_spinner.setAdapter(adapter);
        //String dob to pass it to database

    }
    // Adding Date Picker Dialog And It's Functions
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //month = month + 1;
                String DOB = makeDateString(year, month, day);
                datePicker_button.setText(DOB);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int year, int month, int day) {
        return day + "/" + getMonthFormat(month) + "/" + year;
    }

    private String getMonthFormat(int month) {
            if(month == 1)
                return "JAN";
            if(month == 2)
                return "FEB";
            if (month == 3)
                return "MAR";
            if (month == 4)
                return "APR";
            if (month == 5)
                return "MAY";
            if (month == 6)
                return "JUN";
            if (month == 7)
                return "JUL";
            if (month == 8)
                return "AUG";
            if (month == 9)
                return "SEP";
            if (month == 10)
                return "OCT";
            if (month == 11)
                return "NOV";
            if (month == 12)
                return "DEC";
        return null;
    }


    public void OpenDatePicker(View view) {
        datePickerDialog.show();
    }
}