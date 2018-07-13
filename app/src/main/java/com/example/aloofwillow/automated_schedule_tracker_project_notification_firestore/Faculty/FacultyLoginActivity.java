package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;


public class FacultyLoginActivity extends AppCompatActivity {
    EditText email, password;
    Button submit, register;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.loginProgressBar);
        firebaseFirestore=FirebaseFirestore.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        register = (Button) findViewById(R.id.register);
        auth=FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String EmailTxt = email.getText().toString();
                String PassTxt = password.getText().toString();


                if (!TextUtils.isEmpty(EmailTxt) && !TextUtils.isEmpty(PassTxt)) {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(EmailTxt,PassTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String token_id = FirebaseInstanceId.getInstance().getToken();
                                String user_id = auth.getCurrentUser().getUid();
                                Map<String,Object> tokenMap=new HashMap<>();
                                tokenMap.put("token_id",token_id);
                                firebaseFirestore.collection("Users").document(user_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(FacultyLoginActivity.this, FacultyMainActivity.class));
                                        progressBar.setVisibility(View.GONE);

                                    }
                                });

                        }
                            else{
                                Toast.makeText(FacultyLoginActivity.this,"Incorrect email or password",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                    }else{
                    Toast.makeText(FacultyLoginActivity.this, "Some fields are still empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyLoginActivity.this, FacultyRegisterActivity.class));

            }
        });


    }
}


