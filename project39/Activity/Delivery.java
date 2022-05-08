package com.example.project39.Activity;

public class Delivery {

    String name;
    String email;
    String address;
    String number;
    String date;
    String time;

    public Delivery(String name, String address, String number,String date,String time,String email ) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.number = number;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }
}
