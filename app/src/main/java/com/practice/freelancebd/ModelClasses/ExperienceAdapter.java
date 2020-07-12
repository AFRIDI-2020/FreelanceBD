package com.practice.freelancebd.ModelClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.freelancebd.R;

public class ExperienceAdapter extends FirebaseRecyclerAdapter<Experience, ExperienceAdapter.ExperienceViewHolder> {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ExperienceAdapter(@NonNull FirebaseRecyclerOptions<Experience> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ExperienceViewHolder holder, final int position, @NonNull final Experience model) {


        holder.experienceTV.setText(model.getXp());
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String key = getRef(position).getKey();

                databaseReference.child("users").child(currentUser).child("about").child("experience").child(key)
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            notifyItemRemoved(position);
                        }
                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_experience_edit_profile_demo,parent,false);

        return new ExperienceViewHolder(view);
    }

    public class ExperienceViewHolder extends RecyclerView.ViewHolder {

        TextView experienceTV;
        ImageView deleteIcon;

        public ExperienceViewHolder(@NonNull View itemView) {
            super(itemView);

            experienceTV = itemView.findViewById(R.id.experienceTV);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }
}
