package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer uid;
    private String name;
    private Integer dormId=-1;

    public Integer getUid() {
        return this.uid;
    }
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getDormId() {
        return this.dormId;
    }
    public void setDormId(Integer dormId) {
        this.dormId = dormId;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getUid() + "'" +
                ", name='" + getName() + "'" +
                ", dormId='" + getDormId() + "'" +
                "}";
    }
}