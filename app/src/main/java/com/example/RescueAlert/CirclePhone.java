package com.example.RescueAlert;

public class CirclePhone {
    public String name;
    public String number;

    CirclePhone(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public CirclePhone() {
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

}