package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.practice.freelancebd.R;

public class RegisterVerificationActivity extends AppCompatActivity {

    private TextView givenName;
    private String userName,email,password,givenEmail,givenPassword;
    private TextInputLayout emailTextInputlayout, passwordTextInputLayout;
    private Button logInBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_verification);

        init();

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateEmail() | !validatePassword()){
                    return;
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);

                    email = emailTextInputlayout.getEditText().getText().toString().trim();
                    password = passwordTextInputLayout.getEditText().getText().toString().trim();

                    signIn(email,password);
                }
            }
        });


    }

    private boolean validateEmail(){

        givenEmail = emailTextInputlayout.getEditText().getText().toString().trim();

        if(givenEmail.isEmpty()){
            emailTextInputlayout.setError("Email is required!");
            return false;
        }
        else
        {
            emailTextInputlayout.setError(null);
            return true;
        }

    }

    private boolean validatePassword(){

        givenPassword = passwordTextInputLayout.getEditText().getText().toString().trim();

        if(givenPassword.isEmpty()){
            passwordTextInputLayout.setError("Password is Required!");
            return false;
        }

        else
        {
            passwordTextInputLayout.setError(null);
            return true;
        }
    }

    private void signIn(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    if(firebaseAuth.getCurrentUser().isEmailVerified()){



                        Intent intent = new Intent(RegisterVerificationActivity.this,EditAboutUserActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RegisterVerificationActivity.this, "Please verify your email address.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterVerificationActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {

        givenName = findViewById(R.id.givenName);
        userName = getIntent().getStringExtra("username");
        givenName.setText(userName);
        logInBtn = findViewById(R.id.logInBtn);
        emailTextInputlayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
    }
}
