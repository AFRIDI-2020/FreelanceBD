package com.practice.freelancebd.Custom;

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

public class PersonsPostAdapter extends FirebaseRecyclerAdapter<AllUserPost,PersonsPostAdapter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PersonsPostAdapter(@NonNull FirebaseRecyclerOptions<AllUserPost> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull AllUserPost model) {

        holder.titleTextView.setText(model.getTitle());
        holder.postStatusTextView.setText(model.getPostStatus());
        holder.usernameTextView.setText(model.getEmployerName());
        holder.budgeTextView.setText(model.getBudget());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_posts_on_home_page_demo,parent,false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView, usernameTextView, budgeTextView, postStatusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            budgeTextView = itemView.findViewById(R.id.budgetTextView);
            postStatusTextView = itemView.findViewById(R.id.postStatusTextView);
        }
    }
}
