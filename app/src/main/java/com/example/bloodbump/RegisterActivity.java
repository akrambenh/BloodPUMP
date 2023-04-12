package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    public Boolean status = false;
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDatabase;
    private DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userAuth = FirebaseAuth.getInstance();
        EditText user_name = (EditText) findViewById(R.id.user_name);
        EditText user_lastname = (EditText) findViewById(R.id.user_lastname);
        EditText user_email = (EditText) findViewById(R.id.user_email);
        EditText user_password = (EditText) findViewById(R.id.user_password);
        EditText confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        Button sign_upButton = (Button) findViewById(R.id.sign_upButton);
        EditText user_phone = (EditText) findViewById(R.id.user_phone);
        sign_upButton.setOnClickListener(view -> {
            String name = user_name.getText().toString().trim();
            String lastname = user_lastname.getText().toString().trim();
            String email = user_email.getText().toString().trim();
            String password = user_password.getText().toString().trim();
            String ConfirmedPassword = confirmPassword.getText().toString().trim();
            String phone = user_phone.getText().toString().trim();

            // Using A Regular Expression to avoid numbers in names
            Pattern numbers = Pattern.compile("[1-9]");
            //Using A Regular Expression to avoid characters and symbols in National Number
            if(name.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || ConfirmedPassword.isEmpty()){
                Log.d(TAG, "Not All Fields Are Fields");
                Toast.makeText(RegisterActivity.this, "Please Fill All The Fields", Toast.LENGTH_LONG).show();
            } else if (numbers.matcher(name).matches()) {
                user_name.setError("Name Must Not Have Numbers");
                user_name.requestFocus();
            } else if (numbers.matcher(lastname).matches()) {
                user_lastname.setError("Last Name Must Not Have Numbers");
                user_lastname.requestFocus();
            } else if(!ConfirmedPassword.matches(password)){
                confirmPassword.setError("Password Does Not Match");
                confirmPassword.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                user_email.setError("Please Enter A Valid Email");
                user_email.requestFocus();
            } else {
                // complete constructor

                userDatabase = FirebaseDatabase.getInstance();
                reference = userDatabase.getReference("User");
                Donor user = new Donor(name, lastname, email, password, phone);
                String fullname = user.first_name + " " + user.last_name;
                userAuth.createUserWithEmailAndPassword(user.email, user.password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String ID = userAuth.getUid().toString();
                                    reference.child(ID).setValue(user);
                                    Toast.makeText(RegisterActivity.this, "User Has Been Registered Successfully! \n Complete Additional Information to start Booking donation", Toast.LENGTH_LONG).show();
                                    Toast.makeText(RegisterActivity.this, "Welcome " + fullname, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(RegisterActivity.this, CompleteRegistrationActivity.class));
                                }else {
                                    Toast.makeText(RegisterActivity.this, "Failed To Register!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
        });
    }

    public void switch_login(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}