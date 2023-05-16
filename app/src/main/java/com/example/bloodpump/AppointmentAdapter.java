package com.example.bloodpump;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {
    private Context context;
    private ArrayList Appointment_venue, Appointment_date, Appointment_time, Appointment_type;
    AppointmentAdapter(Context context, ArrayList appointment_venue, ArrayList appointment_date, ArrayList appointment_time, ArrayList appointment_type){
        this.context = context;
        this.Appointment_venue = appointment_venue;
        this.Appointment_date = appointment_date;
        this.Appointment_time = appointment_time;
        this.Appointment_type = appointment_type;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inflate_appointment_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.appointment_venue.setText(String.valueOf(Appointment_venue.get(position)));
        holder.appointment_date.setText(String.valueOf(Appointment_date.get(position)));
        holder.appointment_time.setText(String.valueOf(Appointment_time.get(position)));
        holder.appointment_type.setText(String.valueOf(Appointment_type.get(position)));
    }

    @Override
    public int getItemCount() {
        return Appointment_venue.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView appointment_venue, appointment_date, appointment_time, appointment_type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            appointment_venue = itemView.findViewById(R.id.appointment_venue);
            appointment_date = itemView.findViewById(R.id.appointment_date);
            appointment_time = itemView.findViewById(R.id.appointment_time);
            appointment_type = itemView.findViewById(R.id.appointment_type);
        }
    }
}
