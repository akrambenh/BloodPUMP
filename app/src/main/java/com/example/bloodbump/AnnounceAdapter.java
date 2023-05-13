package com.example.bloodbump;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnnounceAdapter extends RecyclerView.Adapter<AnnounceAdapter.MyViewHolder> {
    private Context context;
    private ArrayList announce_location,announce_establishment, announce_date, announce_time;
    AnnounceAdapter(Context context, ArrayList announce_location,ArrayList announce_establishment, ArrayList announce_date, ArrayList announce_time){
        this.context = context;
        this.announce_location = announce_location;
        this.announce_establishment = announce_establishment;
        this.announce_date = announce_date;
        this.announce_time = announce_time;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inflate_announce_row, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.announce_establishment_textview.setText(String.valueOf(announce_establishment.get(position)));
        holder.announce_location_textview.setText(String.valueOf(announce_location.get(position)));
        holder.announce_date_textview.setText(String.valueOf(announce_date.get(position)));
        holder.announce_time_textview.setText(String.valueOf(announce_time.get(position)));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button announce_book_button;
        TextView announce_establishment_textview, announce_date_textview, announce_location_textview, announce_time_textview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            announce_establishment_textview = itemView.findViewById(R.id.announce_establishment_textview);
            announce_location_textview = itemView.findViewById(R.id.announce_location_textview);
            announce_date_textview = itemView.findViewById(R.id.announce_date_textview);
            announce_time_textview = itemView.findViewById(R.id.announce_time_textview);
            announce_book_button = itemView.findViewById(R.id.announce_book_button);
        }
    }
}
