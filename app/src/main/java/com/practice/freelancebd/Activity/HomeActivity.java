package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.practice.freelancebd.Fragments.ChatFragment;
import com.practice.freelancebd.Fragments.HomeFragment;
import com.practice.freelancebd.Fragments.NotificationFragment;
import com.practice.freelancebd.Fragments.PostJobFragment;
import com.practice.freelancebd.Fragments.ProfileFragment;
import com.practice.freelancebd.R;


public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private TextView toolbarTextView;
    private AutoCompleteTextView searchACTV;



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

        setSupportActionBar(toolbar);

        replaceFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.homeNavItem:

                        if(searchACTV.getVisibility() == View.VISIBLE){
                            toolbarTextView.setVisibility(View.GONE);
                        }
                        else {
                            toolbarTextView.setVisibility(View.VISIBLE);
                        }
                        toolbarTextView.setText("Home");
                        toolbar.getMenu().setGroupVisible(R.id.searchGrp,true);
                        replaceFragment(new HomeFragment());
                        return true;

                    case R.id.chatNavItem:
                        toolbarTextView.setVisibility(View.VISIBLE);
                        toolbarTextView.setText("Chat");
                        searchACTV.setVisibility(View.GONE);
                        toolbar.getMenu().setGroupVisible(R.id.searchGrp,false);
                        replaceFragment(new ChatFragment());
                        return true;

                    case R.id.notificationsNavItem:
                        toolbarTextView.setVisibility(View.VISIBLE);
                        searchACTV.setVisibility(View.GONE);
                        toolbarTextView.setText("Notification");
                        replaceFragment(new NotificationFragment());
                        return true;

                    case R.id.postJobNavItem:
                        toolbarTextView.setVisibility(View.VISIBLE);
                        searchACTV.setVisibility(View.GONE);
                        toolbarTextView.setText("Post job");
                        replaceFragment(new PostJobFragment());
                        return true;

                    case R.id.profileNavItem:
                        toolbarTextView.setVisibility(View.VISIBLE);
                        searchACTV.setVisibility(View.GONE);
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
        menuInflater.inflate(R.menu.log_out_option,menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.search:
                toolbarTextView.setVisibility(View.GONE);
                searchACTV.setVisibility(View.VISIBLE);
                break;
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
        searchACTV = findViewById(R.id.searchACTV);

    }

}
