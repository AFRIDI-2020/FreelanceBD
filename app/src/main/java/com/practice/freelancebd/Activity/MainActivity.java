package com.practice.freelancebd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.practice.freelancebd.R;

public class MainActivity extends AppCompatActivity {

    private Button tempBtn;
    private FirebaseUser runningUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempBtn = findViewById(R.id.tempBtn);
        tempBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(runningUser != null){
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));

                }
                if(runningUser == null) {
                    startActivity(new Intent(MainActivity.this,FrontActivity.class));
                }

            }
        });
    }
}
