package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.R;
import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Student.StudentFacultyActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {
    TextView NotifyData;
    Button yes, no;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        NotifyData = (TextView) findViewById(R.id.message);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        firebaseFirestore = FirebaseFirestore.getInstance();

        String message = getIntent().getStringExtra("message");
        final String from_id = getIntent().getStringExtra("from_user_id");
        String from_name = getIntent().getStringExtra("from_name");

        NotifyData.setText(from_name + ": '" + message + "'");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification("yes", from_id);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification("no", from_id);

            }
        });
    }

    void sendNotification(String msg, String to_id) {
        Map<String, Object> notificationMessage = new HashMap<>();

        notificationMessage.put("message", msg);
        notificationMessage.put("from", FirebaseAuth.getInstance().getUid());

        firebaseFirestore.collection("Students").document(to_id).collection("Notifications").add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(NotificationActivity.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
