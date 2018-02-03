package com.example.a1405001.phonereceiver;

/**
 * Created by 1405001 on 02-02-2018.
 */

public class CallBackDetails {
    String moblieNumber, time, date;

    public CallBackDetails() {
    }

    public CallBackDetails(String s, String s1, String s2) {
        this.moblieNumber = s;
        this.time = s1;
        this.date = s2;
    }

    public String getMoblieNumber() {
        return moblieNumber;
    }

    public void setMoblieNumber(String moblieNumber) {
        this.moblieNumber = moblieNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
