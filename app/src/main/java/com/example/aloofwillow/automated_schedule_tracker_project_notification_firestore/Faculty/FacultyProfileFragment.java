package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class FacultyProfileFragment extends Fragment {

    Button logoutButton;
    TextView name;
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;


    public FacultyProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutButton=view.findViewById(R.id.logout);
        progressBar=view.findViewById(R.id.progress);
        name=view.findViewById(R.id.name);
        firebaseFirestore=FirebaseFirestore.getInstance();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> tokenMapRemove=new HashMap<>();
                tokenMapRemove.put("token_id", FieldValue.delete());
                firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).update(tokenMapRemove)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(container.getContext(), FacultyLoginActivity.class));
                                getActivity().finish();

                            }
                        });

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                if(documentSnapshot.exists()){
                    name.setText("HELLO "+documentSnapshot.get("name").toString().toUpperCase());
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}