package com.example.bloodbump;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HealthReportFragment extends Fragment {
    private TextView date_text, bloodgroup_text, diastolic_text, systolic_text, hiv_text, malaria_text;
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_health_report, container, false);
        //
        date_text = rootView.findViewById(R.id.date_text);
        bloodgroup_text = rootView.findViewById(R.id.bloodgroup_text);
        diastolic_text = rootView.findViewById(R.id.diastolic_text);
        systolic_text=  rootView.findViewById(R.id.systolic_text);
        hiv_text = rootView.findViewById(R.id.hiv_text);
        malaria_text = rootView.findViewById(R.id.malaria_text);

        userAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
        getHealthReport();
        //
        return rootView;
    }

    private void getHealthReport() {
        String UID = userAuth.getCurrentUser().getUid();
        DatabaseReference reference = userDB.getReference("HealthReport");
        reference.child(UID).get().addOnCompleteListener(task -> {
            if(task.getResult().exists()){
                DataSnapshot data = task.getResult();
                //
                String date = String.valueOf(data.child("Date").getValue());
                String bloodgroup = String.valueOf(data.child("bloodGroup").getValue());
                String diastolic = String.valueOf(data.child("diastolic").getValue());
                String systolic = String.valueOf(data.child("systolic").getValue());
                boolean hiv = Boolean.parseBoolean(data.child("HIV").getValue().toString());
                boolean malaria = Boolean.parseBoolean(data.child("malaria").getValue().toString());
                String hivText = null;
                String malariaText = null;
                // Turning boolean into yes no instead of true false
                if(hiv){
                    hivText = "Yes";
                } if(!hiv) {
                    hivText = "No";
                } if(malaria) {
                    malariaText = "Yes";
                } if(!malaria){
                    malariaText = "No";
                }
                //
                date_text.setText(date);
                bloodgroup_text.setText(bloodgroup);
                diastolic_text.setText(diastolic);
                systolic_text.setText(systolic);
                hiv_text.setText(hivText);
                malaria_text.setText(malariaText);
            }
        });
    }
}