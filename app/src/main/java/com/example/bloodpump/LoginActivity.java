package com.example.bloodpump;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText login_email, login_password;
    private FirebaseAuth bumpAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bumpAuth = FirebaseAuth.getInstance();
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        Button login_button = findViewById(R.id.login_button);
        forgotPassword.setPaintFlags(forgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        login_button.setOnClickListener(view -> {
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
                bumpAuth.signInWithEmailAndPassword(donor.email, donor.password)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                // Saving Credentials
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("email", email);
                                editor.putString("password", password);
                                editor.apply();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            }else
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        });

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