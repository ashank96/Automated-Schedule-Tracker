package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentFacultyActivity extends AppCompatActivity {
    ArrayList<String> freeSlotList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    TextView userDescp;
    ListView listView;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_faculty);
        Intent intent=getIntent();
        listView=findViewById(R.id.freeSlotsList);
        userDescp=findViewById(R.id.userDescription);
        firebaseFirestore=FirebaseFirestore.getInstance();
        final String facultyId=intent.getStringExtra("id");
        final String facultyName=intent.getStringExtra("name");
        String facultyPhone=intent.getStringExtra("phone");
        freeSlotList=intent.getStringArrayListExtra("timeList");
        userDescp.setText(facultyName+"'s free slots");
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, freeSlotList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendNotification(freeSlotList.get(i),facultyName,facultyId);
                return true;
            }
        });
    }

    private void sendNotification(final String time, final String fname, final String fid) {
        AlertDialog.Builder alert=new AlertDialog.Builder(this
        );
        alert.setTitle("Request "+fname+"");
        alert.setIcon(R.mipmap.ic_launcher_round);
        alert.setMessage("Do you want to request a meeting between "+time+"?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Map<String,Object> notificationMessage=new HashMap<>();
                notificationMessage.put("message","Can we please meet around "+time+" ?");
                notificationMessage.put("from", FirebaseAuth.getInstance().getUid());

                firebaseFirestore.collection("Users").document(fid).collection("Notifications").add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(StudentFacultyActivity.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogInterface.dismiss();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // Toast.makeText(StudentFacultyActivity.this, , Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog=alert.create();
        dialog.show();
    }

}
