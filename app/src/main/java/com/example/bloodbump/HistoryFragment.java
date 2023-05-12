package com.example.bloodbump;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;


public class HistoryFragment extends Fragment {
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private ArrayList<String> donation_venue, donation_date, donation_time, donation_type, donation_quantity;
    private RecyclerView recycler_view;
    CustomAdapter customAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        donation_venue = new ArrayList();
        donation_date = new ArrayList();
        donation_time = new ArrayList();
        donation_type = new ArrayList();
        donation_quantity = new ArrayList();
        recycler_view = rootView.findViewById(R.id.recycle_view);
        customAdapter = new CustomAdapter(getContext(), donation_venue, donation_date, donation_time, donation_type, donation_quantity);
        recycler_view.setAdapter(customAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
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
                    //

                    //
                    final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                    for(int i = 0; i < iteration; i++){
                        ID[i] = iterator.next().getKey();
                        reference.child(UID).child(ID[i]).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                DataSnapshot data1 = task.getResult();
                                donation_venue.add(data1.child("Medical Establishment").getValue().toString());
                                donation_date.add(data1.child("Donation Date").getValue().toString());
                                donation_time.add(data1.child("Donation Time").getValue().toString());
                                donation_type.add(data1.child("Donation Type").getValue().toString());
                                donation_time.add(data1.child("Quantity").getValue().toString());
                                /*donation_venue.setText(venue);
                                donation_date_textview.setText(date);
                                donation_time_textview.setText(time);
                                donation_type_textview.setText(type);
                                quantity_textview.setText(quantity);*/
                            }
                        });
                    }
                }
            }
        });
    }
}