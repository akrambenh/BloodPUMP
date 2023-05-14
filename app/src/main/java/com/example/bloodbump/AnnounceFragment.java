package com.example.bloodbump;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Iterator;

public class AnnounceFragment extends Fragment {
    private ArrayList<String> announce_name, announce_location, announce_date, announce_time;
    private String UID;
    String name, location, date, time;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_announce, container, false);
        announce_name = new ArrayList<>();
        announce_location = new ArrayList<>();
        announce_date = new ArrayList<>();
        announce_time = new ArrayList<>();
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        UID= userAuth.getCurrentUser().getUid();
        FirebaseDatabase userDB = FirebaseDatabase.getInstance();
        DatabaseReference reference = userDB.getReference("Announce");
        reference.get().addOnCompleteListener(task -> {
            if(task.getResult().exists()){
                AnnounceAdapter announceAdapter;
                RecyclerView announce_view;
                announce_view = view.findViewById(R.id.announce_view);
                DataSnapshot data = task.getResult();
                int iteration = (int) data.getChildrenCount();
                String[] ID = new String[iteration];
                final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                for(int i = 0; i < iteration; i++){
                    ID[i] = iterator.next().getKey();
                    name = data.child(ID[i]).child("Announce Name").getValue().toString();
                    location = data.child(ID[i]).child("Announce Location").getValue().toString();
                    date = data.child(ID[i]).child("Announce Date").getValue().toString();
                    time = data.child(ID[i]).child("Announce Time").getValue().toString();
                    announce_name.add(name);
                    announce_location.add(location);
                    announce_date.add(date);
                    announce_time.add(time);
                }
                announceAdapter = new AnnounceAdapter(getContext(), announce_location, announce_name, announce_date, announce_time, UID);
                announce_view.setAdapter(announceAdapter);
                announce_view.setLayoutManager(new LinearLayoutManager(getContext()));
            }else{
                TextView no_announce_textview = (TextView) view.findViewById(R.id.no_announce_textview);
                no_announce_textview.setVisibility(View.VISIBLE);
            }
        });
    return view;
    }
}