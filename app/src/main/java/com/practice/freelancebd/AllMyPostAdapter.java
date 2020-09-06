package com.practice.freelancebd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AllMyPostAdapter extends FirebaseRecyclerAdapter<AllMyClass,AllMyPostAdapter.AllMyPostViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AllMyPostAdapter(@NonNull FirebaseRecyclerOptions<AllMyClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AllMyPostViewHolder holder, int position, @NonNull AllMyClass model) {

        holder.title.setText(model.getTitle());
        holder.type.setText(model.getType());
        holder.postStatus.setText(model.getPostStatus());
        holder.budget.setText(model.getBudget());
        holder.applyLastDay.setText(model.getApplyLastDay());
        holder.applyLastMonth.setText(model.getApplyLastMonth());
        holder.applyLastYear.setText(model.getApplyLastYear());
        holder.description.setText(model.getDescription());
    }

    @NonNull
    @Override
    public AllMyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_all_post_show_layout,parent,false);
        return new AllMyPostViewHolder(view);
    }

    public class AllMyPostViewHolder extends RecyclerView.ViewHolder {

        private TextView title, type, postStatus, budget, applyLastDay, applyLastMonth, applyLastYear, description;
        private RelativeLayout edit;

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
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
