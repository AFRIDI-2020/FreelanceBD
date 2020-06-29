package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.practice.freelancebd.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextInputLayout emailTextInputLayout;
    private TextView followEmailMsgTV,textView1;
    private Button sendEmail, logInBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        init();

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.sendPasswordResetEmail(emailTextInputLayout.getEditText().getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            textView1.setVisibility(View.GONE);
                            sendEmail.setVisibility(View.GONE);
                            emailTextInputLayout.setVisibility(View.GONE);
                            followEmailMsgTV.setVisibility(View.VISIBLE);
                            logInBtn.setVisibility(View.VISIBLE);
                            logInBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ForgetPasswordActivity.this, LogInActivity.class));
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(ForgetPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void init() {

        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        followEmailMsgTV = findViewById(R.id.followEmailMsgTV);
        sendEmail = findViewById(R.id.sendEmailBtn);
        logInBtn = findViewById(R.id.logInBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        textView1 = findViewById(R.id.textView1);
    }
}
