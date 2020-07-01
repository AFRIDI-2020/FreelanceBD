package com.practice.freelancebd.ModelClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class AllUserPostAdapter extends FirebaseRecyclerAdapter<AllUserPost, AllUserPostAdapter.AllUserPostViewHolder> {

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
    protected void onBindViewHolder(@NonNull AllUserPostViewHolder holder, int position, @NonNull AllUserPost model) {

        holder.userNameTextView.setText(model.getUserName());
        holder.dateTextView.setText(model.getDate());
        holder.timeTextView.setText(model.getTime());
        holder.jobTitleTextView.setText(model.getTitle());
        holder.budgetTextView.setText(model.getBudget());
        holder.durationTextView.setText(model.getDuration());
        holder.statusTextView.setText(model.getStatus());
        String imageLink = model.getProfileImage();
        Picasso.get().load(imageLink).into(holder.profileImageIV);

    }

    @NonNull
    @Override
    public AllUserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_posts_on_home_page_demo,parent,false);

        return new AllUserPostViewHolder(view);
    }

    class AllUserPostViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTextView, dateTextView,timeTextView,jobTitleTextView, budgetTextView, durationTextView, statusTextView;
        CircleImageView profileImageIV;

        public AllUserPostViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            budgetTextView = itemView.findViewById(R.id.budgetTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            profileImageIV = itemView.findViewById(R.id.profileImageIV);
        }
    }
}
