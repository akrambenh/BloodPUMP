package com.example.bloodbump;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AnnounceAdapter extends RecyclerView.Adapter<AnnounceAdapter.MyViewHolder> {
    private Context context;
    private ArrayList announce_location,announce_name, announce_date, announce_time;
    private String UID;
    private String first_name, last_name, dob, gender;
    AnnounceAdapter(Context context, ArrayList announce_location,ArrayList announce_name, ArrayList announce_date, ArrayList announce_time, String UID){
        this.context = context;
        this.announce_location = announce_location;
        this.announce_name = announce_name;
        this.announce_date = announce_date;
        this.announce_time = announce_time;
        this.UID = UID;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inflate_announce_row, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.announce_name_textview.setText(String.valueOf(announce_name.get(position)));
        holder.announce_location_textview.setText(String.valueOf(announce_location.get(position)));
        holder.announce_date_textview.setText(String.valueOf(announce_date.get(position)));
        holder.announce_time_textview.setText(String.valueOf(announce_time.get(position)));
        holder.announce_book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase userDB = FirebaseDatabase.getInstance();
                DatabaseReference reference;
                // get other user data like name ...
                reference = userDB.getReference("User");
                reference.child(UID).get().addOnCompleteListener(task -> {
                    HashMap<String, String> announceRequest = new HashMap<>();
                    DataSnapshot dataSnapshot = task.getResult();
                    first_name = dataSnapshot.child("first_name").getValue().toString();
                    last_name = dataSnapshot.child("last_name").getValue().toString();
                    dob = dataSnapshot.child("Date Of Birth").getValue().toString();
                    gender = dataSnapshot.child("Gender").getValue().toString();
                    announceRequest.put("First Name", first_name);
                    announceRequest.put("Last Name", last_name);
                    announceRequest.put("Date Of Birth", dob);
                    announceRequest.put("Gender", gender);
                    // Setting Announce Request
                    DatabaseReference reference1 = userDB.getReference("Request");
                    String name = String.valueOf(announce_name.get(position));
                    String date = String.valueOf(announce_date.get(position));
                    String time = String.valueOf(announce_time.get(position));
                    announceRequest.put("Campaign Name", name);
                    announceRequest.put("Request Date", date);
                    announceRequest.put("Request Time", time);
                    checkHealthCondition(UID, announceRequest);
                });

            }
        });
    }
    private void checkHealthCondition(String uid ,HashMap<String, String> announceRequest) {
        FirebaseDatabase userDB = FirebaseDatabase.getInstance();
        DatabaseReference reference;
        reference = userDB.getReference("HealthReport");
        HashMap<String, Boolean> illness = new HashMap<>();
        reference.child(uid).get().addOnCompleteListener(task -> {
            if (task.getResult().exists()) {
                DataSnapshot data = task.getResult();
                String date = String.valueOf(data.child("Date").getValue());
                //checking if health report is older than 15 days
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dtformat = new SimpleDateFormat("dd/MM/yyyy");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    try {
                        Calendar cal = Calendar.getInstance();
                        String today = dtformat.format(cal.getTime());
                        cal.setTime(dtformat.parse(date));
                        cal.add(Calendar.DAY_OF_MONTH, 15);
                        String afterFifteen = dtformat.format(cal.getTime());
                        // Parsing this date strings into Date objects
                        Date todayDate = dtformat.parse(today);
                        Date end = dtformat.parse(afterFifteen);
                        if(end.compareTo(todayDate) < 0){
                            Toast.makeText(context, "Your Health Report Is older Than 15 Days\n" +
                                    "Recheck Will Be Performed", Toast.LENGTH_LONG).show();
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                boolean hiv = Boolean.parseBoolean(data.child("HIV").getValue().toString());
                boolean malaria = Boolean.parseBoolean(data.child("malaria").getValue().toString());
                illness.put("HIV", hiv);
                illness.put("Malaria", malaria);
                // Testing
                if(illness.get("HIV")){ // try using text View instead of Toast
                    Toast.makeText(context, "Can't Book The Donation \n  Due To Having HIV", Toast.LENGTH_SHORT).show();
                }else if(illness.get("Malaria")){
                    Toast.makeText(context, "Can't Book The Donation \n Due to Having Malaria", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference reference1 = userDB.getReference("Request");
                    reference1.child(uid).setValue(announceRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return announce_name.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button announce_book_button;
        TextView announce_name_textview, announce_date_textview, announce_location_textview, announce_time_textview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            announce_name_textview = itemView.findViewById(R.id.announce_name_textview);
            announce_location_textview = itemView.findViewById(R.id.announce_location_textview);
            announce_date_textview = itemView.findViewById(R.id.announce_date_textview);
            announce_time_textview = itemView.findViewById(R.id.announce_time_textview);
            announce_book_button = itemView.findViewById(R.id.announce_book_button);
        }
    }
}
