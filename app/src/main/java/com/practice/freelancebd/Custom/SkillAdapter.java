package com.practice.freelancebd.Custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.freelancebd.ModelClasses.Skill;
import com.practice.freelancebd.R;

public class SkillAdapter extends FirebaseRecyclerAdapter<Skill, SkillAdapter.SkillViewHolder> {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SkillAdapter(@NonNull FirebaseRecyclerOptions<Skill> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SkillViewHolder holder, final int position, @NonNull Skill model) {

        holder.experienceTV.setText(model.getSk());
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = getRef(position).getKey();

                databaseReference.child("users").child(currentUser).child("about").child("skill").child(key)
                        .removeValue();
            }
        });
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_experience_edit_profile_demo,parent,false);
        return new SkillViewHolder(view);
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder {

        TextView experienceTV;
        ImageView deleteIcon;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);

            experienceTV = itemView.findViewById(R.id.experienceTV);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }
}
