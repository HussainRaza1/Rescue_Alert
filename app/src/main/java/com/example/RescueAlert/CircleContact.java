package com.example.RescueAlert;


class CircleContact {

    public String user_number, circle_number;

    CircleContact(String circle_number, String user_number) {
        this.circle_number = circle_number;
        this.user_number = user_number;
    }

    public CircleContact() {
    }

    public String getCircle_number() {
        return circle_number;
    }

    public String getUser_number() {
        return user_number;
    }
}

