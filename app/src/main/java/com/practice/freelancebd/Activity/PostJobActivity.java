package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.ModelClasses.Post;
import com.practice.freelancebd.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PostJobActivity extends AppCompatActivity {

    private Spinner statusSpinner, daySpinner, monthSpinner, yearSpinner;
    private String[] status = {"open", "close"};
    private String[] day = {"Day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] month = {"Month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private String[] year = {"Year", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
    private ArrayList<String> statusList, dayList, monthList, yearList;
    private ArrayAdapter<String> statusAdapter, daySpinnerAdapter, monthSpinnerAdapter, yearSpinnerAdapter;
    private TextView postTypeTV;
    private EditText titleET, budgetET, descriptionET;
    private String type, title, budget, description, postStatus, applyLastMonth, applyLastDay, applyLastYear;
    private String userID,employerName,profileImageLink,postKey;
    private Button postJobBtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        init();

        getUsername();

        getProfileImage();



        setPostType();

        setStatusSpinner();
        setDaySpinner();
        setMonthSpinner();
        setYearSpinner();


    }


    //user input validity check

    private boolean budgetValidity() {
        budget = budgetET.getText().toString();
        if (budget.isEmpty()) {
            budgetET.setError("Required!");
            return false;
        } else {
            budgetET.setError(null);
            return true;
        }
    }
    private boolean descriptionValidity() {
        description = descriptionET.getText().toString();
        if (description.isEmpty()) {
            descriptionET.setError("Required!");
            return false;
        } else {
            descriptionET.setError(null);
            return true;
        }
    }
    private boolean titleValidity() {
        title = titleET.getText().toString();
        if (title.isEmpty()) {
            titleET.setError("Required!");
            return false;
        } else if (title.length() > 40) {
            titleET.setError("Make your title less than 40 characters.");
            return false;
        } else {
            titleET.setError(null);
            return true;
        }
    }

    //UI setup

    private void setYearSpinner() {
        yearList = new ArrayList<>(Arrays.asList(year));
        yearSpinnerAdapter = new ArrayAdapter<>(this, R.layout.status_spinner_style, yearList);
        yearSpinner.setAdapter(yearSpinnerAdapter);
    }
    private void setMonthSpinner() {
        monthList = new ArrayList<>(Arrays.asList(month));
        monthSpinnerAdapter = new ArrayAdapter<>(this, R.layout.status_spinner_style, monthList);
        monthSpinner.setAdapter(monthSpinnerAdapter);
    }
    private void setDaySpinner() {
        dayList = new ArrayList<>(Arrays.asList(day));
        daySpinnerAdapter = new ArrayAdapter<>(this, R.layout.status_spinner_style, dayList);
        daySpinner.setAdapter(daySpinnerAdapter);
    }
    private void setPostType() {
        Intent intent = getIntent();
        type = intent.getStringExtra("category");
        postTypeTV.setText(type);
    }
    private void setStatusSpinner() {
        statusList = new ArrayList<>(Arrays.asList(status));
        statusAdapter = new ArrayAdapter<>(this, R.layout.status_spinner_style, statusList);
        statusSpinner.setAdapter(statusAdapter);
    }

    //initiating UI items

    private void init() {
        statusSpinner = findViewById(R.id.statusSpinner);
        postTypeTV = findViewById(R.id.postTypeTV);
        daySpinner = findViewById(R.id.daySpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        titleET = findViewById(R.id.titleET);
        budgetET = findViewById(R.id.budgetET);
        descriptionET = findViewById(R.id.descriptionET);
        postJobBtn = findViewById(R.id.postJobBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userID = firebaseAuth.getCurrentUser().getUid();
    }

    //getting data from database

    private void getUsername() {
        databaseReference.child("users").child(userID).child("about").child("personalInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    employerName = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getProfileImage() {
        databaseReference.child("users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    profileImageLink = dataSnapshot.child("profileImageLink").getValue().toString();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //sending data to database

    public void postTheJob(View view) {
        if(!titleValidity() | !budgetValidity() | !descriptionValidity()){
            return;
        }
        else {
            title = titleET.getText().toString();
            postStatus = statusSpinner.getSelectedItem().toString();
            budget = budgetET.getText().toString();
            applyLastDay = daySpinner.getSelectedItem().toString();
            applyLastMonth = monthSpinner.getSelectedItem().toString();
            applyLastYear = yearSpinner.getSelectedItem().toString();
            description = descriptionET.getText().toString();

            sendPostToDatabase();
        }
    }
    private void sendPostToDatabase() {

        postKey = databaseReference.child("posts").push().getKey();

        databaseReference.child("posts").child(postKey).setValue(new Post(profileImageLink,employerName,type,title,postStatus,budget,applyLastDay,applyLastMonth,applyLastYear,description,userID))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Intent intent = new Intent(PostJobActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(PostJobActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        databaseReference.child("users").child(userID).child("postedJobs").child(postKey).setValue(new Post(profileImageLink,employerName,type,title,postStatus,budget,applyLastDay,applyLastMonth,applyLastYear,description,userID))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(PostJobActivity.this, "posted.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(PostJobActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
