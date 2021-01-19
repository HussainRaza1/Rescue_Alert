package com.example.RescueAlert;


class CircleContact {

    public String user_number, circle_user_name, circle_number;

    CircleContact(String circle_number, String circle_user_name, String user_number) {
        this.circle_number = circle_number;
        this.circle_user_name = circle_user_name;
        this.user_number = user_number;
    }

    public CircleContact() {
    }

    public String getCircle_number() {
        return circle_number;
    }

    public String getCircle_user_name() {
        return circle_user_name;
    }

    public String getUser_number() {
        return user_number;
    }
}

