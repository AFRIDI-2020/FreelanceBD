package com.practice.freelancebd.Custom;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Activity.ClickPostActivity;
import com.practice.freelancebd.Activity.HomeActivity;
import com.practice.freelancebd.Fragments.HomeFragment;
import com.practice.freelancebd.ModelClasses.AllUserPost;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class AllUserPostAdapter extends FirebaseRecyclerAdapter<AllUserPost, AllUserPostAdapter.AllUserPostViewHolder> {


    private DatabaseReference databaseReference;
    private int bidNumber;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AllUserPostAdapter(@NonNull FirebaseRecyclerOptions<AllUserPost> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AllUserPostViewHolder holder, int position, @NonNull AllUserPost model) {


        bidNumber = 0;
        final String postKey = getRef(position).getKey();

        holder.titleTextView.setText(model.getTitle());
        holder.budgetTextView.setText(model.getBudget());
        holder.usernameTextView.setText(model.getEmployerName());
        holder.postStatusTextView.setText(model.getPostStatus());
        holder.currentBidStatus(postKey);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ClickPostActivity.class);
                intent.putExtra("postKey", postKey);
                v.getContext().startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public AllUserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_posts_on_home_page_demo, parent, false);

        return new AllUserPostViewHolder(view);
    }

    class AllUserPostViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView, budgetTextView, usernameTextView, postStatusTextView,bidNumberTextView,bidOrbidsTextView;


        public AllUserPostViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            budgetTextView = itemView.findViewById(R.id.budgetTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            postStatusTextView = itemView.findViewById(R.id.postStatusTextView);
            bidNumberTextView = itemView.findViewById(R.id.bidNumberTextView);
            bidOrbidsTextView = itemView.findViewById(R.id.bidOrBidsTextView);
        }

        public void currentBidStatus(final String postKey) {

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Bids").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.child(postKey).exists()){
                        bidNumber = (int)dataSnapshot.child(postKey).getChildrenCount();
                        bidNumberTextView.setText(Integer.toString(bidNumber));
                        if(bidNumber>1){
                            bidOrbidsTextView.setText(R.string.bids);
                        }
                        else {
                            bidOrbidsTextView.setText(R.string.bid);
                        }

                    }
                    else {
                        bidNumberTextView.setText("0");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
