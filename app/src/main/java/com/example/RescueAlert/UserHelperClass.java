package com.example.RescueAlert;

public class UserHelperClass {
    String mobileNumber, PIN, name;


    public UserHelperClass() {
    }


    public UserHelperClass(String mobileNumber, String PIN, String email) {
        this.mobileNumber = mobileNumber;
        this.PIN = PIN;
        this.name = name;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String name) {
        this.name = name;
    }
}
