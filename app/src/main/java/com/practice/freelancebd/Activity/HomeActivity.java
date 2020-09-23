package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.practice.freelancebd.Fragments.ChatFragment;
import com.practice.freelancebd.Fragments.HomeFragment;
import com.practice.freelancebd.Fragments.NotificationFragment;
import com.practice.freelancebd.Fragments.PostJobFragment;
import com.practice.freelancebd.Fragments.ProfileFragment;
import com.practice.freelancebd.R;


public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private TextView toolbarTextView;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        init();

        setSupportActionBar(toolbar);

        replaceFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.homeNavItem:
                        toolbarTextView.setText("Home");
                        replaceFragment(new HomeFragment());
                        return true;

                    case R.id.chatNavItem:
                        toolbarTextView.setVisibility(View.VISIBLE);
                        toolbarTextView.setText("Chat");
                        replaceFragment(new ChatFragment());
                        return true;

                    case R.id.notificationsNavItem:
                        toolbarTextView.setVisibility(View.VISIBLE);
                        toolbarTextView.setText("Notification");
                        replaceFragment(new NotificationFragment());
                        return true;

                    case R.id.postJobNavItem:
                        toolbarTextView.setVisibility(View.VISIBLE);
                        toolbarTextView.setText("Post job");
                        replaceFragment(new PostJobFragment());
                        return true;

                    case R.id.profileNavItem:
                        toolbarTextView.setVisibility(View.VISIBLE);
                        toolbarTextView.setText("Profile");
                        replaceFragment(new ProfileFragment());
                        return true;
                }


                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.log_out_option, menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {


            case R.id.logOut:
                firebaseAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LogInActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();


    }


    private void init() {

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        toolbar = findViewById(R.id.toolbar);
        toolbarTextView = findViewById(R.id.toolbarTextView);
        firebaseAuth = FirebaseAuth.getInstance();

    }

}
