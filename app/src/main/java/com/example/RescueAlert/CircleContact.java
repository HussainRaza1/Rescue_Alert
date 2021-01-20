package com.example.RescueAlert;


public class CircleContact {
    String user_number, family_name, family_number;

    public CircleContact() {
    }

    CircleContact(String family_number, String family_name, String user_number) {
        this.family_number = family_number;
        this.family_name = family_name;
        this.user_number = user_number;
    }


    public String getFamily_number() {
        return family_number;
    }

    public String getFamily_name() {
        return family_name;
    }

    public String getUser_number() {
        return user_number;
    }
}

