package com.practice.freelancebd.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.freelancebd.Custom.ImageAdapter;
import com.practice.freelancebd.Custom.PdfAdapter;
import com.practice.freelancebd.ModelClasses.Image;
import com.practice.freelancebd.ModelClasses.JobPost;
import com.practice.freelancebd.ModelClasses.Pdf;
import com.practice.freelancebd.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostJobFragment extends Fragment {

    private Spinner jobCategorySpinner,statusSpinner;
    private ArrayAdapter<String> adapter,statusAdapter;
    private LinearLayout addPhotosLayout, addPdfLayout;
    private int GALLERY_IMAGE_PICKER_CODE = 2;
    private int PDF_FILE_PIKER_CODE = 3;
    private RecyclerView imageRecyclerView, pdfRecyclerView;
    private List<Image> imageList;
    private List<Pdf> pdfList;
    private ImageAdapter imageAdapter;
    private PdfAdapter pdfAdapter;
    private Button postTheJobBtn;
    private EditText titleET, budgetET, monthDurationET, dayDurationET, hourDurationET, descriptionET;

    private String category,status, title, budget, duration, month, day, hour, description, saveCurrentDate,
            saveCurrentTime, postRandomId, image, pdf, userName, profileImage;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, userRef,postRef;


    public PostJobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_job, container, false);

        jobCategorySpinner = view.findViewById(R.id.jobCategorySpinner);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        addPhotosLayout = view.findViewById(R.id.addPhotosLayout);
        addPdfLayout = view.findViewById(R.id.addPdfLayout);
        imageRecyclerView = view.findViewById(R.id.showUserSelectedImageRecyclerView);
        pdfRecyclerView = view.findViewById(R.id.pdfRecyclerView);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        imageList = new ArrayList<>();
        pdfList = new ArrayList<>();
        postTheJobBtn = view.findViewById(R.id.postTheJobBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        titleET = view.findViewById(R.id.titleET);
        budgetET = view.findViewById(R.id.budgetET);
        monthDurationET = view.findViewById(R.id.monthDurationET);
        dayDurationET = view.findViewById(R.id.dayDurationET);
        hourDurationET = view.findViewById(R.id.hourDurationET);
        descriptionET = view.findViewById(R.id.descriptionET);


        ArrayList<String> jobCategory = new ArrayList<>();
        jobCategory.add("Not selected");
        jobCategory.add("Writing");
        jobCategory.add("Website & IT");
        jobCategory.add("Art & design");
        jobCategory.add("Data entry");
        jobCategory.add("Event management");
        jobCategory.add("Business");
        jobCategory.add("Personally hiring");

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, jobCategory);
        jobCategorySpinner.setAdapter(adapter);


        jobCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = jobCategorySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> postStatus = new ArrayList<>();
        postStatus.add("Open (default)");
        postStatus.add("Closed");

        statusAdapter = new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,postStatus);
        statusSpinner.setAdapter(statusAdapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                status = statusSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addPhotosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_IMAGE_PICKER_CODE);
            }
        });

        addPdfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select pdf file"), PDF_FILE_PIKER_CODE);
            }
        });


        imageAdapter = new ImageAdapter(imageList, getActivity());
        imageRecyclerView.setAdapter(imageAdapter);

        pdfAdapter = new PdfAdapter(pdfList, getActivity());
        pdfRecyclerView.setAdapter(pdfAdapter);


        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            userName = dataSnapshot.child("name").getValue().toString();
                            profileImage = dataSnapshot.child("profile image").getValue().toString();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        postTheJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateCategory() | !validateTitle() |!validateBudget() | !validateHour() | !validateDescription()){
                    return;
                }
                else {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("EEE, d MMM yyyy");
                    saveCurrentDate = currentDate.format(calendar.getTime());
                    SimpleDateFormat currentTime = new SimpleDateFormat("h:mm a");
                    saveCurrentTime = currentTime.format(calendar.getTime());
                    postRandomId = saveCurrentDate+ " " + saveCurrentTime;


                    title = titleET.getText().toString().trim();
                    budget = budgetET.getText().toString().trim();
                    hour = hourDurationET.getText().toString().trim();
                    description = descriptionET.getText().toString().trim();
                    month = monthDurationET.getText().toString().trim();
                    day = dayDurationET.getText().toString().trim();
                    duration = month + day + hour;
                    image = "null";
                    pdf = "null";
                    
                    saveToDB(userName,profileImage,category,status,title,budget,duration,description,saveCurrentDate,saveCurrentTime,image,pdf);
                }
            }
        });


        return view;
    }

    private void saveToDB(String userName, String profileImage, String category,String status, String title, String budget, String duration, String description, String saveCurrentDate, String saveCurrentTime, String image, String pdf) {

        String currentUser = firebaseAuth.getCurrentUser().getUid();
        userRef = databaseReference.child("users").child(currentUser).child("postedJobs");
        postRef = databaseReference.child("posts");
        JobPost jobPost = new JobPost(userName,profileImage,category,status,title,budget,duration,description,saveCurrentDate,saveCurrentTime,image,pdf);
        userRef.push().setValue(jobPost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Job posted successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String message = task.getException().getMessage();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        postRef.push().setValue(jobPost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout,new HomeFragment())
                            .commit();
                }
                else {
                    String message = task.getException().getMessage();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean validateCategory() {

        if (category.equals("Not selected")) {
            Toast.makeText(getActivity(), "Select a job category!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateTitle() {

        title = titleET.getText().toString();
        if (title.length()<=0) {
            titleET.setError("Job title is required!");
            titleET.requestFocus();
            return false;
        } else {
            titleET.setError(null);
            return true;
        }
    }

    private boolean validateBudget() {
        budget = budgetET.getText().toString().trim();

        if (budget.length()<=0) {
            budgetET.setError("Make your budget for this job!");
            return false;
        } else {
            budgetET.setError(null);
            return true;
        }
    }

    private boolean validateHour() {

        hour = hourDurationET.getText().toString().trim();
        if (hour.length()<=0) {
            hourDurationET.setError("Check job duration!");
            return false;
        } else {
            hourDurationET.setError(null);
            return true;
        }
    }


    private boolean validateDescription() {

        description = descriptionET.getText().toString().trim();

        if (description.length()<=0) {
            descriptionET.setError("Add a description of your post!");
            return false;
        } else {
            descriptionET.setError(null);
            return true;
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_PICKER_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri resultUri = data.getData();
            String fileName = resultUri.getLastPathSegment();

            imageList.add(new Image(resultUri, fileName));
            imageAdapter.notifyDataSetChanged();


        }
        if (requestCode == PDF_FILE_PIKER_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri pdfUri = data.getData();
            String pdfName = pdfUri.getLastPathSegment();

            pdfList.add(new Pdf(pdfUri, pdfName));
            pdfAdapter.notifyDataSetChanged();
        }
    }
}
