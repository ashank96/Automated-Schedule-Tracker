package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;
import com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FacultyFreeSlotsFragment extends Fragment{

    RangeBar seekBar;
    Button saveTime;
    TextView leftTime;
    TextView rightTIme;

    ListView freeTimeList;

    ArrayList<String> myFreeTimeList;
    ArrayAdapter<String> arrayAdapter;

    FirebaseFirestore firebaseFirestore;

    public FacultyFreeSlotsFragment() {

    }

    int getCurrentHoursMins() {
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH");
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("mm");


        String hr = dateFormat2.format(System.currentTimeMillis());
        String min = dateFormat3.format(System.currentTimeMillis());

        int hourmins = Integer.parseInt(min) + (Integer.parseInt(hr) * 60);
        return hourmins;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_users, container, false);

        firebaseFirestore=FirebaseFirestore.getInstance();

        seekBar = (RangeBar) view.findViewById(R.id.seekbar);

        leftTime = (TextView) view.findViewById(R.id.leftTime);

        rightTIme = (TextView) view.findViewById(R.id.rightTime);

        saveTime=view.findViewById(R.id.button);

        freeTimeList = (ListView) view.findViewById(R.id.listView);

        myFreeTimeList = new ArrayList<String>();

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, myFreeTimeList);

        freeTimeList.setAdapter(arrayAdapter);

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat1.format(System.currentTimeMillis());
        leftTime.setText(currentTime);


        int hourMinSecs = getCurrentHoursMins();

        seekBar.setTickEnd((float) hourMinSecs + 600);
        seekBar.setTickStart((float) hourMinSecs);
        seekBar.setTickInterval(1);
        seekBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(final RangeBar rangeBar, final int leftPinIndex, final int rightPinIndex, final String leftPinValue, final String rightPinValue) {
                rangeBar.setFormatter(new IRangeBarFormatter() {
                    @Override
                    public String format(String value) {

                        int valueInt = Integer.parseInt(value);
                        int timeDiff = valueInt - getCurrentHoursMins();
                        Calendar now = Calendar.getInstance();
                        Calendar nowRight = Calendar.getInstance();
                        Calendar nowLeft = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                        if (timeDiff > 0) {
                            now.add(Calendar.MINUTE, timeDiff);
                            value = df.format(now.getTime());
                        } else
                            value = df.format(System.currentTimeMillis());

                        int rTimeDiff = Integer.parseInt(rightPinValue) - getCurrentHoursMins();
                        int lTimeDiff = Integer.parseInt(leftPinValue) - getCurrentHoursMins();

                        if (rTimeDiff > 0) {
                            nowRight.add(Calendar.MINUTE, rTimeDiff);
                            rightTIme.setText(df.format(nowRight.getTime()));
                        } else if (rTimeDiff <= 0)
                            rightTIme.setText(df.format(System.currentTimeMillis()));


                        if (lTimeDiff > 0) {
                            nowLeft.add(Calendar.MINUTE, lTimeDiff);
                            leftTime.setText(df.format(nowLeft.getTime()));
                        } else if (lTimeDiff <= 0)
                            leftTime.setText(df.format(System.currentTimeMillis()));

                        return value;
                    }
                });
            }
        });


        saveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag=1;
                String time=leftTime.getText()+" - "+ rightTIme.getText();
                for(int i=0;i<myFreeTimeList.size();i++)
                    if(myFreeTimeList.get(i).contains(time)) {
                        Toast.makeText(getContext(), "Time already set", Toast.LENGTH_SHORT).show();
                        flag = 0;
                    }
                    if(flag==1) {
                        myFreeTimeList.add(time);
                        arrayAdapter.notifyDataSetChanged();
                        Map<String,Object>myMap=new HashMap<>();
                        myMap.put("timeList",myFreeTimeList);
                        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).set(myMap,SetOptions.merge());
                    }
            }
        });

        return view;
    }

    private void initializeTimeList() {
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()&& document.getData().containsKey("timeList")) {
                        ArrayList<String> arrayList=(ArrayList<String>) document.get("timeList");
                        for(int i=0;i<arrayList.size();i++)
                            myFreeTimeList.add(arrayList.get(i));
                        arrayAdapter.notifyDataSetChanged();
                    } else
                        Toast.makeText(getContext(), "No Free Slot set to Show", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), "get failed with "+task.getException(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        myFreeTimeList.clear();
        arrayAdapter.notifyDataSetChanged();
        initializeTimeList();
    }

    @Override
    public void onPause() {
        super.onPause();
        myFreeTimeList.clear();
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        myFreeTimeList.clear();
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myFreeTimeList.clear();
        arrayAdapter.notifyDataSetChanged();
    }

}
