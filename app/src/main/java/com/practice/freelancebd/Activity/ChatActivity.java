package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private TextView bidderNameTV;
    private CircleImageView bidderProfileImage;
    private DatabaseReference rootRef;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();

        final String bidder_id = getIntent().getStringExtra("bidderId");
        String bidder_name = getIntent().getStringExtra("bidderName");
        String bidder_image = getIntent().getStringExtra("bidderImage");
        bidderNameTV.setText(bidder_name);
        Picasso.get().load(bidder_image).into(bidderProfileImage);
        rootRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        rootRef.child("chat").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.hasChild(bidder_id)){
                    Map chatAddMap = new HashMap();
                    chatAddMap.put("seen",false);
                    chatAddMap.put("timestamp", ServerValue.TIMESTAMP);

                    Map chatUserMap = new HashMap();
                    chatUserMap.put("chat/" + currentUserId + "/" + bidder_id, chatAddMap);
                    chatUserMap.put("chat/" + bidder_id + "/" + currentUserId, chatAddMap);

                    rootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if(error != null){
                                Log.d("CHAT_LOG",error.getMessage());
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void init() {
        bidderNameTV = findViewById(R.id.tv_bidder_name);
        bidderProfileImage = findViewById(R.id.person_image);
    }
}