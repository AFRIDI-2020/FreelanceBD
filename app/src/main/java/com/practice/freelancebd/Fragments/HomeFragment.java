package com.practice.freelancebd.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.ModelClasses.AllUserPost;
import com.practice.freelancebd.Custom.AllUserPostAdapter;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recentPostRV;
    private DatabaseReference databaseReference, postRef;
    private AllUserPostAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private String currentUser;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        postRef = databaseReference.child("posts");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();




        recentPostRV = view.findViewById(R.id.recentPostRV);
        recentPostRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recentPostRV.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<AllUserPost> options =
                new FirebaseRecyclerOptions.Builder<AllUserPost>()
                        .setQuery(postRef, AllUserPost.class)
                        .build();

        adapter = new AllUserPostAdapter(options);
        recentPostRV.setAdapter(adapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
