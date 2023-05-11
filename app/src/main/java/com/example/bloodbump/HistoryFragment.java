package com.example.bloodbump;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;


public class HistoryFragment extends Fragment {
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private TextView donation_venue, donation_date_textview, donation_time_textview, donation_type_textview, quantity_textview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        donation_venue = rootView.findViewById(R.id.donation_venue);
        donation_time_textview = rootView.findViewById(R.id.donation_time_textview);
        donation_date_textview = rootView.findViewById(R.id.donation_date_textview);
        donation_type_textview = rootView.findViewById(R.id.donation_type_textview);
        quantity_textview = rootView.findViewById(R.id.quantity_textview);
        getDonations();
        return rootView;
    }
    private void getDonations() {
        userAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
        String UID = userAuth.getCurrentUser().getUid();
        reference = userDB.getReference("Donation");
        reference.child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){
                    DataSnapshot data = task.getResult();
                    int iteration = (int) data.getChildrenCount();
                    String[] ID = new String[iteration];
                    final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                    for(int i = 0; i < iteration; i++){
                        ID[i] = iterator.next().getKey();
                        reference.child(UID).child(ID[i]).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot data1 = task.getResult();
                                String venue = data1.child("Medical Establishment").getValue().toString();
                                String date = data1.child("Donation Date").getValue().toString();
                                String time = data1.child("Donation Time").getValue().toString();
                                String type = data1.child("Donation Type").getValue().toString();
                                String quantity = data1.child("Quantity").getValue().toString();
                                donation_venue.setText(venue);
                                donation_date_textview.setText(date);
                                donation_time_textview.setText(time);
                                donation_type_textview.setText(type);
                                quantity_textview.setText(quantity);
                            }
                        });
                    }
                }
            }
        });
    }
}