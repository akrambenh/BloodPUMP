package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    public static final int PICK_IMAGE = 1;
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private CircleImageView profile_image;
    private TextView fullname_textview;
    private EditText old_password, new_password, new_confirm_password;
    private EditText old_email, new_email, password;
    private Button save_button;
    private boolean imageChanged = false;
    private boolean passFieldShown = false;
    private  boolean emailFieldShown = false;
    private Bitmap user_bitmap = null;
    private File photoFile = null;
    private String UID = null;
    private String currentPhotoPath = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile_image = findViewById(R.id.profile_image);
        fullname_textview = findViewById(R.id.fullname_textview);
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        new_confirm_password = findViewById(R.id.new_confirm_password);
        old_email = findViewById(R.id.old_email);
        new_email = findViewById(R.id.new_email);
        password = findViewById(R.id.password);
        save_button = findViewById(R.id.save_button);
        save_button.setOnClickListener(this::handleSaving);
        profile_image.setOnClickListener(this::picOptions);
        // Getting Fullname
        userAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
        reference = userDB.getReference("User");
        UID = userAuth.getCurrentUser().getUid();
        reference.child(UID).get().addOnCompleteListener(task -> {
            DataSnapshot data = task.getResult();
            String first_name = data.child("first_name").getValue().toString();
            String last_name = data.child("last_name").getValue().toString();
            String fullname = first_name + " " + last_name;
            fullname_textview.setText(fullname);
        });
        storageReference = FirebaseStorage.getInstance().getReference("Donor/" + UID + "/ProfilePic.jpg");
        try {
            File profile_pic = File.createTempFile("ProfilePic", ".jpg");
            storageReference.getFile(profile_pic).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(profile_pic.getAbsolutePath());
                    profile_image.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void backToHome(View view) {
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
    }
    public void picOptions(View view) {
        Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow();
        Button take_pic_button = dialog.findViewById(R.id.take_pic_button);
        Button upload_pic_button = dialog.findViewById(R.id.upload_pic_button);

        take_pic_button.setOnClickListener(view1 -> {
            Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(CameraIntent, CAMERA_REQUEST);
        });
        upload_pic_button.setOnClickListener(view12 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            dialog.hide();
        });
        dialog.show();
    }
    public void letChangePassword(View view) {
        passFieldShown = true;
        emailFieldShown = false;
        //
        old_password.setVisibility(View.VISIBLE);
        new_password.setVisibility(View.VISIBLE);
        new_confirm_password.setVisibility(View.VISIBLE);
        save_button.setVisibility(View.VISIBLE);
        save_button.setBackground(getResources().getDrawable(R.drawable.large_red_button));
        save_button.setText("Save");
        save_button.setTextColor(getResources().getColor(R.color.white));
        //
        old_email.setVisibility(View.GONE);
        new_email.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
    }
    public void handleSaving(View view){
        if(imageChanged){
            storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://bloodbump-35398.appspot.com/");
            StorageReference avatarRef = storageRef.child("Donor/" + UID + "/ProfilePic.jpg");
            Bitmap bitmap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = avatarRef.putBytes(data);
            uploadTask.addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()).addOnSuccessListener(taskSnapshot -> {
                save_button.setBackground(getResources().getDrawable(R.drawable.transparent_button));
                save_button.setText("Saved");
                save_button.setTextColor(getResources().getColor(R.color.russian_green));
                old_password.setVisibility(View.GONE);
                new_password.setVisibility(View.GONE);
                new_confirm_password.setVisibility(View.GONE);
            });
        }
        if(passFieldShown){
            String old_pass = old_password.getText().toString().trim();
            String new_pass = new_password.getText().toString().trim();
            String confirm_pass = new_confirm_password.getText().toString().trim();
            if(old_pass.isEmpty() && new_pass.isEmpty() && confirm_pass.isEmpty()){

            }else {
                String email =  userAuth.getCurrentUser().getEmail();
                userAuth.signInWithEmailAndPassword(email, old_pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(new_pass.isEmpty()){
                            new_password.setError("Field Must Be Filled");
                            new_password.requestFocus();
                        }else if(confirm_pass.isEmpty()){
                            new_confirm_password.setError("You Must Confirm The Password");
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
                });
            }
        }
        if(emailFieldShown){
            String old_address = old_email.getText().toString().trim();
            String new_address = new_email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            if(old_address.isEmpty() && new_address.isEmpty() && pass.isEmpty()){

            }else{
                String current_email = userAuth.getCurrentUser().getEmail();
                if(old_address.isEmpty()){
                    old_email.setError("Field Must Be Filled");
                    old_email.requestFocus();
                }else if(new_address.isEmpty()){
                    new_email.setError("You Must Confirm The Email");
                    new_email.requestFocus();
                }else if(pass.isEmpty()){
                    password.setError("Password Must Be Entered");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(old_address).matches()){
                    old_email.setError("Enter A Valid Email");
                    old_email.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(new_address).matches()){
                    new_email.setError("Enter A Valid Email");
                }else if(!old_address.equals(current_email)){
                    old_email.setError("Wrong Email");
                    old_email.requestFocus();
                }else{

                        userAuth.signInWithEmailAndPassword(current_email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    reference = userDB.getReference("User");
                                    userAuth.getCurrentUser().updateEmail(new_address).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                reference.child(UID).child("email").setValue(new_address).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        save_button.setBackground(getResources().getDrawable(R.drawable.transparent_button));
                                                        save_button.setText("Saved");
                                                        save_button.setTextColor(getResources().getColor(R.color.russian_green));
                                                        old_email.setVisibility(View.GONE);
                                                        new_email.setVisibility(View.GONE);
                                                        password.setVisibility(View.GONE);
                                                    }
                                                });
                                            }else
                                                Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }else {
                                    Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE){
            Uri selectedImage = data.getData();
            try {
                user_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                //Bitmap roundPic = getRoundedCornerBitmap(user_bitmap);
                profile_image.setImageBitmap(user_bitmap);
                photoFile = createImageFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }if(requestCode == CAMERA_REQUEST){
            user_bitmap = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(user_bitmap);
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "ProfilePic";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",       /* suffix */
                storageDir     /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        imageChanged = true;
        return image;
    }

    public void letChangeEmail(View view) {
        emailFieldShown = true;
        passFieldShown = false;
        //
        old_email.setVisibility(View.VISIBLE);
        new_email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        save_button.setVisibility(View.VISIBLE);
        save_button.setBackground(getResources().getDrawable(R.drawable.large_red_button));
        save_button.setText("Save");
        save_button.setTextColor(getResources().getColor(R.color.white));
        //
        old_password.setVisibility(View.GONE);
        new_password.setVisibility(View.GONE);
        new_confirm_password.setVisibility(View.GONE);
    }
}