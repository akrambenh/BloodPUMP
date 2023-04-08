package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button login_button, signup_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_button = (Button) findViewById(R.id.login_button);
        signup_button = (Button) findViewById(R.id.signup_button);
    }
    public void login(View view){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
    public void signup(View view){
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
    }

}