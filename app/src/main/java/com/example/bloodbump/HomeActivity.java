package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    private CircleImageView profile_nav;
    private TextView nameText;
    private DrawerLayout drawerLayout;
    private androidx.appcompat.widget.Toolbar toolbar;

    @SuppressLint({"UseSupportActionBar", "MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appointmentNotify();
        toolbar = findViewById(R.id.toolbar);
        profile_image = findViewById(R.id.profile_image);
        nameText = findViewById(R.id.nameText);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        profile_nav = header.findViewById(R.id.profile_nav);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        userAuth = FirebaseAuth.getInstance();
        String UID = userAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance().getReference("Donor/" + UID + "/ProfilePic.jpg");
        try {
            File profile_pic = File.createTempFile("ProfilePic", ".jpg");
            storage.getFile(profile_pic).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(profile_pic.getAbsolutePath());
                    profile_image.setImageBitmap(bitmap);
                    profile_nav.setImageBitmap(bitmap);
                }
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
                startActivity(new Intent(HomeActivity.this, AppointmentActivity.class));
                break;
            case R.id.nav_logout:
                userAuth.signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!Objects.equals(getSupportFragmentManager().findFragmentById(R.id.fragment_content), new HomeFragment())) {
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
        startActivity(new Intent(HomeActivity.this, SelectVenueActivity.class));
    }

    public void JumpHealthReportFrag(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new HealthReportFragment()).commit();
    }
    public void JumpSearchVenue(View view) {
        Intent intent = new Intent(HomeActivity.this, SearchVenueActivity.class);
        intent.putExtra("predecessor_activity", "HomeActivity");
        startActivity(intent);
    }

    public void appointmentNotify() {
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseDatabase userDB = FirebaseDatabase.getInstance();
        DatabaseReference reference;
        String UID = userAuth.getCurrentUser().getUid();
        reference = userDB.getReference("Appointment");
        reference.child(UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().exists()) {
                    //
                    DataSnapshot data = task.getResult();

                    String date = String.valueOf(data.child("Donation Date").getValue());
                    String time = String.valueOf(data.child("Donation Time").getValue());
                    String venue = String.valueOf(data.child("Medical Establishment").getValue());
                    String type = String.valueOf(data.child("Donation Type").getValue());
                    String id = "channel_id_one";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = "channel_one";
                        int importance = NotificationManager.INTERRUPTION_FILTER_NONE;
                        @SuppressLint("WrongConstant")
                        NotificationChannel channel = new NotificationChannel(id, name, importance);
                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                        String notifiactionContent = "The '" + type +"' Donation will be done on " + date + " at " + time + " in " + venue;
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), id)
                                .setSmallIcon(R.drawable.icon)
                                .setContentTitle("Appointment Is Set")
                                .setContentText(notifiactionContent)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(notifiactionContent))
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setAutoCancel(true)
                                .setPriority(NotificationCompat.BADGE_ICON_SMALL);
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
                        Intent resultIntent = new Intent(getApplicationContext(), AppointmentActivity.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                        stackBuilder.addNextIntentWithParentStack(resultIntent);
                        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding

                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        builder.setContentIntent(resultPendingIntent);
                        builder.setOngoing(true);
                        managerCompat.notify(1, builder.build());
                    }

                }
            }
        });
    }

    public void checkHistory(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new HistoryFragment()).commit();
    }

    public void JumpAnnounceFrag(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new AnnounceFragment()).commit();
    }

    public void JumpProfile(View view) {
        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
    }

    public void makeDonation(View view) {
        startActivity(new Intent(HomeActivity.this, SelectVenueActivity.class));
    }
}

