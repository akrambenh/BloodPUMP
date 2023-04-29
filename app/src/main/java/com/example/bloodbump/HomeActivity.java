package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private StorageReference storage;
    private CircleImageView profile_image;
    private TextView nameText;
    private DrawerLayout drawerLayout;
    private  androidx.appcompat.widget.Toolbar toolbar;

    @SuppressLint({"UseSupportActionBar", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        profile_image = findViewById(R.id.profile_image);
        nameText = findViewById(R.id.nameText);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        userAuth = FirebaseAuth.getInstance();
        String UID = userAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance().getReference("Donor/"+  UID+  "/ProfilePic.jpg");
        try {
            File profile_pic = File.createTempFile("profilePic", ".jpg");
            storage.getFile(profile_pic).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(profile_pic.getAbsolutePath());
                profile_image.setImageBitmap(bitmap);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userDB = FirebaseDatabase.getInstance();
        reference = userDB.getReference("User");
        reference.child(UID).get().addOnCompleteListener(task -> {
            DataSnapshot data = task.getResult();
            String name = data.child("first_name").getValue().toString();
            String lastName = data.child("last_name").getValue().toString();
            String fullname = name + " " + lastName;
            nameText.setText(fullname);
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new HomeFragment()).commit();
                break;
            case R.id.nav_appointment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new appointmentFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(!Objects.equals(getSupportFragmentManager().findFragmentById(R.id.fragment_content), new HomeFragment())){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new HomeFragment()).commit();
            // Add onBackPressed On HomeFragment to Destroy activity
        } else {
            super.onBackPressed();
        }
    }

    public void JumpDonationFrag(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new donationFragment()).commit();
    }

    public void DonateBlood(View view) {
        startActivity(new Intent(HomeActivity.this, AppointmentActivity.class));
    }
}

