package com.example.teamnova;

import static java.lang.Integer.parseInt;

import java.util.Comparator;

public class TravelData {

    private String date;
    private String time;
    private String spot;
    private String content;
    private String address;
    private String image;


    public TravelData(String date, String time, String spot, String content, String address, String image) {
        this.date = date;
        this.time = time;
        this.spot = spot;
        this.content = content;
        this.address = address;
        this.image = image;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
