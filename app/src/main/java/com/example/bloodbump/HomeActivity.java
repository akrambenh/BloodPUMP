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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CircleImageView profile_image;
    private TextView locationText;
    private DrawerLayout drawerLayout;
    private  androidx.appcompat.widget.Toolbar toolbar;

    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
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

