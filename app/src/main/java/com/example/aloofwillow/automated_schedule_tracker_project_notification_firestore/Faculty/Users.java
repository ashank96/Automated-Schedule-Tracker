package com.example.aloofwillow.automated_schedule_tracker_project_notification_firestore.Faculty;

import java.util.ArrayList;

public class Users {

    String name="",phone="";
    ArrayList<String> timeList=new ArrayList<>();

   public String User_id;


    public Users(){

    }
    public Users(String name, String phone, ArrayList<String> timeList) {
        this.name = name;
        this.phone = phone;
        this.timeList=timeList;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(ArrayList<String> timeList) {
        this.timeList = timeList;
    }
    public Users WithId(String id){
        this.User_id = id;
        return this;
    }


}
