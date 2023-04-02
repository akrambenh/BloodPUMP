package com.example.bloodbump;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class picAddActivity extends AppCompatActivity {
    private ImageView user_pic;
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

        take_pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
        upload_pic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialog.show();

    }
}
