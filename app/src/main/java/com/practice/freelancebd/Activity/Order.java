package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.practice.freelancebd.ModelClasses.Bid;
import com.practice.freelancebd.ModelClasses.Deal;
import com.practice.freelancebd.R;

public class Order extends AppCompatActivity {

    private RecyclerView orderRV;
    private LinearLayoutManager layoutManager;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter<Deal,OrderViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        orderRV = findViewById(R.id.orderRV);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        orderRV.setLayoutManager(layoutManager);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("orders").child(firebaseAuth.getCurrentUser().getUid());

        FirebaseRecyclerOptions<Deal> options =
                new FirebaseRecyclerOptions.Builder<Deal>()
                        .setQuery(query,Deal.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Deal, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Deal model) {
                holder.employer_name.setText(model.getEmployerName());
                holder.bidder_name.setText(model.getBidderName());
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout,parent,false);
                return new OrderViewHolder(view);
            }
        };

        orderRV.setAdapter(firebaseRecyclerAdapter);

    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        private TextView employer_name, bidder_name;
        private Button payBtn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            employer_name = itemView.findViewById(R.id.employer_name);
            bidder_name = itemView.findViewById(R.id.bidder_name);
            payBtn = itemView.findViewById(R.id.payBtn);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        firebaseRecyclerAdapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
}