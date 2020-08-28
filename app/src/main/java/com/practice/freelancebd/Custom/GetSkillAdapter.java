package com.practice.freelancebd.Custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.practice.freelancebd.R;
import com.practice.freelancebd.ModelClasses.Skill;

public class GetSkillAdapter extends FirebaseRecyclerAdapter<Skill,GetSkillAdapter.GetSkillViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public GetSkillAdapter(@NonNull FirebaseRecyclerOptions<Skill> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GetSkillViewHolder holder, int position, @NonNull Skill model) {

        holder.xpTV.setText(model.getSk());
    }

    @NonNull
    @Override
    public GetSkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_experience_about_user_demo,parent,false);
        return new GetSkillViewHolder(view);
    }

    public class GetSkillViewHolder extends RecyclerView.ViewHolder {

        private TextView xpTV;
        public GetSkillViewHolder(@NonNull View itemView) {
            super(itemView);

            xpTV = itemView.findViewById(R.id.xpTV);
        }
    }
}
