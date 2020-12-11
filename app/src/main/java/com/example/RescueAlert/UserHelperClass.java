package com.example.RescueAlert;

public class UserHelperClass {
    String mobileNumber, PIN, name, lat, lon;


    public UserHelperClass() {
    }


    public UserHelperClass(String mobileNumber, String PIN, String name, String lat, String lon) {
        this.mobileNumber = mobileNumber;
        this.PIN = PIN;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

}
