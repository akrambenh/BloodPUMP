package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterAdminActivity extends AppCompatActivity {
    private EditText venue_name;
    private EditText Su_pseudo;
    private EditText venue_email;
    private EditText venue_password;
    private EditText venue_ConfPassword;
    private EditText venue_phone;
    private Button addVenue_button;
    private FirebaseAuth adminAuth;
    private FirebaseDatabase adminDB;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);
        venue_name = (EditText) findViewById(R.id.venue_name);
        Su_pseudo = (EditText) findViewById(R.id.pseudo);
        venue_email = (EditText) findViewById(R.id.venue_email);
        venue_password = (EditText) findViewById(R.id.venue_password);
        venue_ConfPassword= (EditText) findViewById(R.id.venue_ConfPassword);
        venue_phone = (EditText) findViewById(R.id.venue_phone);
        addVenue_button = (Button) findViewById(R.id.addVenue_button);
        // Using A Regular Expression to avoid numbers in names
        Pattern numbers = Pattern.compile("[1-9]");
        addVenue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = venue_name.getText().toString().trim();
                String email = venue_email.getText().toString().trim();
                String pseudo = Su_pseudo.getText().toString().trim();
                String phone = venue_phone.getText().toString().trim();
                String password = venue_password.getText().toString().trim();
                String confirmation = venue_ConfPassword.getText().toString().trim();
                if(name.isEmpty() || email.isEmpty() || pseudo.isEmpty() || password.isEmpty()){
                    Toast.makeText(RegisterAdminActivity.this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                } else if (numbers.matcher(name).matches()) {
                venue_name.setError("Name Must Not Have Numbers");
                venue_name.requestFocus();
            } else if(!confirmation.matches(password)){
                venue_ConfPassword.setError("Password Does Not Match");
                venue_ConfPassword.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                venue_email.setError("Please Enter A Valid Email");
                venue_name.requestFocus();

            } else if (!Patterns.PHONE.matcher(phone).matches()) {
                    venue_phone.setError("Please Enter A Valid Phone");
                    venue_phone.requestFocus();
                } else {
                    adminAuth = FirebaseAuth.getInstance();
                    adminAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        String UID = adminAuth.getCurrentUser().getUid().toString();
                                        Hospital hospital = new Hospital(UID, name, email, pseudo, phone, password);
                                        adminDB = FirebaseDatabase.getInstance();
                                        // Create Admin Table
                                        reference = adminDB.getReference("Admin");
                                        reference.child(UID).setValue(hospital);
                                        // Create Hospital Table
                                        Toast.makeText(RegisterAdminActivity.this, "Hospital Has Been Registered", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterAdminActivity.this, AdminHomeActivity.class));
                                    }
                                }
                            });
                }
            }
        });
    }

    public void switch_login(View view) {
        startActivity(new Intent(RegisterAdminActivity.this, AdminLoginActivity.class));
    }
}