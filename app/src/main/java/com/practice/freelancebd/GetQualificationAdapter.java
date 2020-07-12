package com.practice.freelancebd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class GetQualificationAdapter extends FirebaseRecyclerAdapter<Qualification, GetQualificationAdapter.GetQualificationViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public GetQualificationAdapter(@NonNull FirebaseRecyclerOptions<Qualification> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GetQualificationViewHolder holder, int position, @NonNull Qualification model) {

        holder.xpTV.setText(model.getQua());
    }

    @NonNull
    @Override
    public GetQualificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_experience_about_user_demo,parent,false);
        return new GetQualificationViewHolder(view);
    }

    public class GetQualificationViewHolder extends RecyclerView.ViewHolder {

        private TextView xpTV;
        public GetQualificationViewHolder(@NonNull View itemView) {
            super(itemView);

            xpTV = itemView.findViewById(R.id.xpTV);
        }
    }
}
