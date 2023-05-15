package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText reset_email, reset_password;
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private Button send_email_button, reset_password_button;
    private ImageView back_arrow_button;
    private String email = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        reset_email = findViewById(R.id.reset_email);
        send_email_button = findViewById(R.id.send_email_button);
        reset_password = findViewById(R.id.reset_password);
        reset_password_button = findViewById(R.id.reset_password_button);
        back_arrow_button = findViewById(R.id.back_arrow_button);
        userAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
    }

    public void sendResetEmail(View view) {
        email = reset_email.getText().toString().trim();
        userAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                    send_email_button.setBackground(getResources().getDrawable(R.drawable.transparent_button));
                    send_email_button.setText("SENT");
                    send_email_button.setTextColor(getResources().getColor(R.color.russian_green));
                    reset_email.setVisibility(View.GONE);
                    reset_password.setVisibility(View.VISIBLE);
                    reset_password_button.setVisibility(View.VISIBLE);

                }else{
                    Toast.makeText(ResetPasswordActivity.this, "Failed To Send Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void confirmPassword(View view) {
        String password = reset_password.getText().toString().trim();
        userAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String UID = userAuth.getCurrentUser().getUid();
                reference = userDB.getReference("User");
                reference.child(UID).child("password").setValue(password).addOnSuccessListener(unused -> {
                    Toast.makeText(ResetPasswordActivity.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPasswordActivity.this, HomeActivity.class));
                });
            }else {
                reset_password.setError("Wrong Password");
                reset_password.requestFocus();
            }
        });
    }

    public void backToHome(View view) {
        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
    }
}