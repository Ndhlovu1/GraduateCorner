package com.example.graduatecorner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.graduatecorner.authentication.LoginActivity;
import com.example.graduatecorner.authentication.ProfileActivity;
import com.example.graduatecorner.offers.MentoringActivity;
import com.example.graduatecorner.offers.NotesActivity;
import com.example.graduatecorner.offers.SkillsActivity;
import com.example.graduatecorner.offers.VideoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainDashboardActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DrawerLayout drawerLayout;
    GridLayout mainGrid;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null){
            //Toast.makeText(this, "You still logged in ", Toast.LENGTH_SHORT).show();

        }else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mainGrid = (GridLayout) findViewById(R.id.main_grid_Dash);
        setSingleEvent(mainGrid);

        drawerLayout = findViewById(R.id.drawerlayout);
        auth = FirebaseAuth.getInstance();
    }

    //Clickable redirects of the CardViews
    private void setSingleEvent(GridLayout mainGrid){
        //Enable the CLick for the cardviews

        for (int i = 0; i<mainGrid.getChildCount(); i++){

            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(v -> {
                if (finalI == 0){
                    startActivity(new Intent(MainDashboardActivity.this, ProfileActivity.class));
                }

                else if (finalI == 1){
                    startActivity(new Intent(MainDashboardActivity.this, SkillsActivity.class));
                }
                else if (finalI == 2){
                    startActivity(new Intent(MainDashboardActivity.this, VideoActivity.class));
                }
                else if (finalI == 3){
                    startActivity(new Intent(MainDashboardActivity.this, MentoringActivity.class));
                }

            });

        }



    }

    @Override
    protected void onPause() {
        closeDrawer(drawerLayout);
        super.onPause();
    }

    //Navigational Drawer Clicks
    private static void  openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    //Close Drawer
    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private static void redirectActivity(Activity activity, Class aClass) {

        //initialize intent
        Intent intent = new Intent(activity,aClass);
        //setFlag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start Activity
        activity.startActivity(intent);
    }

    public void clickSideBar(View view){
        openDrawer(drawerLayout);
    }

    public void clickMainDash(){
        redirectActivity(this, MainDashboardActivity.class);
    }

    public void clickProfile(){
        redirectActivity(this, ProfileActivity.class);
    }

    public void clickNotes(){
        redirectActivity(this, NotesActivity.class);
    }

    public void logout(View view){
        //Log the user out of their Account
        //Get Out of the App
        // System.exit(0);
        auth.signOut();
        finish();
    }













}