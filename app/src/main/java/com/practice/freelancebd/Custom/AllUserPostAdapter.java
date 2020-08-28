package com.practice.freelancebd.Custom;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.practice.freelancebd.ModelClasses.AllUserPost;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    protected void onBindViewHolder(@NonNull final AllUserPostViewHolder holder, int position, @NonNull AllUserPost model) {
        holder.titleTextView.setText(model.getTitle());
        holder.budgetTextView.setText(model.getBudget());
        holder.usernameTextView.setText(model.getEmployerName());
        holder.postStatusTextView.setText(model.getPostStatus());
    }

    @NonNull
    @Override
    public AllUserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_posts_on_home_page_demo, parent, false);

        return new AllUserPostViewHolder(view);
    }

    class AllUserPostViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView, budgetTextView,usernameTextView,postStatusTextView;

        public AllUserPostViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            budgetTextView = itemView.findViewById(R.id.budgetTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            postStatusTextView = itemView.findViewById(R.id.postStatusTextView);
        }
    }
}
