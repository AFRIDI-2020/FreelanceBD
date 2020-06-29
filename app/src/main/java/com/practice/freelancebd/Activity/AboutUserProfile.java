package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.practice.freelancebd.R;
import com.practice.freelancebd.ModelClasses.UserAbout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AboutUserProfile extends AppCompatActivity {

    private SwitchCompat switchButton;
    private Spinner categorySpinner1, categorySpinner2;
    private ImageView editIcon;
    private TextView fullNameTV, sexTV, occupationTV, mobileTV, skillTV, selectCategorySuggestion, interestedCategory1TV, interestedCategory2TV, commaTV,userNameTV;
    private EditText fullNameET, occupationET, mobileET, skillET;
    private Button saveBtn,logOutBtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String fullName, sex, occupation, mobile, skills, interestedCategory1, interestedCategory2;
    private String[] gender = {"Male", "Female", "Third gender"};
    private AutoCompleteTextView sexET;
    private ArrayAdapter<String> adapter;
    private FloatingActionButton cameraImagePicker;
    private CircleImageView profilePhoto;
    private StorageReference userProfileImageRef;

    private int STORAGE_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user_profile);


        progressDialog = new ProgressDialog(AboutUserProfile.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.firebase_data_loading_progress_dialog);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressDialog.setCancelable(false);
                progressDialog.dismiss();
            }
        }, 4000);


        init();

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(AboutUserProfile.this, LogInActivity.class));
            }
        });

        getDataFromDB();


        cameraImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(AboutUserProfile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1)
                            .start(AboutUserProfile.this);

                } else {
                    requestStoragePermission();
                }
            }
        });




        ArrayAdapter adapter1 = new ArrayAdapter(AboutUserProfile.this, R.layout.support_simple_spinner_dropdown_item, gender);
        sexET.setAdapter(adapter1);
        sexET.setThreshold(1);

        ArrayList<String> interested_category = new ArrayList<>();

        interested_category.add("Writing");
        interested_category.add("Website & IT");
        interested_category.add("Art & design");
        interested_category.add("Data entry");
        interested_category.add("Event Management");
        interested_category.add("Business");
        interested_category.add("Personally hiring");

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, interested_category);
        categorySpinner1.setAdapter(adapter);
        categorySpinner2.setAdapter(adapter);

        categorySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        categorySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchButton.isChecked()) {

                    Toast.makeText(AboutUserProfile.this, "Mobile number is set to private", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AboutUserProfile.this, "Mobile number is set to public", Toast.LENGTH_SHORT).show();
                }
            }
        });


        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setVisibility();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(AboutUserProfile.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.firebase_data_loading_progress_dialog);


                setVisibility2();

                getDataFromUser();

                insertingInDB();


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                progressDialog = new ProgressDialog(AboutUserProfile.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.firebase_uploading_file_progress_dialog);

                final Uri resultUri = result.getUri();
                final String currentUser = firebaseAuth.getCurrentUser().getUid();
                StorageReference filePath = userProfileImageRef.child(currentUser + ".jpg");
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {

                            final String downloadUriLink = resultUri.toString();
                            databaseReference.child("users").child(currentUser).child("profile image")
                                    .setValue(downloadUriLink).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(AboutUserProfile.this, "Profile image uploaded successfully!", Toast.LENGTH_SHORT).show();
                                        Picasso.get().load(downloadUriLink).into(profilePhoto);
                                        progressDialog.dismiss();
                                    } else {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(AboutUserProfile.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed!")
                    .setMessage("Permission is needed to access next")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(AboutUserProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getDataFromDB() {

        String userID = firebaseAuth.getCurrentUser().getUid();

        DatabaseReference userRef = databaseReference.child("users").child(userID).child("about");
        DatabaseReference userProfileImageRef = databaseReference.child("users").child(userID);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String full_name = dataSnapshot.child("fullName").getValue().toString();
                    String sex = dataSnapshot.child("sex").getValue().toString();
                    String interested_category1 = dataSnapshot.child("interestedCategory1").getValue().toString();
                    String interested_category2 = dataSnapshot.child("interestedCategory2").getValue().toString();
                    String mobile = dataSnapshot.child("mobile").getValue().toString();
                    String occupation = dataSnapshot.child("occupation").getValue().toString();
                    String skills = dataSnapshot.child("skills").getValue().toString();

                    fullNameTV.setText(full_name);
                    fullNameET.setText(full_name);
                    sexTV.setText(sex);
                    sexET.setText(sex);
                    mobileTV.setText(mobile);
                    mobileET.setText(mobile);
                    occupationTV.setText(occupation);
                    occupationET.setText(occupation);
                    skillET.setText(skills);
                    skillTV.setText(skills);
                    interestedCategory1TV.setText(interested_category1);
                    interestedCategory2TV.setText(interested_category2);

                    int spinnerPosition1 = adapter.getPosition(interested_category1);
                    categorySpinner1.setSelection(spinnerPosition1);

                    int spinnerPosition2 = adapter.getPosition(interested_category2);
                    categorySpinner2.setSelection(spinnerPosition2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userProfileImageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    if(dataSnapshot.hasChild("profile image")){
                        String image = dataSnapshot.child("profile image").getValue().toString();
                        Picasso.get().load(image).into(profilePhoto);
                    }

                    if(dataSnapshot.hasChild("name")){
                        String userName = dataSnapshot.child("name").getValue().toString();
                        userNameTV.setText(userName);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getDataFromUser() {

        fullName = fullNameET.getText().toString().trim();
        sex = sexET.getText().toString().trim();
        occupation = occupationET.getText().toString().trim();
        mobile = mobileET.getText().toString().trim();
        skills = skillET.getText().toString().trim();
        interestedCategory1 = categorySpinner1.getSelectedItem().toString();
        interestedCategory2 = categorySpinner2.getSelectedItem().toString();
    }


    private void insertingInDB() {

        String userID = firebaseAuth.getCurrentUser().getUid();

        DatabaseReference userRef = databaseReference.child("users").child(userID).child("about");

        UserAbout userAbout = new UserAbout(fullName, sex, occupation, mobile, skills, interestedCategory1, interestedCategory2);

        userRef.setValue(userAbout).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(AboutUserProfile.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setVisibility2() {

        fullNameTV.setVisibility(View.VISIBLE);
        sexTV.setVisibility(View.VISIBLE);
        occupationTV.setVisibility(View.VISIBLE);
        mobileTV.setVisibility(View.VISIBLE);
        skillTV.setVisibility(View.VISIBLE);
        interestedCategory1TV.setVisibility(View.VISIBLE);
        interestedCategory2TV.setVisibility(View.VISIBLE);
        commaTV.setVisibility(View.VISIBLE);


        categorySpinner1.setVisibility(View.GONE);
        categorySpinner2.setVisibility(View.GONE);
        fullNameET.setVisibility(View.GONE);
        sexET.setVisibility(View.GONE);
        occupationET.setVisibility(View.GONE);
        mobileET.setVisibility(View.GONE);
        skillET.setVisibility(View.GONE);
        saveBtn.setVisibility(View.GONE);
        editIcon.setVisibility(View.VISIBLE);
        selectCategorySuggestion.setVisibility(View.GONE);
    }

    private void setVisibility() {

        fullNameTV.setVisibility(View.GONE);
        sexTV.setVisibility(View.GONE);
        occupationTV.setVisibility(View.GONE);
        mobileTV.setVisibility(View.GONE);
        skillTV.setVisibility(View.GONE);
        interestedCategory1TV.setVisibility(View.GONE);
        interestedCategory2TV.setVisibility(View.GONE);
        commaTV.setVisibility(View.GONE);

        categorySpinner1.setVisibility(View.VISIBLE);
        categorySpinner2.setVisibility(View.VISIBLE);
        fullNameET.setVisibility(View.VISIBLE);
        sexET.setVisibility(View.VISIBLE);
        occupationET.setVisibility(View.VISIBLE);
        mobileET.setVisibility(View.VISIBLE);
        skillET.setVisibility(View.VISIBLE);
        saveBtn.setVisibility(View.VISIBLE);
        editIcon.setVisibility(View.GONE);
        selectCategorySuggestion.setVisibility(View.VISIBLE);
    }


    private void init() {
        switchButton = findViewById(R.id.switchButton);
        categorySpinner1 = findViewById(R.id.categorySpinner1);
        categorySpinner2 = findViewById(R.id.categorySpinner2);
        fullNameTV = findViewById(R.id.fullNameTV);
        sexTV = findViewById(R.id.sexTV);
        occupationTV = findViewById(R.id.occupationTV);
        mobileTV = findViewById(R.id.mobileTV);
        skillTV = findViewById(R.id.skillsTV);
        selectCategorySuggestion = findViewById(R.id.selectCategorySuggestion);
        fullNameET = findViewById(R.id.fullNameET);
        sexET = findViewById(R.id.sexET);
        occupationET = findViewById(R.id.occupationET);
        mobileET = findViewById(R.id.mobileET);
        skillET = findViewById(R.id.skillsET);
        editIcon = findViewById(R.id.editIcon);
        saveBtn = findViewById(R.id.saveBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        interestedCategory1TV = findViewById(R.id.interestedCategory1TV);
        interestedCategory2TV = findViewById(R.id.interestedCategory2TV);
        cameraImagePicker = findViewById(R.id.cameraImagePicker);
        profilePhoto = findViewById(R.id.profilePhoto);
        commaTV = findViewById(R.id.commaTV);
        logOutBtn = findViewById(R.id.logOutBtn);
        userNameTV = findViewById(R.id.usernameTV);

    }


}
