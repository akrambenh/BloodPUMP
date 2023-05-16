package com.example.bloodpump;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList donation_venue, donation_date, donation_time, donation_type, donation_quantity;
    CustomAdapter(Context context, ArrayList donation_venue, ArrayList donation_date, ArrayList donation_time, ArrayList donation_type, ArrayList donation_quantity){
        this.context = context;
        this.donation_venue = donation_venue;
        this.donation_date = donation_date;
        this.donation_time = donation_time;
        this.donation_type = donation_type;
        this.donation_quantity = donation_quantity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.inflate_box_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.donation_venue_textview.setText(String.valueOf(donation_venue.get(position)));
        holder.donation_date_textview.setText(String.valueOf(donation_date.get(position)));
        holder.donation_time_textview.setText(String.valueOf(donation_time.get(position)));
        holder.donation_type_textview.setText(String.valueOf(donation_type.get(position)));
        holder.donation_quantity_textview.setText(String.valueOf(donation_quantity.get(position)));
    }

    @Override
    public int getItemCount() {
        return donation_date.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView donation_venue_textview, donation_date_textview, donation_time_textview, donation_type_textview, donation_quantity_textview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            donation_date_textview = itemView.findViewById(R.id.donation_date_textview);
            donation_time_textview = itemView.findViewById(R.id.donation_time_textview);
            donation_type_textview = itemView.findViewById(R.id.donation_type_textview);
            donation_venue_textview = itemView.findViewById(R.id.donation_venue);
            donation_quantity_textview = itemView.findViewById(R.id.quantity_textview);
        }
    }
}
