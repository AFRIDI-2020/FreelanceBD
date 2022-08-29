package com.practice.freelancebd.Fragments;


import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Activity.BidDetailActivity;
import com.practice.freelancebd.ModelClasses.Bid;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private RecyclerView notificationRV;
    private LinearLayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseRecyclerAdapter<Bid,NotificationViewHolder> firebaseRecyclerAdapter;
    private String finalDay;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_notification, container, false);
        notificationRV = view.findViewById(R.id.notification_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        notificationRV.setLayoutManager(layoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("notification").child(firebaseAuth.getCurrentUser().getUid());

        FirebaseRecyclerOptions<Bid> options =
                new FirebaseRecyclerOptions.Builder<Bid>()
                        .setQuery(query, Bid.class)
                        .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Bid, NotificationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final NotificationViewHolder holder, int position, @NonNull Bid model) {

                String user_key = getRef(position).getKey();

                final String bidderProfileImage = model.getBidderProfileImageLink();
                final String bidderName = model.getBidder();
                final String bidAmount = model.getBidAmount();
                final String bidDay = model.getBidDay();
                final String bidder_id = model.getBidderId();
                int day = Integer.parseInt(bidDay);
                if(day>1){
                    finalDay = "days";
                }
                else {
                    finalDay = "day";
                }
                final String postKey = model.getPostKey();

                Picasso.get().load(bidderProfileImage).into(holder.bidderProfileImage);
                FirebaseDatabase.getInstance().getReference().child("posts").child(postKey)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    final String title = snapshot.child("title").getValue().toString();
                                    final String employer_name = snapshot.child("employerName").getValue().toString();
                                    String notification_text =
                                            bidderName + " is interested in your job post- " + title + "- for "+ bidAmount +"Tk. in "
                                                    + bidDay +" " + finalDay;

                                    holder.notification_tv.setText(notification_text);

                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getContext(), BidDetailActivity.class);
                                            intent.putExtra("title",title);
                                            intent.putExtra("employer_name",employer_name);
                                            intent.putExtra("bidder_name",bidderName);
                                            intent.putExtra("amount",bidAmount);
                                            intent.putExtra("bid_day",bidDay);
                                            intent.putExtra("bidder_id",bidder_id);
                                            intent.putExtra("bidderProfileImage",bidderProfileImage);
                                            intent.putExtra("postKey",postKey);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }

            @NonNull
            @Override
            public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.bid_notification,parent,false);
                return new NotificationViewHolder(view1);
            }
        };

        notificationRV.setAdapter(firebaseRecyclerAdapter);

        return view;
    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder{

        private TextView notification_tv;
        private CircleImageView bidderProfileImage;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            notification_tv = itemView.findViewById(R.id.notification_tv);
            bidderProfileImage = itemView.findViewById(R.id.bidderProfileImage);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        firebaseRecyclerAdapter.startListening();
    }
}
