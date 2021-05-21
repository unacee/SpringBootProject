package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dorm {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer dormId;
    private String location;
    private Integer vacant=4;
    public Integer getDormId() {
        return this.dormId;
    }
    public void setDormId(Integer dormId) {
        this.dormId = dormId;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getVacant() {
        return this.vacant;
    }
    public void setVacant(Integer vacant) {
        this.vacant = vacant;
    }

    @Override
    public String toString() {
        return "{" +
                " dormId='" + getDormId() + "'" +
                ", dormLocation='" + getLocation() + "'" +
                ", dormVacant='" + getVacant() + "'" +
                "}";
    }
}
