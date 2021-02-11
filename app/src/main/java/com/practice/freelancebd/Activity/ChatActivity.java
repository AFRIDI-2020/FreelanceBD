package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Custom.MessageAdapter;
import com.practice.freelancebd.ModelClasses.Message;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private TextView bidderNameTV;
    private CircleImageView bidderProfileImage;
    private DatabaseReference rootRef;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private ImageView sendIcon;
    private EditText et_msg;
    private RecyclerView messageRecyclerView;
    private List<Message>messageList = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();

        final String bidder_id = getIntent().getStringExtra("bidderId");
        String bidder_name = getIntent().getStringExtra("bidderName");
        String bidder_image = getIntent().getStringExtra("bidderImage");
        Picasso.get().load(bidder_image).into(bidderProfileImage);
        bidderNameTV.setText(bidder_name);
        Picasso.get().load(bidder_image).into(bidderProfileImage);
        rootRef = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        messageAdapter = new MessageAdapter(messageList);
        messageRecyclerView = findViewById(R.id.messageRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        messageRecyclerView.setHasFixedSize(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);

        messageRecyclerView.setAdapter(messageAdapter);

        loadMessages(bidder_id);


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
        
        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(bidder_id);
            }
        });



    }

    private void loadMessages(String bidder_id) {

        DatabaseReference messageRef = rootRef.child("message").child(currentUserId).child(bidder_id);


        messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Message message = snapshot.getValue(Message.class);

                messageList.add(message);
                messageAdapter.notifyDataSetChanged();

                messageRecyclerView.scrollToPosition(messageList.size() - 1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String bidder_id) {
        String message = et_msg.getText().toString();
        if(!message.isEmpty()){
            String current_user_ref = "message/" + currentUserId + "/" + bidder_id;
            String bidder_ref = "message/" + bidder_id + "/" + currentUserId;

            DatabaseReference user_msg_push = rootRef.child("message")
                    .child(currentUserId).child(bidder_id).push();

            String push_key = user_msg_push.getKey();

            Map messageMap = new HashMap();
            messageMap.put("message",message);
            messageMap.put("seen",false);
            messageMap.put("type","text");
            messageMap.put("time",ServerValue.TIMESTAMP);
            messageMap.put("from",currentUserId);

            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref + "/" + push_key,messageMap);
            messageUserMap.put(bidder_ref + "/" + push_key, messageMap);

            et_msg.setText("");

            rootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if(error != null){
                        Log.d("CHAT_LOG",error.getMessage());
                    }
                }
            });
        }
    }

    private void init() {
        bidderNameTV = findViewById(R.id.tv_bidder_name);
        bidderProfileImage = findViewById(R.id.person_image);
        sendIcon = findViewById(R.id.send_icon);
        et_msg = findViewById(R.id.et_msg);
    }
}