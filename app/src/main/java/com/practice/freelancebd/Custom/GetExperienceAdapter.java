package com.practice.freelancebd.Custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.practice.freelancebd.ModelClasses.Experience;
import com.practice.freelancebd.R;

public class GetExperienceAdapter extends FirebaseRecyclerAdapter<Experience,GetExperienceAdapter.getExperienceViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public GetExperienceAdapter(@NonNull FirebaseRecyclerOptions<Experience> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull getExperienceViewHolder holder, int position, @NonNull Experience model) {

        holder.xpTV.setText(model.getXp());

    }

    @NonNull
    @Override
    public getExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_experience_about_user_demo,parent,false);
        return new getExperienceViewHolder(view);
    }

    public class getExperienceViewHolder extends RecyclerView.ViewHolder {

        private TextView xpTV;

        public getExperienceViewHolder(@NonNull View itemView) {
            super(itemView);

            xpTV = itemView.findViewById(R.id.xpTV);
        }
    }
}
