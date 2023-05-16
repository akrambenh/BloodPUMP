package com.example.bloodpump;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        /*if(preferences.getString("email", "") != null &&
                preferences.getString("password", "") != null){
            FirebaseAuth userAuth = FirebaseAuth.getInstance();
            String email = preferences.getString("email", "");
            String password = preferences.getString("password", "");
            userAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> startActivity(new Intent(MainActivity.this, HomeActivity.class)));
        }else*/
            setContentView(R.layout.activity_main);

    }
    public void login(View view){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
    public void signup(View view){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

}