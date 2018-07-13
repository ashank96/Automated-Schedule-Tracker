package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Student;

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

public class StudentRegisterActivity extends AppCompatActivity {
    EditText email, password, name,phone;
    Button submit, login;

    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = (ProgressBar) findViewById(R.id.registerProgressBar);
        email = (EditText) findViewById(R.id.email);
        name=(EditText)findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        phone=findViewById(R.id.phone);
        submit = (Button) findViewById(R.id.submit);
        login = (Button) findViewById(R.id.login);
        mAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Register();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }




    private void Register() {


        final String user_name = name.getText().toString();
        final String EmailTxt = email.getText().toString();
        String PassTxt = password.getText().toString();
        final String phno = phone.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(EmailTxt) && !TextUtils.isEmpty(PassTxt) && !TextUtils.isEmpty(phno)) {
            mAuth.createUserWithEmailAndPassword(EmailTxt, PassTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        final String userId = mAuth.getCurrentUser().getUid();
                        final String token_id= FirebaseInstanceId.getInstance().getToken();
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("name", user_name);
                        userMap.put("phone", phno);
                        userMap.put("token_id",token_id);

                        firestore.collection("Students").document(userId).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                sendToMain();
                            }
                        });

                    } else {
                        Toast.makeText(StudentRegisterActivity.this, "Error "+task.getException(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            startActivity(new Intent(StudentRegisterActivity.this, StudentMainActivity.class));
            finishAffinity();
        } else {
            Toast.makeText(StudentRegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
    void sendToMain(){
        Intent intent=new Intent(StudentRegisterActivity.this,StudentMainActivity.class);
        startActivity(intent);
        finish();
    }
}
