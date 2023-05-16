package com.example.bloodpump;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Iterator;

public class HistoryFragment extends Fragment {
    private static final int VERTICAL_ITEM_SPACE = 48;
    private  ArrayList<String> donation_venue, donation_date, donation_time, donation_type, donation_quantity;
    private  String venue, date, time, type, quantity;
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
        //
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseDatabase userDB = FirebaseDatabase.getInstance();
        String UID = userAuth.getCurrentUser().getUid();
        DatabaseReference reference = userDB.getReference("Donation");
        reference.child(UID).get().addOnCompleteListener(task -> {
            if(task.getResult().exists()) {
                CustomAdapter customAdapter;
                RecyclerView recycler_view;
                recycler_view = rootView.findViewById(R.id.recycle_view);
                DataSnapshot data = task.getResult();
                int iteration = (int) data.getChildrenCount();
                String[] ID = new String[iteration];
                final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                for (int i = 0; i < iteration; i++) {
                    ID[i] = iterator.next().getKey();
                    venue = data.child(ID[i]).child("Medical Establishment").getValue().toString();
                    date = data.child(ID[i]).child("Donation Date").getValue().toString();
                    time = data.child(ID[i]).child("Donation Time").getValue().toString();
                    type = data.child(ID[i]).child("Donation Type").getValue().toString();
                    quantity = data.child(ID[i]).child("Quantity").getValue().toString();
                    donation_venue.add(venue);
                    donation_date.add(date);
                    donation_time.add(time);
                    donation_type.add(type);
                    donation_quantity.add(quantity);
                }
                customAdapter = new CustomAdapter(getContext(), donation_venue, donation_date, donation_time, donation_type, donation_quantity);
                recycler_view.setAdapter(customAdapter);
                recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        return rootView;
    }
}