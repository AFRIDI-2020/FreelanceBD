package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.practice.freelancebd.ModelClasses.Experience;
import com.practice.freelancebd.ModelClasses.ExperienceAdapter;
import com.practice.freelancebd.PersonalInfo;
import com.practice.freelancebd.Qualification;
import com.practice.freelancebd.QualificationAdapter;
import com.practice.freelancebd.R;
import com.practice.freelancebd.Skill;
import com.practice.freelancebd.SkillAdapter;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAboutUserActivity extends AppCompatActivity {


    private EditText nameET, emailET, mobileET, professionalTagET, aboutYourselfET,
            workExperienceET, qualificationET, skillET;
    private TextView descriptionLayout;
    private ImageView experienceExpandIV,qualificationExpandIV, skillExpandIV,personalInfoExpandIV;
    private Button saveBtn,saveXpBtn,saveQualificationBtn,saveSkillBtn;
    private CircleImageView profileImage;
    private FloatingActionButton profileImagePicker;
    private Uri resultUri;
    private int STORAGE_PERMISSION_CODE = 1;
    private RecyclerView experienceRV, qualificationRV, skillRV;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,experienceRef,qualificationRef,skillRef;
    private StorageReference storageReference;
    private String currentUser, name, email, mobileNo, professionalTag, xp, qua, sk, description, profileImageLink;
    private ExperienceAdapter experienceAdapter;
    private QualificationAdapter qualificationAdapter;
    private SkillAdapter skillAdapter;
    private LinearLayout nameLayout, emailLayout, mobileNoLayout, tagLayout, descriptionLayoutET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_user);

        init();

        getPersonalInfoFromDB();

        getExperienceFromDB();

        getQualificationFromDB();

        getSkillFromDB();


        profileImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmail() | !validateName() | !validateMobileNo() | !validateProfessinalTag() |
                        !validateAboutYourSelf()) {
                    return;
                } else {

                    getPersonalInfoFromUser();

                    insertPersonalInfoToDB();
                }
            }
        });

        experienceExpandIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experienceFieldVisibility();
            }
        });

        qualificationExpandIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qualificationFieldVisibility();
            }
        });

        skillExpandIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillFieldVisibility();
            }
        });

        personalInfoExpandIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalInfoFieldVisibility();
            }
        });


    }

    private void personalInfoFieldVisibility() {

        if(nameLayout.getVisibility() == View.GONE){
            nameLayout.setVisibility(View.VISIBLE);
            emailLayout.setVisibility(View.VISIBLE);
            mobileNoLayout.setVisibility(View.VISIBLE);
            tagLayout.setVisibility(View.VISIBLE);
            descriptionLayoutET.setVisibility(View.VISIBLE);
            descriptionLayout.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
        }
        else
        {
            nameLayout.setVisibility(View.GONE);
            emailLayout.setVisibility(View.GONE);
            mobileNoLayout.setVisibility(View.GONE);
            tagLayout.setVisibility(View.GONE);
            descriptionLayout.setVisibility(View.GONE);
            descriptionLayoutET.setVisibility(View.GONE);
            saveBtn.setVisibility(View.GONE);
        }
    }

    private void skillFieldVisibility() {

        if(skillET.getVisibility() == View.VISIBLE){
            skillET.setVisibility(View.GONE);
            skillRV.setVisibility(View.GONE);
            saveSkillBtn.setVisibility(View.GONE);
        }else{
            skillET.setVisibility(View.VISIBLE);
            skillRV.setVisibility(View.VISIBLE);
            saveSkillBtn.setVisibility(View.VISIBLE);
        }
    }

    private void qualificationFieldVisibility() {

        if(qualificationET.getVisibility() == View.VISIBLE){
            qualificationET.setVisibility(View.GONE);
            qualificationRV.setVisibility(View.GONE);
            saveQualificationBtn.setVisibility(View.GONE);
        }else
        {
            qualificationET.setVisibility(View.VISIBLE);
            qualificationRV.setVisibility(View.VISIBLE);
            saveQualificationBtn.setVisibility(View.VISIBLE);
        }
    }

    private void experienceFieldVisibility() {

        if(workExperienceET.getVisibility() == View.VISIBLE){
            workExperienceET.setVisibility(View.GONE);
            experienceRV.setVisibility(View.GONE);
            saveXpBtn.setVisibility(View.GONE);
        }
        else
        {
            workExperienceET.setVisibility(View.VISIBLE);
            experienceRV.setVisibility(View.VISIBLE);
            saveXpBtn.setVisibility(View.VISIBLE);
        }
    }

    private void getSkillFromDB() {

        skillRV.setLayoutManager(new LinearLayoutManager(EditAboutUserActivity.this));
        skillRef = databaseReference.child("users").child(currentUser).child("about").child("skill");

        FirebaseRecyclerOptions<Skill> option2 =
                new FirebaseRecyclerOptions.Builder<Skill>()
                        .setQuery(skillRef, Skill.class)
                        .build();
        skillAdapter = new SkillAdapter(option2);
        skillRV.setAdapter(skillAdapter);

    }

    private void getQualificationFromDB() {

        qualificationRV.setLayoutManager(new LinearLayoutManager(EditAboutUserActivity.this));
        qualificationRef = databaseReference.child("users").child(currentUser).child("about").child("qualification");
        FirebaseRecyclerOptions<Qualification> option1 =
                new FirebaseRecyclerOptions.Builder<Qualification>()
                        .setQuery(qualificationRef, Qualification.class)
                        .build();
        qualificationAdapter = new QualificationAdapter(option1);
        qualificationRV.setAdapter(qualificationAdapter);
    }

    private void getExperienceFromDB() {

        experienceRV.setLayoutManager(new LinearLayoutManager(EditAboutUserActivity.this));
        experienceRef = databaseReference.child("users").child(currentUser).child("about").child("experience");
        FirebaseRecyclerOptions<Experience> option =
                new FirebaseRecyclerOptions.Builder<Experience>()
                        .setQuery(experienceRef, Experience.class)
                        .build();

        experienceAdapter = new ExperienceAdapter(option);
        experienceRV.setAdapter(experienceAdapter);
    }

    private void getPersonalInfoFromDB() {

        databaseReference.child("users").child(currentUser).child("about").child("personalInfo")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            String description = dataSnapshot.child("description").getValue().toString();
                            String email = dataSnapshot.child("email").getValue().toString();
                            String mobileNo = dataSnapshot.child("mobileNo").getValue().toString();
                            String name = dataSnapshot.child("name").getValue().toString();
                            String professionalTag = dataSnapshot.child("professionalTag").getValue().toString();

                            nameET.setText(name);
                            emailET.setText(email);
                            mobileET.setText(mobileNo);
                            professionalTagET.setText(professionalTag);
                            aboutYourselfET.setText(description);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        databaseReference.child("users").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    if(dataSnapshot.hasChild("profileImage")){
                        String image = dataSnapshot.child("profileImage").getValue().toString();
                        Picasso.get().load(image).into(profileImage);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void insertPersonalInfoToDB() {
        databaseReference.child("users").child(currentUser).child("about").child("personalInfo")
                .setValue(new PersonalInfo(name,email,mobileNo,description,professionalTag))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EditAboutUserActivity.this, "saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getPersonalInfoFromUser() {

        name = nameET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        mobileNo = mobileET.getText().toString().trim();
        professionalTag = professionalTagET.getText().toString().trim();
        description = aboutYourselfET.getText().toString().trim();
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed!")
                    .setMessage("Permission is needed to access next")
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
                resultUri = result.getUri();
                profileImage.setImageURI(resultUri);
                storageReference.child("profileImages").child(currentUser + ".jpg")
                        .putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {
                            profileImageLink = resultUri.toString();
                            databaseReference.child("users").child(currentUser).child("profileImageLink")
                                    .setValue(profileImageLink).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(EditAboutUserActivity.this, "profile image saved", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(EditAboutUserActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }


    public boolean validateName() {

        String name = nameET.getText().toString().trim();
        if (name.isEmpty()) {
            nameET.setError("Please write your name here.");
            return false;
        } else {
            nameET.setError(null);
            return true;
        }
    }

    public boolean validateEmail() {

        String email = emailET.getText().toString().trim();

        if (email.isEmpty()) {
            emailET.setError("Please write your email address here.");
            return false;
        } else {
            emailET.setError(null);
            return true;
        }
    }

    public boolean validateMobileNo() {

        String mobileNo = mobileET.getText().toString().trim();

        if (mobileNo.isEmpty()) {
            mobileET.setError("Please Write your mobile number.");
            return false;
        } else if (mobileNo.length() < 11 | mobileNo.length() > 11) {
            mobileET.setError("Mobile number must be of 11 digits.");
            return false;
        } else {
            return true;
        }
    }

    public boolean validateProfessinalTag() {

        String pTag = professionalTagET.getText().toString().trim();

        if (pTag.isEmpty()) {
            professionalTagET.setError("Choose your professional tag.");
            return false;
        } else {
            return true;
        }
    }

    public boolean validateAboutYourSelf() {

        String aboutYourself = aboutYourselfET.getText().toString().trim();

        if (aboutYourself.isEmpty()) {
            aboutYourselfET.setError("Please write about yourself here.");
            return false;
        } else {
            aboutYourselfET.setError(null);
            return true;
        }
    }

    private void init() {
        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        mobileET = findViewById(R.id.mobileET);
        professionalTagET = findViewById(R.id.professionalTagET);
        aboutYourselfET = findViewById(R.id.aboutYourselfET);
        workExperienceET = findViewById(R.id.workExperienceET);
        qualificationET = findViewById(R.id.qualificationET);
        skillET = findViewById(R.id.skillET);
        experienceRV = findViewById(R.id.experienceRV);
        qualificationRV = findViewById(R.id.qualificationRV);
        skillRV = findViewById(R.id.skillRV);
        saveBtn = findViewById(R.id.saveBtn);
        profileImage = findViewById(R.id.circleImageView3);
        profileImagePicker = findViewById(R.id.profileImagePicker);
        saveQualificationBtn= findViewById(R.id.saveQualificationBtn);
        saveXpBtn = findViewById(R.id.saveXpBtn);
        saveSkillBtn = findViewById(R.id.saveSkillBtn);
        experienceExpandIV = findViewById(R.id.experienceExpandIV);
        qualificationExpandIV = findViewById(R.id.qualificationExpandIV);
        skillExpandIV = findViewById(R.id.skillExpandIV);
        personalInfoExpandIV = findViewById(R.id.personalInfoExpandIV);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        nameLayout = findViewById(R.id.nameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        mobileNoLayout = findViewById(R.id.mobileNoLayout);
        tagLayout = findViewById(R.id.tagLayout);
        descriptionLayoutET = findViewById(R.id.descriptionLayoutET);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        currentUser = firebaseAuth.getCurrentUser().getUid();

    }

    public void saveExperience(View view) {

        xp = workExperienceET.getText().toString();
        if(xp.isEmpty()){
            workExperienceET.setError("Add your experience here");
        }
        else
        {
            databaseReference.child("users").child(currentUser).child("about").child("experience").push()
                    .setValue(new Experience(xp));
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }


    }

    public void saveQualification(View view) {

        qua = qualificationET.getText().toString();
        if(qua.isEmpty()){
            qualificationET.setError("Add your qualification here");
        }
        else {
            databaseReference.child("users").child(currentUser).child("about").child("qualification").push()
                    .setValue(new Qualification(qua));
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        experienceAdapter.startListening();
        qualificationAdapter.startListening();
        skillAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        experienceAdapter.stopListening();
        qualificationAdapter.stopListening();
        skillAdapter.stopListening();
    }

    public void saveSkill(View view) {

        sk = skillET.getText().toString();
        if(sk.isEmpty()){
            skillET.setError("Add your skill here");
        }
        else {
            databaseReference.child("users").child(currentUser).child("about").child("skill").push()
                    .setValue(new Skill(sk));
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        }
    }


}
