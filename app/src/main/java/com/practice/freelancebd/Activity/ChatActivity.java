package com.practice.freelancebd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private TextView bidderNameTV;
    private CircleImageView bidderProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();

        String bidder_id = getIntent().getStringExtra("bidderId");
        String bidder_name = getIntent().getStringExtra("bidderName");
        String bidder_image = getIntent().getStringExtra("bidderImage");
        bidderNameTV.setText(bidder_name);
        Picasso.get().load(bidder_image).into(bidderProfileImage);

    }

    private void init() {
        bidderNameTV = findViewById(R.id.tv_bidder_name);
        bidderProfileImage = findViewById(R.id.person_image);
    }
}