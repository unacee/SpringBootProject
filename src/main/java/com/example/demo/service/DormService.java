package com.example.demo.service;

import com.example.demo.entity.Dorm;
import com.example.demo.entity.User;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.util.*;
import java.io.IOException;
import java.io.Reader;

public class DormService {
    public static String insert(Dorm dorm){
        String message=new String();
        Reader reader;
        //情况：地点未输入、地点已被占用
        try {
            reader = Resources.getResourceAsReader("mybatisConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(reader);
            SqlSession sqlSession = build.openSession();
            if(dorm.getLocation().equals("")){
                message+="location cannot be empty";
            }
            if(dorm.getLocation().isEmpty()){
                message+="Location cannot be empty\n";
            }
            else if(sqlSession.selectOne("com.example.demo.dao.DormDao.findByLocation", dorm.getLocation())!=null){
                message+="location has been occupied\n";
            }
            else {
                sqlSession.insert("com.example.demo.dao.DormDao.insertDorm", dorm);
                sqlSession.commit();
                message+="Dorm added: " + dorm.toString();
            }
            reader.close();
            sqlSession.close();
        }
        catch (IOException e){
            return "error";
        }
        return message;
    }

    public static void vacantUpdate(Dorm dorm){
        Reader reader;
        try {
            reader = Resources.getResourceAsReader("mybatisConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(reader);
            SqlSession sqlSession = build.openSession();
            dorm.setVacant(dorm.getVacant()-1);
            sqlSession.update("com.example.demo.dao.DormDao.updateVacant",dorm);
            sqlSession.commit();
            reader.close();
            sqlSession.close();
        }
        catch (IOException e){
        }
    }

    public static String selectAll(){
        String message=new String();
        Reader reader;
        try {
            reader = Resources.getResourceAsReader("mybatisConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(reader);
            SqlSession sqlSession = build.openSession();
            List<Dorm> dormList=sqlSession.selectList("com.example.demo.dao.DormDao.findAllDorm");
            if(dormList.isEmpty()){
                return "no dorm";
            }
            for(Dorm dorm : dormList){
                message+="dorm:\n";
                message+="*--------------------------------------------*\n";
                message+="id:"+dorm.getDormId()+", location:"+dorm.getLocation()+", vacant:"+dorm.getVacant()+"\n";
                List<User> userList=sqlSession.selectList("com.example.demo.dao.UserDao.findUserByDormId",dorm.getDormId());
                message+="----------------------------------------------\n";
                if(userList.isEmpty()){
                    message+="no user\n";
                }
                else {
                    message+="users:\n";
                    message+="*--------------------------------------------*\n";
                    for (User user : userList) {
                        message+="uid:"+user.getUid()+", name:"+user.getName()+"\n";
                    }
                }
                message+="\n|-------------------------------------------------------------------|\n";
            }
            reader.close();
            sqlSession.close();
        }
        catch (IOException e){
        }
        return message;
    }
}