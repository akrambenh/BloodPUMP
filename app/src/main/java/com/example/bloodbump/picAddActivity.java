package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import io.getstream.avatarview.AvatarView;

public class picAddActivity extends AppCompatActivity {
    private ImageView user_pic;
    private AvatarView avatarView;
    private Button skip_continue_button;
    private Button take_pic_button;
    private Button upload_pic_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_adding);

        user_pic = (ImageView) findViewById(R.id.user_pic);
        skip_continue_button = (Button) findViewById(R.id.skip_continue_button);
    }
    public void PicOptions(View view) {
        Toast.makeText(this, "Opening Pic Options", Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(picAddActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        take_pic_button = dialog.findViewById(R.id.take_pic_button);
        upload_pic_button = dialog.findViewById(R.id.upload_pic_button);
        avatarView = (AvatarView) findViewById(R.id.avatarView);

        take_pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        upload_pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);

            }
        });
        dialog.show();

    }
    @Override
    public void startActivityForResult(Intent intent, int requestCode){
        super.startActivityForResult(intent, requestCode);
        if(requestCode == RESULT_OK && intent != null){
            Uri selectedImage = intent.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Drawable d = new BitmapDrawable(bitmap);
                avatarView.setPlaceholder(d);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
