package com.example.bloodbump;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


public class donationFragment extends Fragment {
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private TextView completed_donations_text;
    private TextView blood_quantity_text;
    private TextView check_history_text;
    private Button donate_blood;
    private String completed = null;
    private int [] quantity = null;
    private String [] CharQuantity = null;
    public int looper;

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
        donate_blood = rootView.findViewById(R.id.donate_button);
        getDonations();
        //

        return rootView;
    }

    private void getDonations() {
        userAuth = FirebaseAuth.getInstance();
        String UID = userAuth.getCurrentUser().getUid();
        userDB = FirebaseDatabase.getInstance();
        reference = userDB.getReference("Donation").child(UID);
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){
                    DataSnapshot data = task.getResult();
                    int iteration = (int) data.getChildrenCount();
                    completed = String.valueOf(iteration);
                    String [] ID = new String[iteration];
                    CharQuantity = new String[iteration];
                    quantity = new int[iteration];
                    final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                    for(int i = 0; i <iteration; i++){
                        looper = i;
                        ID[i] = iterator.next().getKey();
                        reference.child(ID[i]).child("Quantity").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if(task.getResult().exists()){
                                    Pattern number = Pattern.compile("[0-9]+");
                                    DataSnapshot data = task.getResult();
                                    CharQuantity[looper] = String.valueOf(data.child("Quantity").getValue());
                                    // CharQuantity is null
                                    Matcher num = number.matcher(CharQuantity[looper]);
                                    while(num.find()){
                                        quantity[looper] = Integer.parseInt(num.group(0));
                                        // which means this one is also null
                                    }
                                }
                            }
                        });
                    }

                }
                int all = IntStream.of(quantity).sum();
                completed_donations_text.setText(completed);
                blood_quantity_text.setText(CharQuantity[0] + " mL");
            }
        });
    }
}