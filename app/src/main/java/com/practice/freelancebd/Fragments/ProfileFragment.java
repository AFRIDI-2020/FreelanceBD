package com.practice.freelancebd.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Activity.AboutUserProfile;
import com.practice.freelancebd.Activity.EditAboutUserActivity;
import com.practice.freelancebd.Activity.MyPostActivity;
import com.practice.freelancebd.Activity.Order;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ConstraintLayout myPostedJobLayout;
    private CircleImageView profileImage;
    private TextView userFullNameTV, professionTV, userDetailsTV,taken_jobs_tv;
    private DatabaseReference databaseReference,userProfileRef;
    private FirebaseAuth firebaseAuth;
    private ImageView editIcon;
    private ConstraintLayout job_taken_layout;
    String currentUser;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        myPostedJobLayout = view.findViewById(R.id.myPostsLayout);
        userFullNameTV = view.findViewById(R.id.tv_username);
        job_taken_layout = view.findViewById(R.id.job_taken_layout);
        userDetailsTV = view.findViewById(R.id.tv_user_details);
        professionTV = view.findViewById(R.id.tv_profession);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userProfileRef = databaseReference.child("users").child(currentUser).child("userProfile");
        profileImage = view.findViewById(R.id.profile_image);
        editIcon =view.findViewById(R.id.edit_icon);
        taken_jobs_tv = view.findViewById(R.id.taken_jobs);

        getDataFromDatabase();
        myPostedJobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MyPostActivity.class));
            }
        });

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditAboutUserActivity.class));
            }
        });

        job_taken_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), Order.class));
            }
        });

        return view;
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

    }
}
