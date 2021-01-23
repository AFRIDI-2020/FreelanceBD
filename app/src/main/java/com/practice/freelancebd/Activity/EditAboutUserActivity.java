package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.practice.freelancebd.ModelClasses.UserProfile;
import com.practice.freelancebd.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class EditAboutUserActivity extends AppCompatActivity {

    private ImageView profileImage,backImageView;
    private TextInputEditText fullNameTIET, professionalTagTIET, aboutUserTIET;
    private int STORAGE_PERMISSION_CODE = 1;
    private Uri resultUri;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private String currentUser, profileImageLink = null;
    private AlertDialog uploadAlertDialog, savingAlertDialog,loadingAlertDialog;
    private DatabaseReference databaseReference, userProfileRef;
    private TextInputLayout fullNameTextInputLayout, professioanlTagTextInputLayout, aboutUserTextInputLayout;
    private int flag = 0, completeness = 0;
    private TextView saveTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_user);

        init();
        startLoadingAlertDialog();
        getPreviousData();

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateFullName() | !validateProfessionalTag() | !validateaboutUser() | !validateProfileImageLink()) {
                    return;
                } else {
                    startSavingAlertDialog();
                    insertUserInputsToDatabase();
                }
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(completeness == 4){
                    startActivity(new Intent(EditAboutUserActivity.this,AboutUserProfile.class));
                }
                else {
                    Toast.makeText(EditAboutUserActivity.this, "complete your profile first", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    //get previous data from database

    private void getPreviousData() {

        userProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String fullName = snapshot.child("fullName").getValue().toString();
                    completeness++;
                    fullNameTIET.setText(fullName);
                    String professionalTag = snapshot.child("professionalTag").getValue().toString();
                    completeness++;
                    professionalTagTIET.setText(professionalTag);
                    String aboutUser = snapshot.child("aboutUser").getValue().toString();
                    completeness++;
                    aboutUserTIET.setText(aboutUser);
                    String mProfileImageLink = snapshot.child("profileImageLink").getValue().toString();
                    completeness++;
                    profileImageLink = mProfileImageLink;
                    flag = 1;
                    Picasso.get().load(mProfileImageLink).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        loadingAlertDialog.dismiss();
    }

    private void startLoadingAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAboutUserActivity.this);
        View view = getLayoutInflater().inflate(R.layout.firebase_data_loading_progress_dialog,null);
        builder.setView(view);
        builder.setCancelable(false);
        loadingAlertDialog = builder.create();
        loadingAlertDialog.show();
    }


    //save data to database

    private void insertUserInputsToDatabase() {

        userProfileRef.setValue(new UserProfile(fullNameTIET.getText().toString(), professionalTagTIET.getText().toString(), aboutUserTIET.getText().toString(), profileImageLink))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            savingAlertDialog.dismiss();
                            Toast.makeText(EditAboutUserActivity.this, "profile updated!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditAboutUserActivity.this, AboutUserProfile.class));
                        } else {
                            Toast.makeText(EditAboutUserActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void startSavingAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAboutUserActivity.this);
        View view = getLayoutInflater().inflate(R.layout.saving_layout, null);
        builder.setView(view);
        builder.setTitle(null);
        builder.setCancelable(false);
        savingAlertDialog = builder.create();
        savingAlertDialog.show();
    }


    //initializing all layout views

    private void init() {
        profileImage = findViewById(R.id.profile_image);
        fullNameTIET = findViewById(R.id.fullNameTIET);
        professionalTagTIET = findViewById(R.id.professionalTagTIET);
        aboutUserTIET = findViewById(R.id.aboutUserTIET);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userProfileRef = databaseReference.child("users").child(currentUser).child("userProfile");
        fullNameTextInputLayout = findViewById(R.id.fullNameTextInputLayout);
        professioanlTagTextInputLayout = findViewById(R.id.professionalTagTextInputLayout);
        aboutUserTextInputLayout = findViewById(R.id.aboutUserTextInputLayout);
        backImageView = findViewById(R.id.backImageView);
        saveTv = findViewById(R.id.tv_save);
    }


    //checking user inputs validity

    public boolean validateFullName() {
        String fullName = fullNameTIET.getText().toString().trim();
        if (fullName.isEmpty()) {
            fullNameTextInputLayout.setError("Required!");
            return false;
        } else {
            fullNameTextInputLayout.setError(null);
            return true;
        }
    }

    public boolean validateProfessionalTag() {
        String professinalTag = professionalTagTIET.getText().toString().trim();
        if (professinalTag.isEmpty()) {
            professioanlTagTextInputLayout.setError("Required!");
            return false;
        } else {
            professioanlTagTextInputLayout.setError(null);
            return true;
        }
    }

    public boolean validateaboutUser() {
        String aboutUser = aboutUserTIET.getText().toString().trim();
        if (aboutUser.isEmpty()) {
            aboutUserTextInputLayout.setError("Required!");
            return false;
        } else {
            aboutUserTextInputLayout.setError(null);
            return true;
        }
    }

    public boolean validateProfileImageLink() {

        if (flag == 0) {

            Toast.makeText(this, "select a profile photo", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    //press selectPhotoBtn to choose photo, taking storage permission, crop photo and select photo

    public void selectPhoto(View view) {
        if (ContextCompat.checkSelfPermission(EditAboutUserActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(EditAboutUserActivity.this);

        } else {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed!")
                    .setMessage("Permission is needed to access storage")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(EditAboutUserActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                startUploadAlertDialog();
                resultUri = result.getUri();
                flag = 1;
                saveToCloudStorage(resultUri);
                profileImage.setImageURI(resultUri);

            }
        }
    }

    private void saveToCloudStorage(Uri resultUri) {
        final StorageReference profileImgRef = storageReference.child("profileImages").child(currentUser + ".jpg");
        final UploadTask uploadTask = profileImgRef.putFile(resultUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return profileImgRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            profileImageLink  = downloadUri.toString();
                            uploadAlertDialog.dismiss();
                        }
                    }
                });
            }
        });
    }



    //a dialog is showing while uploading
    private void startUploadAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditAboutUserActivity.this);
        View view = getLayoutInflater().inflate(R.layout.uploading_layout, null);
        builder.setView(view);
        builder.setTitle(null);
        builder.setCancelable(false);
        uploadAlertDialog = builder.create();
        uploadAlertDialog.show();
    }



}
