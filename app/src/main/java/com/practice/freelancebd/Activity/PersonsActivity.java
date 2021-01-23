package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Custom.PersonsPostAdapter;
import com.practice.freelancebd.ModelClasses.AllUserPost;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

public class PersonsActivity extends AppCompatActivity {

    private String bidderId;
    private ImageView profileImage, backImageView;
    private TextView personFullNameTV, aboutPersonTV,professionalTagTV,numberOfPostTV;
    private String profileImageLink;
    private DatabaseReference databaseReference;
    private LinearLayout personPostsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons);

        bidderId = getIntent().getStringExtra("bidderId");
        init();
        viewPersonalInfo();
        viewNumberOfPostedJob();
        personPostsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonsActivity.this);
                View view = getLayoutInflater().inflate(R.layout.view_persons_post_layout,null);
                RecyclerView personsPostListRV = view.findViewById(R.id.personPostsListRV);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PersonsActivity.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                personsPostListRV.setLayoutManager(linearLayoutManager);

                Query query = databaseReference.child("users").child(bidderId).child("postedJobs");

                FirebaseRecyclerOptions<AllUserPost> options
                        = new FirebaseRecyclerOptions.Builder<AllUserPost>()
                        .setQuery(query,AllUserPost.class)
                        .build();

                PersonsPostAdapter personsPostAdapter = new PersonsPostAdapter(options);
                personsPostAdapter.startListening();
                personsPostListRV.setAdapter(personsPostAdapter);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void viewNumberOfPostedJob() {
        databaseReference.child("users").child(bidderId).child("postedJobs")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int numberOfPost = (int)snapshot.getChildrenCount();
                            numberOfPostTV.setText(Integer.toString(numberOfPost));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void viewPersonalInfo() {
        databaseReference.child("users").child(bidderId).child("userProfile")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String fullName = snapshot.child("fullName").getValue().toString();
                            personFullNameTV.setText(fullName);
                            String aboutUser = snapshot.child("aboutUser").getValue().toString();
                            aboutPersonTV.setText(aboutUser);
                            String professionalTag = snapshot.child("professionalTag").getValue().toString();
                            professionalTagTV.setText(professionalTag);
                            profileImageLink = snapshot.child("profileImageLink").getValue().toString();
                            Picasso.get().load(profileImageLink).into(profileImage);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    private void init() {
        profileImage = findViewById(R.id.profileImage);
        backImageView = findViewById(R.id.backImageView);
        personFullNameTV = findViewById(R.id.personFullNameTV);
        aboutPersonTV = findViewById(R.id.aboutPersonTV);
        professionalTagTV = findViewById(R.id.professionalTagTV);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        numberOfPostTV = findViewById(R.id.numberOfPostTV);
        personPostsLayout = findViewById(R.id.personPostsLayout);
    }
}
