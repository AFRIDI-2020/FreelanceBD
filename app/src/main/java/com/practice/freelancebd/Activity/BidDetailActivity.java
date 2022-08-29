package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Person;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.ModelClasses.Deal;
import com.practice.freelancebd.R;

public class BidDetailActivity extends AppCompatActivity {

    private String employerName, postTitle, bidderName, bidAmount, bidDay, bidder_id,bidderProfileImage,user_name,postKey;
    private TextView jobTitleTV, employerNameTV, bidderNameTV, demandedAmountTV, dayTV, seeProfileTV;
    private Button messageBtn ,dealButton;
    private AlertDialog dialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_detail);

        init();

        employerName = getIntent().getStringExtra("employer_name");
        postTitle = getIntent().getStringExtra("title");
        bidderName = getIntent().getStringExtra("bidder_name");
        bidAmount = getIntent().getStringExtra("amount");
        bidDay = getIntent().getStringExtra("bid_day");
        bidder_id = getIntent().getStringExtra("bidder_id");
        bidderProfileImage = getIntent().getStringExtra("bidderProfileImage");
        postKey = getIntent().getStringExtra("postKey");

        jobTitleTV.setText(postTitle);
        employerNameTV.setText(employerName);
        bidderNameTV.setText(bidderName);
        demandedAmountTV.setText(bidAmount);
        dayTV.setText(bidDay);

        seeProfileTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BidDetailActivity.this, PersonsActivity.class);
                intent.putExtra("bidderId",bidder_id);
                startActivity(intent);
            }
        });


        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BidDetailActivity.this, ChatActivity.class);
                intent.putExtra("bidderId",bidder_id);
                intent.putExtra("bidderName",bidderName);
                intent.putExtra("bidderImage",bidderProfileImage);
                startActivity(intent);
            }
        });


        dealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BidDetailActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Confirm this deal?");

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String currentUID = firebaseAuth.getCurrentUser().getUid();
                        databaseReference.child("users").child(currentUID).child("userProfile").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(snapshot.exists()){
                                    user_name = snapshot.child("fullName").getValue().toString();
                                    final DatabaseReference orderRef = databaseReference.child("orders");
                                    final Deal deal = new Deal(user_name,bidderName,postTitle,bidAmount,bidDay,postKey);
                                    orderRef.child(currentUID).child(postKey).setValue(deal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            orderRef.child(bidder_id).child(postKey).setValue(deal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(BidDetailActivity.this, "Deal confirm!", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(BidDetailActivity.this,Order.class));
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();

                dialog.show();
            }
        });
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        jobTitleTV = findViewById(R.id.job_title);
        employerNameTV = findViewById(R.id.tv_employerName);
        bidderNameTV = findViewById(R.id.tv_bidderName);
        demandedAmountTV = findViewById(R.id.tv_demanded_amount);
        dayTV = findViewById(R.id.tv_day);
        seeProfileTV = findViewById(R.id.tv_see_profile);
        messageBtn = findViewById(R.id.message_button);
        dealButton = findViewById(R.id.deal_button);
    }
}