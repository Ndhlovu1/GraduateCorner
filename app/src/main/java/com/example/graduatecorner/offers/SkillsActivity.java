package com.example.graduatecorner.offers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.graduatecorner.PdfViewerBook;
import com.example.graduatecorner.R;
import com.google.firebase.auth.FirebaseAuth;

public class SkillsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    FirebaseAuth auth;
    Button btn_bk1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        drawerLayout = findViewById(R.id.drawerlayoutSkills);
        btn_bk1 = findViewById(R.id.viewBook);
        auth = FirebaseAuth.getInstance();

        btn_bk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SkillsActivity.this, PdfViewerBook.class));
            }
        });




    }
}