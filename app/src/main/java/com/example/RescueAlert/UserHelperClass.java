package com.example.RescueAlert;

public class UserHelperClass {
    String mobileNumber, PIN, email;


    public UserHelperClass() {
    }


    public UserHelperClass(String mobileNumber, String PIN, String email) {
        this.mobileNumber = mobileNumber;
        this.PIN = PIN;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
