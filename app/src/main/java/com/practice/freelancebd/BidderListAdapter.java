package com.practice.freelancebd;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.practice.freelancebd.Activity.PersonsActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class BidderListAdapter extends FirebaseRecyclerAdapter<Bidder,BidderListAdapter.BidderViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BidderListAdapter(@NonNull FirebaseRecyclerOptions<Bidder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BidderViewHolder holder, int position, @NonNull Bidder model) {

        holder.bidderNameTV.setText(model.getBidder());
        holder.bidAmountTV.setText(model.getBidAmount());
        holder.bidDayTV.setText(model.getBidDay());
        final String bidderId = model.getBidderId();
        String countDay = model.getBidDay();
        int day = Integer.parseInt(countDay);
        if(day>1){
            holder.dayOrDaysTV.setText("days");
        }
        else {
            holder.dayOrDaysTV.setText("day");
        }
        holder.bidDescriptionTV.setText(model.getBidDescription());
        Picasso.get().load(model.getBidderProfileImageLink()).into(holder.bidderProfileImage);

        holder.bidderNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PersonsActivity.class);
                intent.putExtra("bidderId",bidderId);
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public BidderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_bidders_list_demo_layout,parent,false);
        return new BidderViewHolder(view);
    }

    public class BidderViewHolder extends RecyclerView.ViewHolder {

        private TextView bidderNameTV, bidAmountTV, bidDayTV, bidDescriptionTV,dayOrDaysTV;
        private CircleImageView bidderProfileImage;

        public BidderViewHolder(@NonNull View itemView) {
            super(itemView);

            bidderNameTV = itemView.findViewById(R.id.bidderNameTV);
            bidAmountTV = itemView.findViewById(R.id.bidAmountTV);
            bidDayTV = itemView.findViewById(R.id.bidDayTV);
            bidDescriptionTV = itemView.findViewById(R.id.bidDescriptionTV);
            bidderProfileImage = itemView.findViewById(R.id.bidderProfileImage);
            dayOrDaysTV = itemView.findViewById(R.id.dayOrDaysTV);
        }
    }
}
