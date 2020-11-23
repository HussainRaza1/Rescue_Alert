package com.example.RescueAlert;

public class FamilyContact {
    String number, user_ref;

    public FamilyContact() {

    }

    public FamilyContact(String number, String user_ref) {
        this.number = number;
        this.user_ref = user_ref;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setUser_ref(String user_ref) {
        this.user_ref = user_ref;
    }

    public String getUser_ref() {
        return user_ref;
    }
}

