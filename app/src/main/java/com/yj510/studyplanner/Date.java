package com.yj510.studyplanner;

public class Date {
    String date;
    String date_format;

    public Date(String date, String date_format) {
        this.date = date;
        this.date_format = date_format;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_format() {
        return date_format;
    }

    public void setDate_format(String date_format) {
        this.date_format = date_format;
    }
}
