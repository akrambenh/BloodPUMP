package com.example.bloodbump;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import io.getstream.avatarview.AvatarView;

public class picAddActivity extends AppCompatActivity {
    private ImageView user_pic;
    private AvatarView avatarView;
    private Button skip_continue_button;
    private Button take_pic_button;
    private Button upload_pic_button;
    public static final int PICK_IMAGE = 1;
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
            private ContentResolver getContentResolver;

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
        });
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE){
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                int w  = bitmap.getWidth();
                int h = bitmap.getHeight();
                Bitmap rounder = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(rounder);
                Paint xferPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                xferPaint.setColor(Color.RED);
                canvas.drawRoundRect(new RectF(0,0,w,h), 20.0f, 20.0f, xferPaint);
                xferPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() ,Bitmap.Config.ARGB_8888);
                Canvas resultCanvas = new Canvas(result);
                resultCanvas.drawBitmap(bitmap, 0, 0, null);
                resultCanvas.drawBitmap(rounder, 0, 0, xferPaint);
                avatarView.setImageBitmap(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
