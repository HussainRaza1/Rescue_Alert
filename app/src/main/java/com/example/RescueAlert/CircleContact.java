package com.example.RescueAlert;


class CircleContact {

    public String user_number, family_name, family_number;

    CircleContact(String circle_number, String family_name, String family_number) {
        this.family_number = circle_number;
        this.family_name = family_name;
        this.user_number = user_number;
    }

    public CircleContact() {
    }

    public String getCircle_number() {
        return family_number;
    }

    public String getCircle_user_name() {
        return family_name;
    }

    public String getUser_number() {
        return user_number;
    }
}