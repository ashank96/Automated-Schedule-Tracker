package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Student;

import android.content.Intent;
import android.os.Bundle;

import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty.FacultyLoginActivity;
import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty.Users;
import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentMainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    ListView facultyListView;
    Button logoutButton;
    FirebaseFirestore firebaseFirestore;
    public static ArrayList<Users> facultyObjectList;
    ArrayList<String>facultyList;
    ArrayAdapter<String>arrayAdapter;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser==null){
            sendToLogin();
        }else {
            facultyList.clear();
            firebaseFirestore.collection("Users").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String userId = doc.getDocument().getId();
                            Users faculty = doc.getDocument().toObject(Users.class).WithId(userId);
                            facultyObjectList.add(faculty);
                            facultyList.add(faculty.getName());
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

    }

    private void sendToLogin() {
        Intent intent=new Intent(StudentMainActivity.this,StudentLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        logoutButton=findViewById(R.id.logout);
        mAuth=FirebaseAuth.getInstance();
        facultyListView=findViewById(R.id.facultyList);
        firebaseFirestore=FirebaseFirestore.getInstance();
        facultyObjectList=new ArrayList<>();
        facultyList=new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, facultyList);
        facultyListView.setAdapter(arrayAdapter);
        facultyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(StudentMainActivity.this,StudentFacultyActivity.class);
                intent.putStringArrayListExtra("timeList",facultyObjectList.get(i).getTimeList());
                intent.putExtra("id",facultyObjectList.get(i).User_id);
                intent.putExtra("name",facultyObjectList.get(i).getName());
                intent.putExtra("phone",facultyObjectList.get(i).getPhone());
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String,Object> tokenMapRemove=new HashMap<>();
                tokenMapRemove.put("token_id", FieldValue.delete());
                firebaseFirestore.collection("Students").document(FirebaseAuth.getInstance().getUid()).update(tokenMapRemove)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(StudentMainActivity.this, StudentLoginActivity.class));
                                finish();

                            }
                        });


            }
        });



    }
}
