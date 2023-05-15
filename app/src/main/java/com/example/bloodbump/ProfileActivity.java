package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private CircleImageView profile_image;
    private TextView fullname_textview;
    private EditText old_password, new_password, new_confirm_password;
    private Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile_image = findViewById(R.id.profile_image);
        fullname_textview = findViewById(R.id.fullname_textview);
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        new_confirm_password = findViewById(R.id.new_confirm_password);
        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(this::changePassword);
        // Getting Fullname
        userAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
        reference = userDB.getReference("User");
        String UID = userAuth.getCurrentUser().getUid();
        reference.child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot data = task.getResult();
                String first_name = data.child("first_name").getValue().toString();
                String last_name = data.child("last_name").getValue().toString();
                String fullname = first_name + " " + last_name;
                fullname_textview.setText(fullname);
            }
        });
    }

    public void backToHome(View view) {
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
    }

    public void picOptions(View view) {
    }

    public void letChangePassword(View view) {
        old_password.setVisibility(View.VISIBLE);
        new_password.setVisibility(View.VISIBLE);
        new_confirm_password.setVisibility(View.VISIBLE);
        save_button.setVisibility(View.VISIBLE);
        save_button.setBackground(getResources().getDrawable(R.drawable.large_red_button));
        save_button.setText("Save");
        save_button.setTextColor(getResources().getColor(R.color.white));
    }
    public void changePassword(View view){
        String old_pass = old_password.getText().toString().trim();
        String new_pass = new_password.getText().toString().trim();
        String confirm_pass = new_confirm_password.getText().toString().trim();
        String email =  userAuth.getCurrentUser().getEmail();
        userAuth.signInWithEmailAndPassword(email, old_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(new_pass.isEmpty()){
                        new_password.setError("Enter The Password");
                        new_password.requestFocus();
                    }else if(confirm_pass.isEmpty()){
                        new_confirm_password.setError("Confirm The Password");
                        new_confirm_password.requestFocus();
                    } else if (!new_pass.equals(confirm_pass)) {
                        new_confirm_password.setError("Password Doesn't Match");
                        new_confirm_password.requestFocus();
                    }else {
                        userAuth.getCurrentUser().updatePassword(new_pass);
                        String UID = userAuth.getCurrentUser().getUid();
                        reference = userDB.getReference("User");
                        reference.child(UID).child("password").setValue(new_pass).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                save_button.setBackground(getResources().getDrawable(R.drawable.transparent_button));
                                save_button.setText("Saved");
                                save_button.setTextColor(getResources().getColor(R.color.russian_green));
                                old_password.setVisibility(View.GONE);
                                new_password.setVisibility(View.GONE);
                                new_confirm_password.setVisibility(View.GONE);
                            }
                        });
                    }
                }else{
                    old_password.setError("Wrong Password");
                    old_password.requestFocus();
                }
            }
        });
    }
}