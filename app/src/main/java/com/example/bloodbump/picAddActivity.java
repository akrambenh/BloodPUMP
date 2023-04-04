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
import android.graphics.Rect;
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

import de.hdodenhof.circleimageview.CircleImageView;
import io.getstream.avatarview.AvatarView;

public class picAddActivity extends AppCompatActivity {
    private Button skip_continue_button;
    private Button take_pic_button;
    private Button upload_pic_button;
    private CircleImageView profile_image;
    public Bitmap user_bitmap;
    public static final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_adding);
        skip_continue_button = (Button) findViewById(R.id.skip_continue_button);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
    }
    public void PicOptions(View view) {
        Toast.makeText(this, "Opening Pic Options", Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(picAddActivity.this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow();
        take_pic_button = dialog.findViewById(R.id.take_pic_button);
        upload_pic_button = dialog.findViewById(R.id.upload_pic_button);

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
                dialog.hide();
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
                user_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                //Bitmap roundPic = getRoundedCornerBitmap(user_bitmap);
                profile_image.setImageBitmap(user_bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        // Used This Method to manually make photo's corners Rounder now with CircleImageView Dependency this method is not needed
    }public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Toast.makeText(this, "Making Pic Rounder", Toast.LENGTH_SHORT).show();
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 380;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
