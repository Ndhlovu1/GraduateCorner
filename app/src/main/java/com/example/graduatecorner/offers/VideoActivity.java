package com.example.graduatecorner.offers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.graduatecorner.MainDashboardActivity;
import com.example.graduatecorner.R;
import com.example.graduatecorner.authentication.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

public class VideoActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);

        drawerLayout = findViewById(R.id.drawerlayout);

        VideoView videoView = findViewById(R.id.video_view);
        String videoPath = "android.resource://"+getPackageName() + "/"+R.raw.personal_growth;

        //Using the video
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        //Media Controllers
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);


    }

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