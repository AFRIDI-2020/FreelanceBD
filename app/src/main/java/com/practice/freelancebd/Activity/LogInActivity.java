package com.practice.freelancebd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.TashieLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.practice.freelancebd.R;

public class LogInActivity extends AppCompatActivity {

    private TextView termsOfServiceTV, forgetPasswordTV, registerTV;
    private Button logInBtn;
    private TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    private String email, password, givenEmail, givenPassword;
    private FirebaseAuth firebaseAuth;
    TashieLoader tashieLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        init();


        TashieLoader tashie = new TashieLoader(
                this, 5,
                30, 10,
                ContextCompat.getColor(this, R.color.whiteColor));

        tashie.setAnimDuration(500);
        tashie.setAnimDelay(100);
        tashie.setInterpolator(new LinearInterpolator());

        tashieLoader.addView(tashie);

        termsOfServiceTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, TermsOfServiceActivity.class));
            }
        });

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmail() | !validatePassword()) {
                    return;
                } else {

                    tashieLoader.setVisibility(View.VISIBLE);

                    email = emailTextInputLayout.getEditText().getText().toString().trim();
                    password = passwordTextInputLayout.getEditText().getText().toString().trim();

                    signIn(email, password);
                }
            }
        });

        forgetPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, ForgetPasswordActivity.class));
            }
        });

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });

    }

    private boolean validateEmail() {

        givenEmail = emailTextInputLayout.getEditText().getText().toString().trim();

        if (givenEmail.isEmpty()) {
            emailTextInputLayout.setError("Email is required!");
            return false;
        } else {
            emailTextInputLayout.setError(null);
            return true;
        }

    }

    private boolean validatePassword() {

        givenPassword = passwordTextInputLayout.getEditText().getText().toString().trim();

        if (givenPassword.isEmpty()) {
            passwordTextInputLayout.setError("Password is Required!");
            return false;
        } else {
            passwordTextInputLayout.setError(null);
            return true;
        }
    }

    private void signIn(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    if (firebaseAuth.getCurrentUser().isEmailVerified()) {

                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        tashieLoader.setVisibility(View.GONE);
                        Toast.makeText(LogInActivity.this, "Please verify your email address.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tashieLoader.setVisibility(View.GONE);
                    Toast.makeText(LogInActivity.this, "Invalid email or password or try again!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void init() {

        termsOfServiceTV = findViewById(R.id.termsOfServiceTV);
        termsOfServiceTV.setPaintFlags(termsOfServiceTV.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        logInBtn = findViewById(R.id.logInBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        registerTV = findViewById(R.id.registerTV);

        forgetPasswordTV = findViewById(R.id.forgetPasswordTV);
        tashieLoader = (TashieLoader) findViewById(R.id.tashieLoader);
    }
}
