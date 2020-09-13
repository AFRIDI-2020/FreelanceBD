package com.practice.freelancebd.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.freelancebd.Custom.AllMyPostAdapter;
import com.practice.freelancebd.ModelClasses.AllMyClass;
import com.practice.freelancebd.R;

public class MyPostActivity extends AppCompatActivity {

    private RecyclerView myPostRecyclerView;
    private DatabaseReference databaseReference, getPostsRef;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private AllMyPostAdapter allMyPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        init();
        setRecyclerView();


    }

    private void setRecyclerView() {
        myPostRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        myPostRecyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<AllMyClass> options =
                new FirebaseRecyclerOptions.Builder<AllMyClass>()
                        .setQuery(getPostsRef, AllMyClass.class)
                        .build();

        allMyPostAdapter = new AllMyPostAdapter(options);
        myPostRecyclerView.setAdapter(allMyPostAdapter);
    }

    private void init() {
        myPostRecyclerView = findViewById(R.id.myPostRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        getPostsRef = databaseReference.child("users").child(currentUser).child("postedJobs");
    }


    @Override
    protected void onStart() {
        super.onStart();

        allMyPostAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        allMyPostAdapter.stopListening();
    }
}
