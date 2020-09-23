package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.freelancebd.R;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private TextView termsOfServiceTV;
    private TextInputLayout userNameTextInputLayout, emailTextInputLayout,
            passwordTextInputLayout,confirmPasswordTextInputLayout;

    private String publicUserName, registrationEmail, registrationPassword, registrationConfirmPassword;
    private String username, email, password, confirmPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        termsOfServiceTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,TermsOfServiceActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validatePublicUserName() | !validateEmail() | !validatePassword() | !validateConfirmPassword() ){

                    return;
                }
                else {

                    progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.register_progress_dialouge);

                    username = userNameTextInputLayout.getEditText().getText().toString().trim();
                    email = emailTextInputLayout.getEditText().getText().toString().trim();
                    password = passwordTextInputLayout.getEditText().getText().toString().trim();
                    confirmPassword = confirmPasswordTextInputLayout.getEditText().getText().toString().trim();

                    register(username,email,password);
                }
            }
        });


    }

    private void register(final String username, final String email, String password) {



        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                progressDialog.setCancelable(true);

                                Intent intent = new Intent(RegisterActivity.this,RegisterVerificationActivity.class);

                                intent.putExtra("username",username);
                                startActivity(intent);

                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                    String userID = firebaseAuth.getCurrentUser().getUid();

                    DatabaseReference userRef = databaseReference.child("users").child(userID);

                    HashMap<String,Object> userInfo = new HashMap<>();
                    userInfo.put("email",email);
                    userInfo.put("name",username);

                    userRef.setValue(userInfo);

                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validatePublicUserName(){

        publicUserName = userNameTextInputLayout.getEditText().getText().toString().trim();

        if(publicUserName.isEmpty()){
            userNameTextInputLayout.setError("Public Username required!");
            return false;
        }
        else{
            userNameTextInputLayout.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){

        registrationEmail = emailTextInputLayout.getEditText().getText().toString().trim();

        if(registrationEmail.isEmpty()){
            emailTextInputLayout.setError("Email is required!");
            return false;
        }
        else{
            emailTextInputLayout.setError(null);
            return true;
        }
    }


    private boolean validatePassword(){

        registrationPassword = passwordTextInputLayout.getEditText().getText().toString().trim();

        if(registrationPassword.isEmpty()){
            passwordTextInputLayout.setError("Set a password!");
            return false;
        }
        else if(registrationPassword.length()<6)
        {
            passwordTextInputLayout.setError("Minimum six digits password required!");
            return false;
        }
        else{
            passwordTextInputLayout.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword(){

        registrationConfirmPassword = confirmPasswordTextInputLayout.getEditText().getText().toString().trim();

        if(registrationConfirmPassword.isEmpty()){
            confirmPasswordTextInputLayout.setError("Set a password!");
            return false;
        }
        else if(!registrationConfirmPassword.equals(registrationPassword)){
            confirmPasswordTextInputLayout.setError("Passwords don't match! Try again!");
            return false;
        }
        else{
            confirmPasswordTextInputLayout.setError(null);
            return true;
        }
    }



    private void init() {

        termsOfServiceTV = findViewById(R.id.termsOfServiceTV);
        termsOfServiceTV.setPaintFlags(termsOfServiceTV.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        userNameTextInputLayout = findViewById(R.id.userNameTextInputLayout);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        confirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout);
        registerBtn = findViewById(R.id.registerBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

}
