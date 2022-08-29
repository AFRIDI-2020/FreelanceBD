package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Bidder;
import com.practice.freelancebd.BidderListAdapter;
import com.practice.freelancebd.ModelClasses.Bid;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClickPostActivity extends AppCompatActivity {

    String postKey, title, type, employerName, description, budget, day, month, year, profileImageLink;
    DatabaseReference databaseReference, bidRef, currentPostRef;
    private FirebaseAuth firebaseAuth;
    private TextView userNameTV, jobyTypeTV, titleTV, budgetTV, dayTV, monthTV, yearTV, descriptionTV, numberOfProposals, seeProposalsTV;
    private CircleImageView circleProfileImage;
    private String currentUser, userID, bidder, bidderProfileImageLink,bidderId;
    private ImageView backImageView;
    private LinearLayout bidLayout;
    boolean bidStatus = false;
    int numberOfBid;
    private BidderListAdapter bidderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);


        init();
        bidRef = databaseReference.child("Bids");

        postKey = getIntent().getExtras().get("postKey").toString();


        currentPostRef = databaseReference.child("Bids").child(postKey);

        getNumberOfBids(postKey);

        databaseReference.child("posts").child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    title = dataSnapshot.child("title").getValue().toString();
                    type = dataSnapshot.child("type").getValue().toString();
                    employerName = dataSnapshot.child("employerName").getValue().toString();
                    description = dataSnapshot.child("description").getValue().toString();
                    budget = dataSnapshot.child("budget").getValue().toString();
                    day = dataSnapshot.child("applyLastDay").getValue().toString();
                    month = dataSnapshot.child("applyLastMonth").getValue().toString();
                    year = dataSnapshot.child("applyLastYear").getValue().toString();
                    profileImageLink = dataSnapshot.child("profileImageLink").getValue().toString();
                    userID = dataSnapshot.child("userID").getValue().toString();

                }

                if (userID.equals(currentUser)) {
                    bidLayout.setVisibility(View.INVISIBLE);
                }


                titleTV.setText(title);
                jobyTypeTV.setText(type);
                userNameTV.setText(employerName);
                descriptionTV.setText(description);
                budgetTV.setText(budget);
                dayTV.setText(day);
                monthTV.setText(month);
                yearTV.setText(year);

                Picasso.get().load(profileImageLink).into(circleProfileImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        getCurrentUsername();
        getCurrentUserProfileImageLink();


        bidLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ClickPostActivity.this);
                View view = getLayoutInflater().inflate(R.layout.make_a_bid_layout, null);

                final EditText bidAmountET = view.findViewById(R.id.bidAmountET);
                final EditText bidDayET = view.findViewById(R.id.bidDayET);
                final EditText bidDescriptionET = view.findViewById(R.id.bidDescriptionET);
                TextView cancelTV = view.findViewById(R.id.cancelTV);
                TextView bidTV = view.findViewById(R.id.bidTV);

                builder.setView(view);
                builder.setCancelable(false);
                final AlertDialog dialog = builder.create();

                dialog.show();
                cancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                bidRef.child(postKey).child(currentUser).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            bidAmountET.setText(dataSnapshot.child("bidAmount").getValue().toString());
                            bidDayET.setText(dataSnapshot.child("bidDay").getValue().toString());
                            bidDescriptionET.setText(dataSnapshot.child("bidDescription").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                bidTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bidStatus = true;

                        String bidAmount = bidAmountET.getText().toString();
                        String bidDay = bidDayET.getText().toString();
                        String bidDescription = bidDescriptionET.getText().toString();

                        final Bid bid = new Bid(bidAmount, bidDay, bidDescription,"true",bidder,bidderProfileImageLink, bidderId, postKey );

                        bidRef.child(postKey).child(currentUser).setValue(bid).addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            databaseReference.child("notification").child(userID).push().setValue(bid)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(ClickPostActivity.this, "You made a bid on this post", Toast.LENGTH_SHORT).show();
                                                                dialog.dismiss();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                }
                        );



                    }
                });


            }
        });

        seeProposalsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClickPostActivity.this);
                View view = getLayoutInflater().inflate(R.layout.bidders_list, null);
                RecyclerView bidderListRV = view.findViewById(R.id.bidderList);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                bidderListRV.setLayoutManager(new LinearLayoutManager(ClickPostActivity.this));

                FirebaseRecyclerOptions<Bidder> options =
                        new FirebaseRecyclerOptions.Builder<Bidder>()
                                .setQuery(currentPostRef, Bidder.class)
                                .build();

                bidderListAdapter = new BidderListAdapter(options);
                bidderListRV.setAdapter(bidderListAdapter);
                bidderListAdapter.startListening();


            }
        });


        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClickPostActivity.this, HomeActivity.class));
            }
        });


//mainFunction
    }


    private void getNumberOfBids(final String postKey) {

        numberOfBid = 0;

        bidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(postKey).exists()) {
                    numberOfBid = (int) dataSnapshot.child(postKey).getChildrenCount();
                    numberOfProposals.setText(Integer.toString(numberOfBid));
                } else {
                    numberOfProposals.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCurrentUserProfileImageLink() {

        databaseReference.child("users").child(currentUser).child("userProfile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    bidderProfileImageLink = dataSnapshot.child("profileImageLink").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCurrentUsername() {
        databaseReference.child("users").child(currentUser).child("userProfile")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            bidder = dataSnapshot.child("fullName").getValue().toString();
                        }
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
        yearTV = findViewById(R.id.yearTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        circleProfileImage = findViewById(R.id.circularProfileImage);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        bidLayout = findViewById(R.id.bidLayout);
        backImageView = findViewById(R.id.backImageView);
        numberOfProposals = findViewById(R.id.numberOfProposals);
        seeProposalsTV = findViewById(R.id.seeProposalsTV);
        bidderId = currentUser;
    }


    @Override
    public void onBackPressed() {
    }


}