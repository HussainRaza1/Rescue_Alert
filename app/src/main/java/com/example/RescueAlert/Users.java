package com.example.RescueAlert;

public class Users {

    private String userID,
            phone;

    public Users(String userID, String phone){
        this.userID = userID;
        this.phone = phone;
    }

    public  Users() {}

    public String getUserID() {
        return userID;
    }


    public String getPhone() {
        return phone;
    }
}