package com.practice.freelancebd.Activity;
import androidx.annotation.NonNull;
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
import com.practice.freelancebd.Fragments.ProfileFragment;
import com.practice.freelancebd.GetExperienceAdapter;
import com.practice.freelancebd.GetQualificationAdapter;
import com.practice.freelancebd.ModelClasses.Experience;
import com.practice.freelancebd.Qualification;
import com.practice.freelancebd.R;
import com.practice.freelancebd.Skill;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class AboutUserProfile extends AppCompatActivity {

    private TextView editTextView,nameTV,professionalTagTV,descriptionTV, emailTV, mobileTV;
    private CircleImageView circleImageView;
    private ImageView backImageView;
    private DatabaseReference databaseReference,experienceRef, qualificationRef, skillRef;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private RecyclerView showExperienceRV, showQualificationRV, showSkillRV;
    private GetExperienceAdapter getExperienceAdapter;
    private GetQualificationAdapter getQualificationAdapter;
    private GetSkillAdapter getSkillAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user_profile);


        init();

        getDataFromDataBase();

        editTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AboutUserProfile.this, EditAboutUserActivity.class));
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUserProfile.this,HomeActivity.class));
            }
        });

    }

    private void getDataFromDataBase() {

        databaseReference.child("users").child(currentUser).child("about").child("personalInfo")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            String name = dataSnapshot.child("name").getValue().toString();
                            String email = dataSnapshot.child("email").getValue().toString();
                            String description = dataSnapshot.child("description").getValue().toString();
                            String mobileNo = dataSnapshot.child("mobileNo").getValue().toString();
                            String professionalTag = dataSnapshot.child("professionalTag").getValue().toString();


                            nameTV.setText(name);
                            emailTV.setText(email);
                            descriptionTV.setText(description);
                            mobileTV.setText(mobileNo);
                            professionalTagTV.setText(professionalTag);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        databaseReference.child("users").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    if(dataSnapshot.hasChild("profileImageLink")){
                        String image = dataSnapshot.child("profileImageLink").getValue().toString();
                        Picasso.get().load(image).into(circleImageView);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getExperience();

        getQualification();

        getSkill();

    }

    private void getSkill() {

        showSkillRV.setLayoutManager(new LinearLayoutManager(AboutUserProfile.this));
        skillRef = databaseReference.child("users").child(currentUser).child("about").child("skill");

        FirebaseRecyclerOptions<Skill> option =
                new FirebaseRecyclerOptions.Builder<Skill>()
                        .setQuery(skillRef, Skill.class)
                        .build();

        getSkillAdapter = new GetSkillAdapter(option);

        showSkillRV.setAdapter(getSkillAdapter);
    }

    private void getQualification() {

        showQualificationRV.setLayoutManager(new LinearLayoutManager(AboutUserProfile.this));
        qualificationRef = databaseReference.child("users").child(currentUser).child("about").child("qualification");

        FirebaseRecyclerOptions<Qualification> option =
                new FirebaseRecyclerOptions.Builder<Qualification>()
                        .setQuery(qualificationRef, Qualification.class)
                        .build();

        getQualificationAdapter = new GetQualificationAdapter(option);
        showQualificationRV.setAdapter(getQualificationAdapter);
    }

    private void getExperience() {

        showExperienceRV.setLayoutManager(new LinearLayoutManager(AboutUserProfile.this));
        experienceRef = databaseReference.child("users").child(currentUser).child("about").child("experience");

        FirebaseRecyclerOptions<Experience> option =
                new FirebaseRecyclerOptions.Builder<Experience>()
                        .setQuery(experienceRef, Experience.class)
                        .build();

        getExperienceAdapter = new GetExperienceAdapter(option);
        showExperienceRV.setAdapter(getExperienceAdapter);

        getExperienceAdapter.notifyDataSetChanged();
    }

    private void init() {

        editTextView = findViewById(R.id.editTextView);
        nameTV = findViewById(R.id.nameTV);
        professionalTagTV = findViewById(R.id.professionalTagTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        emailTV = findViewById(R.id.emailTV);
        mobileTV = findViewById(R.id.mobileTV);
        circleImageView = findViewById(R.id.circleImageView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        showExperienceRV = findViewById(R.id.showExperienceRV);
        showQualificationRV = findViewById(R.id.showQualificationRV);
        showSkillRV = findViewById(R.id.showSkillRV);
        backImageView = findViewById(R.id.backImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getExperienceAdapter.startListening();
        getQualificationAdapter.startListening();
        getSkillAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        getExperienceAdapter.stopListening();
        getQualificationAdapter.stopListening();
        getSkillAdapter.stopListening();
    }
}
