package com.example.RescueAlert;

public class Contacts {
    String name;
    String number;

    public Contacts() {

    }

    Contacts(String number, String name) {
        this.number = number;
        this.name = name;
    }

 
    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

}
