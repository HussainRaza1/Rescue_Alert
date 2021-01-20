package com.example.RescueAlert;

public class FamilyContact {
    String close_number, user_ref, close_name;

    public FamilyContact() {

    }

    public FamilyContact(String close_number, String user_ref, String close_name) {
        this.close_number = close_number;
        this.close_name = close_name;
        this.user_ref = user_ref;
    }

    public String getClose_number() {
        return close_number;
    }

    public void setClose_number(String close_number) {
        this.close_number = close_number;
    }

    public String getClose_name() {
        return close_name;
    }

    public void setClose_name(String close_name) {
        this.close_name = close_name;
    }

    public String getUser_ref() {
        return user_ref;
    }

    public void setUser_ref(String user_ref) {
        this.user_ref = user_ref;
    }

}

