package com.service.serviceapp.model;

import com.service.serviceapp.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationDto {

    @Autowired
    UserDao userDao;

    private String year;
    private String month;
    private String day;
    private String hour;
    private String username;

    public ReservationDto() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
