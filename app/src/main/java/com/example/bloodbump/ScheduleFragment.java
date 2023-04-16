package com.example.bloodbump;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ScheduleFragment extends Fragment {
    private TextView first_day;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MMMM/yyyy");
        simpleformat.format(cal.getTime());
        //first_day.setText(simpleformat.toString());
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
}