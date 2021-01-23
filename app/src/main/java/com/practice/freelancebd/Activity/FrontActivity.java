package com.practice.freelancebd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.practice.freelancebd.R;

public class FrontActivity extends AppCompatActivity {

    private TextView signInTV, signUpTV;
    private FirebaseUser runningUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        init();

        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogInActivity();
            }
        });


        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterActivity();
            }
        });
    }



    private void goToRegisterActivity() {

        startActivity(new Intent(FrontActivity.this,RegisterActivity.class));
    }

    private void goToLogInActivity() {

        startActivity(new Intent(FrontActivity.this,LogInActivity.class));
    }

    private void init() {

        signInTV = findViewById(R.id.signInTV);
        signUpTV = findViewById(R.id.signUpTV);
    }

}
