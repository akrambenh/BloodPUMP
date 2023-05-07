package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectVenueActivity extends AppCompatActivity {
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private ImageView back_arrow;
    private ImageView header;
    private TextView suggest_venue1, suggest_venue2, suggest_venue3;
    private Button suggest_button1, suggest_button2, suggest_button3;
    public String chu_setif = "CHU Saadna Abdenour De Sétif";
    public String centre_transfusion = "Centre de transfusion sanguine (CTS)";
    public String chu_mustapha = "Mustapha University Hospital Center";
    public String[] suggestedCentre = {centre_transfusion, chu_mustapha, chu_setif};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_venue);

        header = findViewById(R.id.header);
        back_arrow = findViewById(R.id.back_arrow_button);
        suggest_venue1 = findViewById(R.id.suggest_venue1);
        suggest_venue2 = findViewById(R.id.suggest_venue2);
        suggest_venue3 = findViewById(R.id.suggest_venue3);
        suggest_button1 = findViewById(R.id.suggest_button1);
        suggest_button2 = findViewById(R.id.suggest_button2);
        suggest_button3 = findViewById(R.id.suggest_button3);
        suggest_button1.setOnClickListener(this::onClick);
        suggest_button2.setOnClickListener(this::onClick);
        suggest_button3.setOnClickListener(this::onClick);
        getSuggestion();
    }

    private void getSuggestion() {

        TextView[] suggestedVenue = {suggest_venue1, suggest_venue2, suggest_venue3};
        for(int i = 0;i < suggestedCentre.length;i++){
            suggestedVenue[i].setText(suggestedCentre[i]);
        }
    }

    public void backToHome(View view) {
        startActivity(new Intent(SelectVenueActivity.this, HomeActivity.class));
    }
    public void onClick(View v){
        Intent intent = new Intent(SelectVenueActivity.this, DateSelectionActivity.class);
        intent.putExtra("predecessor_activity", "AppointmentActivity");
        switch(v.getId()){
            case R.id.suggest_button1:
                intent.putExtra("venue", suggestedCentre[0]);
                startActivity(intent);
                break;
            case R.id.suggest_button2:
                intent.putExtra("venue", suggestedCentre[1]);
                startActivity(intent);
                break;
            case R.id.suggest_button3:
                intent.putExtra("venue", suggestedCentre[2]);
                startActivity(intent);
                break;
        }
    }


    public void searchVenue(View view) {
        Intent intent = new Intent(SelectVenueActivity.this, SearchVenueActivity.class);
        intent.putExtra("predecessor_activity", "AppointmentActivity");
        startActivity(intent);
    }
}