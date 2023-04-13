package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Fade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AppointmentActivity extends AppCompatActivity {
    private ImageView back_arrow;
    private ImageView header;
    private TextView suggest_venue1;
    private Button suggest_button1;
    private TextView suggest_date1;
    private TextView suggest_time1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        header = findViewById(R.id.header);
        back_arrow = findViewById(R.id.back_arrow_button);
        suggest_venue1 = findViewById(R.id.suggest_venue1);
        suggest_button1 = findViewById(R.id.suggest_button1);
        suggest_button1.setOnClickListener(this::onClick);
        suggest_date1 = findViewById(R.id.suggest_date1);
        suggest_time1 = findViewById(R.id.suggest_time1);
    }

    public void backToHome(View view) {
        startActivity(new Intent(AppointmentActivity.this, HomeActivity.class));
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.suggest_button1:

                break;
        }
    }
}