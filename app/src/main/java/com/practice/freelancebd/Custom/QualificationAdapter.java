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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.freelancebd.ModelClasses.Qualification;
import com.practice.freelancebd.R;

public class QualificationAdapter extends FirebaseRecyclerAdapter<Qualification,QualificationAdapter.QualificationViewHolder> {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public QualificationAdapter(@NonNull FirebaseRecyclerOptions<Qualification> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull QualificationViewHolder holder, final int position, @NonNull Qualification model) {

        holder.experienceTV.setText(model.getQua());
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = getRef(position).getKey();

                databaseReference.child("users").child(currentUser).child("about").child("qualification").child(key)
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
    public QualificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_experience_edit_profile_demo,parent,false);
        return new QualificationViewHolder(view);
    }

    public class QualificationViewHolder extends RecyclerView.ViewHolder {

        TextView experienceTV;
        ImageView deleteIcon;

        public QualificationViewHolder(@NonNull View itemView) {
            super(itemView);

            experienceTV = itemView.findViewById(R.id.experienceTV);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }
}
