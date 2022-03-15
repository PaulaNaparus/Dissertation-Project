package com.service.serviceapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "year")
    private String year;


    @Column(name = "month")
    private String month;

    @NotNull
    @Column(name = "day")
    private String day;

    @NotNull
    @Column(name = "hour")
    private String hour;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false,
            cascade = {
            CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private User user;

    public Reservation() {
    }

    public Reservation(String year, String month, String day, String hour, User user) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
