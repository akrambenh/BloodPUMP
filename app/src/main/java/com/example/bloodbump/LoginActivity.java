package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText login_email, login_password;
    private Button login_button;
    private TextView forgotPassword;

    private FirebaseAuth bumpAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // User Session
        setContentView(R.layout.activity_login);
        SessionManager sessionManager = new SessionManager(LoginActivity.this);
        sessionManager.getUserDetail();
        bumpAuth = FirebaseAuth.getInstance();
        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        login_button = (Button) findViewById(R.id.login_button);
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        if(sessionManager.checkLogin()){
            bumpAuth.signInWithEmailAndPassword(sessionManager.KEY_EMAIL, sessionManager.KEY_PASSWORD)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                    });
        }
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString().trim();
                String password = login_password.getText().toString().trim();
                if(email.isEmpty()){
                    login_email.setError("You Must Enter This Field");
                    login_email.requestFocus();
                }else if(password.isEmpty()){
                    login_password.setError("You Must Enter This Field");
                    login_password.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    login_email.setError("You Must Enter A Valid Email");
                    login_email.requestFocus();
                }else {
                    Donor donor = new Donor(email, password);
                    bumpAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                        sessionManager.createLoginSession(email, password);
                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    }else
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });

    }

    public void resetPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
    }

    public void switch_signUp(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}