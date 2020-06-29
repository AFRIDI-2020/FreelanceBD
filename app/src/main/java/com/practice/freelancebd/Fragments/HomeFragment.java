package com.practice.freelancebd.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.practice.freelancebd.ModelClasses.AllUserPost;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recentPostRV;
    private DatabaseReference databaseReference, postRef;
    private Query postsQuery;
    private FirebaseRecyclerAdapter<AllUserPost, PostsViewHolder>firebaseRecyclerAdapter;


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
        postsQuery = postRef.orderByKey();

        recentPostRV = view.findViewById(R.id.recentPostRV);
        recentPostRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recentPostRV.setLayoutManager(linearLayoutManager);

        displayAllUsersPosts();


        return view;
    }

    private void displayAllUsersPosts() {

        FirebaseRecyclerOptions firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<AllUserPost>()
                .setQuery(postsQuery, AllUserPost.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AllUserPost, PostsViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull AllUserPost model) {



                holder.userNameTextView.setText(model.getFullName());
                holder.dateTextView.setText(model.getDate());
                holder.timeTextView.setText(model.getTime());
                holder.jobTitleTextView.setText(model.getTitle());
                holder.budgetTextView.setText(model.getBudget());
                holder.durationTextView.setText(model.getDuration());
                holder.statusTextView.setText(model.getStatus());

            }

            @NonNull
            @Override
            public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_posts_on_home_page_demo,parent,false);
                return new PostsViewHolder(view);
            }
        };

        recentPostRV.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {

        View mview;
        private TextView userNameTextView,dateTextView,timeTextView, jobTitleTextView, budgetTextView,durationTextView, statusTextView;
        private CircleImageView profieImageIV;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setFullName(String fullName){
            userNameTextView = mview.findViewById(R.id.userNameTextView);
            userNameTextView.setText(fullName);
        }

        public void setDate(String date){
            dateTextView = mview.findViewById(R.id.dateTextView);
            dateTextView.setText(date);
        }

        public void setTime(String time){
            timeTextView = mview.findViewById(R.id.timeTextView);
            timeTextView.setText(time);
        }

        public void setTitle(String title){
            jobTitleTextView = mview.findViewById(R.id.jobTitleTextView);
            jobTitleTextView.setText(title);
        }

        public void setBudget(String budget){
            budgetTextView = mview.findViewById(R.id.budgetTextView);
            budgetTextView.setText(budget);
        }

        public void setDuration(String duration){
            durationTextView = mview.findViewById(R.id.durationTextView);
            durationTextView.setText(duration);
        }

        public void setStatus(String status){
            statusTextView = mview.findViewById(R.id.statusTextView);
            statusTextView.setText(status);
        }

        public void setProfileImage(String profileImage){
            profieImageIV = mview.findViewById(R.id.profileImageIV);
        }


    }



}
