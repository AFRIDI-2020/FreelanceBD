package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.Handler;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.practice.freelancebd.Fragments.ChatFragment;
import com.practice.freelancebd.Fragments.HomeFragment;
import com.practice.freelancebd.Fragments.NotificationFragment;
import com.practice.freelancebd.Fragments.PostJobFragment;
import com.practice.freelancebd.Fragments.ProfileFragment;
import com.practice.freelancebd.R;


public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.firebase_data_loading_progress_dialog);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressDialog.setCancelable(false);
                progressDialog.dismiss();
            }
        }, 4000);


        init();


        replaceFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.homeNavItem:

                        replaceFragment(new HomeFragment());
                        return true;

                    case R.id.chatNavItem:

                        replaceFragment(new ChatFragment());
                        return true;

                    case R.id.notificationsNavItem:

                        replaceFragment(new NotificationFragment());
                        return true;

                    case R.id.postJobNavItem:

                        replaceFragment(new PostJobFragment());
                        return true;

                    case R.id.profileNavItem:

                        replaceFragment(new ProfileFragment());
                        return true;
                }


                return false;
            }
        });

    }


    private void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();


    }

    private void init() {

        bottomNavigationView = findViewById(R.id.bottomNavigation);

    }


}
