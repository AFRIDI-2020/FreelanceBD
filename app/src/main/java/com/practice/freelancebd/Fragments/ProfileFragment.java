package com.practice.freelancebd.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.freelancebd.Activity.AboutUserProfile;
import com.practice.freelancebd.MyPostActivity;
import com.practice.freelancebd.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private CardView userAboutCardView, createLeafletCardView, myLeafletsCardView, transactionsCardView,
            myPostsCardview, ordersCardView;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userAboutCardView = view.findViewById(R.id.userAboutCardView);
        createLeafletCardView = view.findViewById(R.id.createLeafletCardView);
        myLeafletsCardView = view.findViewById(R.id.myLeafletCardView);
        transactionsCardView = view.findViewById(R.id.transactionCardView);
        myPostsCardview = view.findViewById(R.id.myPostCardView);
        ordersCardView = view.findViewById(R.id.ordersCardView);


        userAboutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUserProfile.class));
            }
        });

        myPostsCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyPostActivity.class));
            }
        });


        return view;
    }


}
