package com.barattoManager.ui.mvc.dialogs.newMeet;

import com.barattoManager.ui.mvc.base.BaseModel;

import java.util.ArrayList;

public class NewMeetModel implements BaseModel {

    private String city;
    private String square;
    private String[] days;
    private String startTime;
    private String endTime;
    private String daysBeforeExpire;

    public NewMeetModel() {
        this.city = "";
        this.square = "";
        this.days = new String[]{};
        this.startTime = "";
        this.endTime = "";
        this.daysBeforeExpire = "";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDaysBeforeExpire() {
        return daysBeforeExpire;
    }

    public void setDaysBeforeExpire(String daysBeforeExpire) {
        this.daysBeforeExpire = daysBeforeExpire;
    }
}
