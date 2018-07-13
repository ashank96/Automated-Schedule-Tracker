package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class FacultyPagerViewAdapter extends FragmentPagerAdapter {
    public FacultyPagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
            FacultyProfileFragment facultyProfileFragment = new FacultyProfileFragment();
            return facultyProfileFragment;

            case 1:
            FacultyFreeSlotsFragment facultyFreeSlotsFragment = new FacultyFreeSlotsFragment();
            return facultyFreeSlotsFragment;

            case 2:
                FacultyNotificationFragment facultyNotificationFragment = new FacultyNotificationFragment();
                return facultyNotificationFragment;

             default:
                 return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
