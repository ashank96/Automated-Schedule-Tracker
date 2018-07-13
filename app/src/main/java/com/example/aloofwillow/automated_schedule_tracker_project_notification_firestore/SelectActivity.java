package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty.FacultyLoginActivity;
import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty.FacultyMainActivity;
import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Student.StudentMainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        CircleImageView faculty=findViewById(R.id.faculty);
        CircleImageView student=findViewById(R.id.student);
        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectActivity.this, FacultyMainActivity.class));
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectActivity.this, StudentMainActivity.class));
            }
        });
    }
}
