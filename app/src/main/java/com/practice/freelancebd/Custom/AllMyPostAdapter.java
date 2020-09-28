package com.practice.freelancebd.Custom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Activity.HomeActivity;
import com.practice.freelancebd.ModelClasses.AllMyClass;
import com.practice.freelancebd.R;

public class AllMyPostAdapter extends FirebaseRecyclerAdapter<AllMyClass,AllMyPostAdapter.AllMyPostViewHolder> {



    public AllMyPostAdapter(@NonNull FirebaseRecyclerOptions<AllMyClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AllMyPostViewHolder holder, final int position, @NonNull final AllMyClass model) {


        final String postKey = getRef(position).getKey();


        holder.title.setText(model.getTitle());
        holder.type.setText(model.getType());
        holder.postStatus.setText(model.getPostStatus());
        holder.budget.setText(model.getBudget());
        holder.applyLastDay.setText(model.getApplyLastDay());
        holder.applyLastMonth.setText(model.getApplyLastMonth());
        holder.applyLastYear.setText(model.getApplyLastYear());
        holder.description.setText(model.getDescription());




        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String currentUser = firebaseAuth.getCurrentUser().getUid();
            DatabaseReference userPostRef = databaseReference.child("users").child(currentUser).child("postedJobs")
                    .child(postKey);
            DatabaseReference postRef = databaseReference.child("posts").child(postKey);

            @Override
            public void onClick(final View v) {
                userPostRef.removeValue();
                postRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(v.getContext(), "post removed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                v.getContext().startActivity(intent);
            }
        });
        
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = (LayoutInflater)v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_layout_edit_post,null);

                final EditText titleET = view.findViewById(R.id.titleET);
                final EditText budgetET = view.findViewById(R.id.budgetET);
                final EditText statusET = view.findViewById(R.id.statusET);
                final EditText descriptionET = view.findViewById(R.id.descriptionET);
                final EditText dayET = view.findViewById(R.id.dayET);
                final EditText monthET = view.findViewById(R.id.monthET);
                final EditText yearET = view.findViewById(R.id.yearET);
                final TextView cancelTV = view.findViewById(R.id.cancelTV);
                final TextView updateTV = view.findViewById(R.id.updateTV);
                titleET.setText(model.getTitle());
                budgetET.setText(model.getBudget());
                statusET.setText(model.getPostStatus());
                descriptionET.setText(model.getDescription());
                dayET.setText(model.getApplyLastDay());
                monthET.setText(model.getApplyLastMonth());
                yearET.setText(model.getApplyLastYear());

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);


                cancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


                updateTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        String currentUser = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference userPostRef =databaseReference.child("users").child(currentUser).child("postedJobs")
                                .child(postKey);

                        DatabaseReference postRef = databaseReference.child("posts").child(postKey);

                        userPostRef.child("title").setValue(titleET.getText().toString().toUpperCase());
                        userPostRef.child("budget").setValue(budgetET.getText().toString());
                        userPostRef.child("postStatus").setValue(statusET.getText().toString());
                        userPostRef.child("description").setValue(descriptionET.getText().toString());
                        userPostRef.child("applyLastDay").setValue(dayET.getText().toString());
                        userPostRef.child("applyLastYear").setValue(yearET.getText().toString());
                        userPostRef.child("applyLastMonth").setValue(monthET.getText().toString());

                        postRef.child("title").setValue(titleET.getText().toString());
                        postRef.child("budget").setValue(budgetET.getText().toString());
                        postRef.child("postStatus").setValue(statusET.getText().toString());
                        postRef.child("description").setValue(descriptionET.getText().toString());
                        postRef.child("applyLastDay").setValue(dayET.getText().toString());
                        postRef.child("applyLastYear").setValue(yearET.getText().toString());
                        postRef.child("applyLastMonth").setValue(monthET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(v.getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                                else {
                                    Toast.makeText(v.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                });





                alertDialog.show();


            }
        });
        
    }


    @NonNull
    @Override
    public AllMyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_all_post_show_layout,parent,false);
        return new AllMyPostViewHolder(view);
    }

    public class AllMyPostViewHolder extends RecyclerView.ViewHolder {

        private TextView title, type, postStatus, budget, applyLastDay, applyLastMonth, applyLastYear, description;
        private Button editBtn, deleteBtn;

        public AllMyPostViewHolder(@NonNull View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.type);
            postStatus = itemView.findViewById(R.id.poststatus);
            budget = itemView.findViewById(R.id.budget);
            applyLastDay = itemView.findViewById(R.id.applyLastDay);
            applyLastMonth = itemView.findViewById(R.id.applyLastMonth);
            applyLastYear = itemView.findViewById(R.id.applyLastYear);
            description = itemView.findViewById(R.id.description);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
