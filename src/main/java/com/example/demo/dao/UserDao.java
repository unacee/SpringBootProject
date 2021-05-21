package com.example.demo.dao;

import java.util.*;

import com.example.demo.entity.*;

public interface UserDao {
    User findByUid(Integer uid);
    List<User> findAllUser();
    List<User> findUserByDormId(Integer uid);
    void insertUser(User user);
    void deleteUser(Integer uid);
}
