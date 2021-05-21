package com.example.demo.dao;

import com.example.demo.entity.Dorm;

import java.util.List;

public interface DormDao {
    Dorm findByDormId(Integer dormId);
    Dorm findByLocation(String location);
    List<Dorm> findAllDorm();
    List<Dorm> findAvailableDorm(Integer vacant);
    void insertDorm(Dorm dorm);
    void deleteDorm(Integer dormId);
    void updateVacant(Dorm dorm);
}
