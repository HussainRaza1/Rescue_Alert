package com.example.RescueAlert;

class Contacts {
    public String name;
    public String number;

    Contacts(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public Contacts() {
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

}
