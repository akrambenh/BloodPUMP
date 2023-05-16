package com.example.bloodpump;

import android.annotation.SuppressLint;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class donationFragment extends Fragment {
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private TextView completed_donations_text;
    private TextView blood_quantity_text;
    private TextView check_history_text;
    private String completed;
    public int sum = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_donation, container, false);
        completed_donations_text = rootView.findViewById(R.id.completed_donations_text);
        blood_quantity_text = rootView.findViewById(R.id.blood_quantity_text);
        check_history_text = rootView.findViewById(R.id.check_history_text);
        getDonations();
        //
        return rootView;
    }

    private void getDonations() {
        userAuth = FirebaseAuth.getInstance();
        String UID = userAuth.getCurrentUser().getUid();
        userDB = FirebaseDatabase.getInstance();
        reference = userDB.getReference("Donation");

        reference.child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){
                    DataSnapshot data = task.getResult();
                    int iteration = (int) data.getChildrenCount();
                    completed = String.valueOf(iteration);
                    String [] ID = new String[iteration];
                    final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                    for(int i = 0; i <iteration; i++){
                        ID[i] = iterator.next().getKey();
                        reference.child(UID).child(ID[i]).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @SuppressLint("ResourceType")
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                Pattern number = Pattern.compile("[0-9]+");
                                DataSnapshot data1 = task.getResult();
                                String value = String.valueOf(data1.child("Quantity").getValue());
                                // CharQuantity is null
                                Matcher num = number.matcher(value);
                                while(num.find()){
                                    sum = sum + Integer.parseInt(num.group());
                                    blood_quantity_text.setText(String.valueOf(sum + " mL"));
                                }
                            }
                        });
                    }
                    completed_donations_text.setText(completed);
                }
            }
        });
    }

}