package com.example.graduatecorner.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.graduatecorner.MainDashboardActivity;
import com.example.graduatecorner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText full_name, email, institution, cellphone,password;
    Button registerbtn;
    TextView login;

    FirebaseAuth auth;
    DatabaseReference ref;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        full_name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        institution = findViewById(R.id.institution);
        cellphone = findViewById(R.id.cellphone);
        password = findViewById(R.id.password);

        login = findViewById(R.id.txt_login);
        registerbtn = findViewById(R.id.register_btn);

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setTitle("Registering");
                pd.setMessage("Please wait...");
                pd.show();

                String str_full_name = full_name.getText().toString();
                String str_email = email.getText().toString();
                String str_institution = institution.getText().toString();
                String str_cellphone = cellphone.getText().toString();
                String str_password = password.getText().toString();

                if (TextUtils.isEmpty(str_full_name)  || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_institution) || TextUtils.isEmpty(str_password) || TextUtils.isEmpty(str_cellphone)) {

                    Toast.makeText(RegisterActivity.this, "All fields are needed", Toast.LENGTH_SHORT).show();

                }

                else if (str_password.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Password can't be less than 8 Characters", Toast.LENGTH_SHORT).show();
                }

               else {
                   register(str_full_name, str_email, str_institution, str_cellphone, str_password);
                }

            }
        });

    }

    private void register(String str_full_name, String str_email, String str_institution,String str_password, String str_cellphone){

        auth.createUserWithEmailAndPassword(str_email, str_password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    String userId = firebaseUser.getUid();
                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Namibia").child(userId);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("Full_Name", str_full_name);
                    hashMap.put("Email",str_email);
                    hashMap.put("Institution",str_institution);
                    hashMap.put("Cellphone",str_cellphone);
                    hashMap.put("Password", str_password);

                    ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pd.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, MainDashboardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        }
                    });
                }

                            else{
                                Toast.makeText(RegisterActivity.this, "You cannot register with this email.", Toast.LENGTH_SHORT).show();

                }
                            pd.dismiss();

            }
        });

    }











}