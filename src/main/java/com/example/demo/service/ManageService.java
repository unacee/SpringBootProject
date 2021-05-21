package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.Dorm;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.Reader;

public class ManageService {
    public static String assignDorm(Integer uid,Integer dormId){
        String message=new String();
        Reader reader;
        User user;
        Dorm dorm;
        try {
            reader = Resources.getResourceAsReader("mybatisConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(reader);
            SqlSession sqlSession = build.openSession();
            user=sqlSession.selectOne("com.example.demo.dao.UserDao.findByUid",uid);
            dorm=sqlSession.selectOne("com.example.demo.dao.DormDao.findByDormId",dormId);
            //输入空、用户空、房间空、新房间与原先房间一样\房间满、分配房间（原先未分配）\更改房间（原先已分配）
            if(uid==null || dormId==null){
                message="uid or dormId is empty\n";
            }
            else if(user==null){
                message+="user is non-existent\n";
            }
            else if(dorm==null){
                message+="dorm is non-existent\n";
            }
            else if(dorm.getDormId().equals(user.getDormId())){
                message+="room has already been assigned.nothing change.\n";
            }
            else if(dorm.getVacant()<=0){
                message+="assignment failed.\n"+"dorm :"+dorm.getLocation()+", has been full.\n";
            }
            else if(user.getDormId().equals(-1)){
                message+="user :"+user.getName()+" previous dorm is null.\ndorm "+dorm.getLocation()+" is assigned\n";
                user.setDormId(dormId);
                sqlSession.update("com.example.demo.dao.ManageDao.assignDorm",user);
                sqlSession.commit();
                DormService.vacantUpdate(dorm);
            }
            else{
                message+="user :"+user.getName()+" dorm is changed to "+dorm.getLocation();
                user.setDormId(dormId);
                sqlSession.update("com.example.demo.dao.ManageDao.assignDorm",user);
                sqlSession.commit();
                DormService.vacantUpdate(dorm);
            }
            reader.close();
            sqlSession.close();
            return message;
        }
        catch (IOException e){
            return "error";
        }
    }
}
