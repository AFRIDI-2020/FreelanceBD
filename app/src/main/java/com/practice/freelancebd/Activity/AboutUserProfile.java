package com.practice.freelancebd.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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


    private ImageView editIV,profileImage,backImageView;
    private TextView fullNameTV, professionalTagTV, aboutUserTV;
    private DatabaseReference databaseReference,userProfileRef;
    private FirebaseAuth firebaseAuth;
    String currentUser;
    private AlertDialog loadingAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user_profile);



        init();
        startLoadingAlertDialog();
        getDataFromDatabase();
        editIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTOEditProfile();
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUserProfile.this,HomeActivity.class));
            }
        });


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
                    fullNameTV.setText(fullName);
                    String professionalTag = snapshot.child("professionalTag").getValue().toString();
                    professionalTagTV.setText(professionalTag);
                    String aboutUser = snapshot.child("aboutUser").getValue().toString();
                    aboutUserTV.setText(aboutUser);
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

        editIV = findViewById(R.id.editIV);
        fullNameTV = findViewById(R.id.fullNameTV);
        professionalTagTV = findViewById(R.id.professionalTagTV);
        aboutUserTV = findViewById(R.id.aboutUserTV);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userProfileRef = databaseReference.child("users").child(currentUser).child("userProfile");
        profileImage = findViewById(R.id.profileImage);
        backImageView = findViewById(R.id.backImageView);
    }

    @Override
    public void onBackPressed() {

    }
}
