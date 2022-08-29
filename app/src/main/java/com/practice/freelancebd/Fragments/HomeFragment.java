package com.practice.freelancebd.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.ModelClasses.AllUserPost;
import com.practice.freelancebd.Custom.AllUserPostAdapter;
import com.practice.freelancebd.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recentPostRV;
    private DatabaseReference databaseReference, postRef;
    private AllUserPostAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private String currentUser;
    private TextView websiteAndITTV, writingsTV, artAndDesignTV, businessTV, dataEntryTV, eventManagementTV, personalTV, salesTV,searchTV,allTV;
    private ArrayList<AllUserPost>allUserPostArrayList;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View loading = LayoutInflater.from(getActivity()).inflate(R.layout.firebase_data_loading_progress_dialog,null);
        builder.setView(loading);
        builder.setTitle(null);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },4000);



        allUserPostArrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        postRef = databaseReference.child("posts");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        websiteAndITTV = view.findViewById(R.id.websiteAndITTV);
        writingsTV = view.findViewById(R.id.writingsTV);
        artAndDesignTV = view.findViewById(R.id.artAndDesignTV);
        businessTV = view.findViewById(R.id.businessTV);
        dataEntryTV = view.findViewById(R.id.dataEntryTV);
        eventManagementTV = view.findViewById(R.id.eventManagementTV);
        personalTV = view.findViewById(R.id.personalTV);
        salesTV = view.findViewById(R.id.salesTV);
        searchTV = view.findViewById(R.id.searchTV);
        allTV = view.findViewById(R.id.allTV);



        recentPostRV = view.findViewById(R.id.recentPostRV);
        recentPostRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recentPostRV.setLayoutManager(linearLayoutManager);


        fetchAllPostFromDatabase("");

        getSearchTV();


        searchTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString()!=null){
                    fetchAllPostFromDatabase(s.toString());
                    searchTV.setVisibility(View.GONE);
                }
                else {
                    fetchAllPostFromDatabase("");
                    searchTV.setVisibility(View.GONE);
                }

            }
        });



        return view;
    }

    private void getSearchTV() {
        websiteAndITTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTV.setText("Website & IT");
            }
        });

        writingsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTV.setText("Writings");
            }
        });

        artAndDesignTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTV.setText("Art & design");
            }
        });

        businessTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTV.setText("Business");
            }
        });

        dataEntryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTV.setText("Data entry");
            }
        });

        personalTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTV.setText("Personal");
            }
        });

        salesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTV.setText("Sales");
            }
        });

        eventManagementTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTV.setText("Event management");
            }
        });

        allTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTV.setText("");
            }
        });

    }


    private void fetchAllPostFromDatabase(String data) {

        Query query = postRef.orderByChild("type").startAt(data).endAt(data + "\uf8ff");
        FirebaseRecyclerOptions<AllUserPost> options =
                new FirebaseRecyclerOptions.Builder<AllUserPost>()
                        .setQuery(query, AllUserPost.class)
                        .build();

        adapter = new AllUserPostAdapter(options);
        adapter.startListening();
        recentPostRV.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
