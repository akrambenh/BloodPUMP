package com.example.bloodpump;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class picAddActivity extends AppCompatActivity {
    private FirebaseAuth userAuth;
    private FirebaseStorage storage;
    private Button skip_continue_button;
    private File photoFile = null;
    private String currentPhotoPath = null;
    private Button take_pic_button;
    private Button upload_pic_button;
    private CircleImageView profile_image;
    private Bitmap user_bitmap = null;
    private static final int CAMERA_REQUEST = 1888;
    public static final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_adding);
        skip_continue_button = (Button) findViewById(R.id.skip_continue_button);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        userAuth = FirebaseAuth.getInstance();
        String UID = userAuth.getCurrentUser().getUid();
        skip_continue_button.setOnClickListener(view -> {
            if(photoFile == null){
                // set standard avatar as profile pic and save in storage
            }else {
                storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://bloodbump-35398.appspot.com/");
                StorageReference avatarRef = storageRef.child("Donor/" + UID + "/ProfilePic.jpg");
                Bitmap bitmap = ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = avatarRef.putBytes(data);
                uploadTask.addOnFailureListener(e -> Toast.makeText(picAddActivity.this, "Failed To Upload Image", Toast.LENGTH_SHORT).show()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(picAddActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(picAddActivity.this, HomeActivity.class));
                    }
                });

            }
        });
    }
    public void PicOptions(View view) {
        Dialog dialog = new Dialog(picAddActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow();
        take_pic_button = dialog.findViewById(R.id.take_pic_button);
        upload_pic_button = dialog.findViewById(R.id.upload_pic_button);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE){
            Uri selectedImage = data.getData();
            try {
                user_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                //Bitmap roundPic = getRoundedCornerBitmap(user_bitmap);
                skip_continue_button.setText("Continue");
                profile_image.setImageBitmap(user_bitmap);
                photoFile = createImageFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }if(requestCode == CAMERA_REQUEST){
            user_bitmap = (Bitmap) data.getExtras().get("data");
            skip_continue_button.setText("Continue");
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
        //uploadUri = image.toURI();
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
