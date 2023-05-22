package com.example.graduatecorner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduatecorner.authentication.LoginActivity;
import com.example.graduatecorner.authentication.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingScreenActivity extends AppCompatActivity {

    Button lgn_btn, register;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Should the user be logged in already, then simply forward to logged in screen
        if (firebaseUser != null){
            startActivity(new Intent(LandingScreenActivity.this, MainDashboardActivity.class));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_landing_screen);

        lgn_btn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_btn);

        lgn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingScreenActivity.this, LoginActivity.class));
                finish();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingScreenActivity.this, RegisterActivity.class));
                finish();
            }
        });




    }
}