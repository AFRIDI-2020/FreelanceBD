package com.practice.freelancebd.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class AboutUserProfile extends AppCompatActivity {


    private CircleImageView profileImage;
    private TextView userFullNameTV, professionTV, userDetailsTV;
    private DatabaseReference databaseReference,userProfileRef;
    private FirebaseAuth firebaseAuth;
    String currentUser;
    private AlertDialog loadingAlertDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user_profile);
        init();
        setUpToolbar();
        startLoadingAlertDialog();
        getDataFromDatabase();
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.about_user_profile_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void startLoadingAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AboutUserProfile.this);
        View view = getLayoutInflater().inflate(R.layout.firebase_data_loading_progress_dialog,null);
        builder.setView(view);
        builder.setCancelable(false);
        loadingAlertDialog = builder.create();
        loadingAlertDialog.show();
    }

    private void getDataFromDatabase() {
        userProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String fullName = snapshot.child("fullName").getValue().toString();
                    userFullNameTV.setText(fullName);
                    String professionalTag = snapshot.child("professionalTag").getValue().toString();
                    professionTV.setText(professionalTag);
                    String aboutUser = snapshot.child("aboutUser").getValue().toString();
                    userDetailsTV.setText(aboutUser);
                    String profileImageLink = snapshot.child("profileImageLink").getValue().toString();
                    Picasso.get().load(profileImageLink).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        loadingAlertDialog.dismiss();
    }

    private void goTOEditProfile() {
        startActivity(new Intent(AboutUserProfile.this,EditAboutUserActivity.class));
    }


    private void init() {
        userFullNameTV = findViewById(R.id.tv_username);
        userDetailsTV = findViewById(R.id.tv_user_details);
        professionTV = findViewById(R.id.tv_profession);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userProfileRef = databaseReference.child("users").child(currentUser).child("userProfile");
        profileImage = findViewById(R.id.profile_image);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.edit_profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.edit_profile){
            startActivity(new Intent(this,EditAboutUserActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
