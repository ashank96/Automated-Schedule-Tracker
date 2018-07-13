package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty;


import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import java.util.ArrayList;
import java.util.List;


public class FacultyNotificationFragment extends Fragment{

    private List<Notification> notificationList;
    /*RecyclerView recyclerView;
    NotificationRecyclerViewAdapter notificationRecyclerViewAdapter;

    FirebaseAuth auth;
    String User_id;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference user_notification;
    public FacultyNotificationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        auth = FirebaseAuth.getInstance();
        User_id = auth.getCurrentUser().getUid();

        user_notification = databaseReference.child(User_id).child("Notifications");

        recyclerView = (RecyclerView)view.findViewById(R.id.recycleView);
        notificationList = new ArrayList<>();

        notificationRecyclerViewAdapter = new NotificationRecyclerViewAdapter(notificationList,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        recyclerView.setAdapter(notificationRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        notificationList.clear();

        user_notification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot NotificationList : dataSnapshot.getChildren()){


                    Notification notification = NotificationList.getValue(Notification.class);
                    notificationList.add(notification);
                    notificationRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

}
