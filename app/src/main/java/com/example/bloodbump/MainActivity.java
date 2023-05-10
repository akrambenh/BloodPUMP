package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.StartupTime;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button login_button, signup_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(preferences.getString("email", "") != null &&
                preferences.getString("password", "") != null){
            FirebaseAuth userAuth = FirebaseAuth.getInstance();
            String email = preferences.getString("email", "");
            String password = preferences.getString("password", "");
            userAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            });
        }
        setContentView(R.layout.activity_main);
        login_button = (Button) findViewById(R.id.login_button);
        signup_button = (Button) findViewById(R.id.signup_button);


    }
    public void login(View view){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
    public void signup(View view){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

}