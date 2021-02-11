package com.practice.freelancebd.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Activity.ChatActivity;
import com.practice.freelancebd.Custom.Conversation;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private RecyclerView chatListRV;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference databaseReference, userRef, messageRef, conversationRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseRecyclerAdapter<Conversation, ConversationViewHolder> firebaseRecyclerAdapter;



    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatListRV = view.findViewById(R.id.chatListRV);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        chatListRV.setHasFixedSize(true);
        chatListRV.setLayoutManager(linearLayoutManager);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userRef = databaseReference.child("users");
        messageRef = databaseReference.child("message").child(firebaseAuth.getCurrentUser().getUid());
        conversationRef = databaseReference.child("chat").child(firebaseAuth.getCurrentUser().getUid());

        Query query = conversationRef.orderByChild("timestamp");

        FirebaseRecyclerOptions<Conversation> options =
                new FirebaseRecyclerOptions.Builder<Conversation>()
                        .setQuery(query, Conversation.class)
                        .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Conversation,
                ConversationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ConversationViewHolder holder, int position, @NonNull Conversation model) {

                final String list_user_id = getRef(position).getKey();

                Query lastMsgQuery = messageRef.child(list_user_id).limitToLast(1);

                lastMsgQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        String data = snapshot.child("message").getValue().toString();
                        holder.last_message.setText(data);
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

                userRef.child(list_user_id).child("userProfile").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final String image = snapshot.child("profileImageLink").getValue().toString();
                        final String fullName = snapshot.child("fullName").getValue().toString();

                        holder.person_name.setText(fullName);
                        Picasso.get().load(image).into(holder.profile_image);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(),ChatActivity.class);
                                intent.putExtra("bidderId",list_user_id);
                                intent.putExtra("bidderName",fullName);
                                intent.putExtra("bidderImage",image);
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

            @NonNull
            @Override
            public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_list_preview, parent, false);
                return new ConversationViewHolder(view1);
            }
        };

        chatListRV.setAdapter(firebaseRecyclerAdapter);

        return view;
    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView profile_image;
        private TextView person_name;
        private TextView last_message;

        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profileImage);
            person_name = itemView.findViewById(R.id.person_name);
            last_message = itemView.findViewById(R.id.last_message);
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
