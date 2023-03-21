package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
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
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        RadioButton donor = (RadioButton) findViewById(R.id.donor);
        RadioButton center = (RadioButton) findViewById(R.id.center);
        EditText user_username = (EditText) findViewById(R.id.user_lastname);
        TextView textview8 = (TextView) findViewById(R.id.textview8);
        TextView textview9 = (TextView) findViewById(R.id.textView9);
        EditText user_phone = (EditText) findViewById(R.id.user_phone);
        sign_upButton.setOnClickListener(view -> {
            String name = user_name.getText().toString().trim();
            String lastname = user_lastname.getText().toString().trim();
            String username = user_username.getText().toString().trim();
            String email = user_email.getText().toString().trim();
            String password = user_password.getText().toString().trim();
            String ConfirmedPassword = confirmPassword.getText().toString().trim();
            String phone = user_phone.getText().toString().trim();
            String fullname = lastname + " " + name;
            int SelectedID = radioGroup.getCheckedRadioButtonId();
            RadioButton SelectedRadioButton = (RadioButton) findViewById(SelectedID);
            String UserType = SelectedRadioButton.getText().toString();
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
            } else if(SelectedRadioButton == donor){
                // complete constructor
                Donor user = new Donor(name, lastname, username, email, password, phone);
                userAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener(task -> {
                    try {
                        if (task.isComplete()) {
                            Log.d(TAG, "OnRegister: User Authenticated");
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child("Donor")
                                            .child(userAuth.getCurrentUser().getUid())
                                                    .setValue(user)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "Instance Created Successfully");
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Account Created Successfully " + user.first_name + " " + user.last_name + " !", Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "OnRegister: Transition to Document Auth Activity");
                                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                    } else {
                                        Log.d(TAG, "OnRegister: User Authentication Failed");
                                        Toast.makeText(RegisterActivity.this, "Failed To Register :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        }catch (NullPointerException e){
                        Log.d(TAG, "OnRegister: Null Pointer Exception Caught");
                    }
                });
            }else if(SelectedRadioButton == center){}
        });
    }
}