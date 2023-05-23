package com.example.graduatecorner.authentication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.graduatecorner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    TextView fnames, txt_mail, txt_cell, txt_website;
    ImageView email_pic, whatsapp_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawerlayoutprofile);

        txt_website = findViewById(R.id.support_link);
        email_pic = findViewById(R.id.clickEmail);
        whatsapp_pic = findViewById(R.id.whatsapp_link);

        auth = FirebaseAuth.getInstance();

        fnames = findViewById(R.id.fnames);
        txt_mail = findViewById(R.id.txt_mail);
        txt_cell = findViewById(R.id.txt_cell);

        //Declare the Cell Numbers
        String num_whatsapp = "+264813948555";
        String msg_whatsapp = "Please Enter Your Message.";

        //Whatsapp
        whatsapp_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean installed = isAppInstalled("com.whatsapp");

                if (installed){

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num_whatsapp+"&text="+msg_whatsapp));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ProfileActivity.this, "Whatsapp Required", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Web Page
        txt_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://www.winville.biz/");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

                try {
                    startActivity(Intent.createChooser(webIntent, "Website Activity"));
                }

                catch (ActivityNotFoundException e){

                    Toast.makeText(ProfileActivity.this, "No Website Opening Applications Found", Toast.LENGTH_SHORT).show();

                }

            }
        });

        //Email
        email_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"support@winville.biz"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject: Winville Study App");
                intent.putExtra(Intent.EXTRA_TEXT, "Email body");


                try {
                    startActivity(Intent.createChooser(intent, "Email Winville"));
                }

                catch (ActivityNotFoundException e){

                    Toast.makeText(ProfileActivity.this, "No Email Opening Application Found", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //Show user data
        showAllUserData();

    }


    private boolean isAppInstalled(String s) {
        PackageManager packageManager = getPackageManager();
        boolean isInstalled;

        try {
            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            isInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            isInstalled = false;
            e.printStackTrace();
        }
        return isInstalled;


    }

    private void showAllUserData() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String user_id = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Namibia").child(user_id);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String name = snapshot.child("Fullname").getValue().toString().toLowerCase();
                    String email = snapshot.child("Email").getValue().toString().toLowerCase();
                    String cellphone = snapshot.child("Cellphone").getValue().toString();

                    fnames.setText(name);
                    txt_mail.setText(email);
                    txt_cell.setText(cellphone);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfileActivity.this, "Sorry Data Unavailable", Toast.LENGTH_SHORT).show();

                }
            });



        }



    }



}