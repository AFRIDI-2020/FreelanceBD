package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Fragments.ChatFragment;
import com.practice.freelancebd.Fragments.HomeFragment;
import com.practice.freelancebd.Fragments.NotificationFragment;
import com.practice.freelancebd.Fragments.PostJobFragment;
import com.practice.freelancebd.Fragments.ProfileFragment;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity  {

    private BottomNavigationView bottomNavigationView;
    private TextView toolbarHomeTV;
    private CircleImageView loggedInUserImage;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private DatabaseReference databaseReference;
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

        getLoggedInUserImageFromDB();


        replaceFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.homeNavItem:
                        toolbarHomeTV.setText("Home");
                        replaceFragment(new HomeFragment());
                        return true;

                    case R.id.chatNavItem:
                        toolbarHomeTV.setText("Chat");
                        replaceFragment(new ChatFragment());
                        return true;

                    case R.id.notificationsNavItem:
                        toolbarHomeTV.setText("Notifications");
                        replaceFragment(new NotificationFragment());
                        return true;

                    case R.id.postJobNavItem:
                        toolbarHomeTV.setText("Post Job");
                        replaceFragment(new PostJobFragment());
                        return true;

                    case R.id.profileNavItem:
                        toolbarHomeTV.setText("Profile");
                        replaceFragment(new ProfileFragment());
                        return true;
                }


                return false;
            }
        });

    }

    private void getLoggedInUserImageFromDB() {

        DatabaseReference userRef = databaseReference.child("users").child(currentUser);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    if(dataSnapshot.hasChild("profile image")){

                        String image = dataSnapshot.child("profile image").getValue().toString();
                        Picasso.get().load(image).into(loggedInUserImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();


    }

    private void init() {

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        toolbarHomeTV = findViewById(R.id.toolbarHomeTV);
        loggedInUserImage = (CircleImageView)findViewById(R.id.loggedInUserImage);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
