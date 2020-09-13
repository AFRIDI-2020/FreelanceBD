package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClickPostActivity extends AppCompatActivity {

    String postKey,title,type,employerName,description,budget,day,month,year,profileImageLink;
    DatabaseReference databaseReference;
    private TextView userNameTV,jobyTypeTV,titleTV, budgetTV, dayTV, monthTV, yearTV, descriptionTV;
    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);


        init();

        postKey = getIntent().getExtras().get("postKey").toString();

        databaseReference.child("posts").child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                title = dataSnapshot.child("title").getValue().toString();
                type = dataSnapshot.child("type").getValue().toString();
                employerName = dataSnapshot.child("employerName").getValue().toString();
                description = dataSnapshot.child("description").getValue().toString();
                budget = dataSnapshot.child("budget").getValue().toString();
                day = dataSnapshot.child("applyLastDay").getValue().toString();
                month = dataSnapshot.child("applyLastMonth").getValue().toString();
                year = dataSnapshot.child("applyLastYear").getValue().toString();
                profileImageLink = dataSnapshot.child("profileImageLink").getValue().toString();

                titleTV.setText(title);
                jobyTypeTV.setText(type);
                userNameTV.setText(employerName);
                descriptionTV.setText(description);
                budgetTV.setText(budget);
                dayTV.setText(day);
                monthTV.setText(month);
                yearTV.setText(year);

                Picasso.get().load(profileImageLink).into(circleImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userNameTV = findViewById(R.id.userNameTV);
        jobyTypeTV = findViewById(R.id.jobTypeTV);
        titleTV = findViewById(R.id.titleTV);
        budgetTV = findViewById(R.id.budgetTV);
        dayTV = findViewById(R.id.dayTV);
        monthTV = findViewById(R.id.monthTV);
        yearTV=findViewById(R.id.yearTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        circleImageView = findViewById(R.id.circleProfileImage);
    }
}
