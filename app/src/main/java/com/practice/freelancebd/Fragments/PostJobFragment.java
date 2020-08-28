package com.practice.freelancebd.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.freelancebd.Activity.PostJobActivity;
import com.practice.freelancebd.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostJobFragment extends Fragment {

    private CardView websiteAndITCV, writingCV, artDesignCV, dataEntryCV,
            saleCV, eventManagementCV, businessCV, personalCV;

    public PostJobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_job, container, false);

        websiteAndITCV = view.findViewById(R.id.websiteAndITCV);
        writingCV = view.findViewById(R.id.writhingCV);
        artDesignCV = view.findViewById(R.id.artDesignCV);
        dataEntryCV = view.findViewById(R.id.dataEntryCV);
        saleCV = view.findViewById(R.id.salesCV);
        eventManagementCV = view.findViewById(R.id.eventManagementCV);
        businessCV = view.findViewById(R.id.businessCV);
        personalCV = view.findViewById(R.id.personalCV);

        websiteAndITToPostJobActivity();
        writhingsToPostJobActivity();
        artDesignToPostJobActivity();
        dataEntryToPostJobActivity();
        saleToPostJobActivity();
        eventManagementToPostJobActivity();
        businessToPostJobActivity();
        personalToPostJobActivity();


        return view;
    }

    private void personalToPostJobActivity() {
        personalCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PostJobActivity.class);
                intent.putExtra("category","Personal job");
                startActivity(intent);
            }
        });
    }

    private void businessToPostJobActivity() {
        businessCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PostJobActivity.class);
                intent.putExtra("category","Business");
                startActivity(intent);
            }
        });
    }

    private void eventManagementToPostJobActivity() {
        eventManagementCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PostJobActivity.class);
                intent.putExtra("category","Event management");
                startActivity(intent);
            }
        });
    }

    private void saleToPostJobActivity() {
        saleCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PostJobActivity.class);
                intent.putExtra("category","Sales");
                startActivity(intent);
            }
        });
    }

    private void dataEntryToPostJobActivity() {
        dataEntryCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PostJobActivity.class);
                intent.putExtra("category","Data entry");
                startActivity(intent);
            }
        });
    }

    private void artDesignToPostJobActivity() {
        artDesignCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PostJobActivity.class);
                intent.putExtra("category","Art & design");
                startActivity(intent);
            }
        });
    }

    private void writhingsToPostJobActivity() {
        writingCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PostJobActivity.class);
                intent.putExtra("category","Writings");
                startActivity(intent);
            }
        });
    }

    private void websiteAndITToPostJobActivity() {
        websiteAndITCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PostJobActivity.class);
                intent.putExtra("category","Website & IT");
                startActivity(intent);
            }
        });
    }


}
