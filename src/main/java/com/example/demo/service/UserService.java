package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.Dorm;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class UserService {
    public static String insert(User user){
        String message=new String();
        Reader reader;
        Dorm dorm;
        try {
            reader = Resources.getResourceAsReader("mybatisConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(reader);
            SqlSession sqlSession = build.openSession();
            dorm=sqlSession.selectOne("com.example.demo.dao.DormDao.findByDormId",user.getDormId());
            //用户名空、房间不存在、房间满
            if(user.getName().isEmpty()){
                message+="UserName cannot be empty.\n";
            }
            else if(dorm==null){
                user.setDormId(-1);
                sqlSession.insert("com.example.demo.dao.UserDao.insertUser", user);
                sqlSession.commit();
                message+="dorm is non-existent.\nreplaced with empty dorm\n";
            }
            else if(dorm.getVacant()<=0){
                user.setDormId(-1);
                sqlSession.insert("com.example.demo.dao.UserDao.insertUser", user);
                sqlSession.commit();
                message+="dorm is full.\nreplaced with empty dorm\n";
            }
            else {
                sqlSession.insert("com.example.demo.dao.UserDao.insertUser", user);
                sqlSession.commit();
                DormService.vacantUpdate(dorm);
                message+="User added: " + user.toString();
            }
            reader.close();
            sqlSession.close();
        }
        catch (IOException e){
            return "error";
        }
        return message;
    }

    public static String selectAll(){
        String message=new String();
        Dorm dorm;
        try {
            Reader reader = Resources.getResourceAsReader("mybatisConfig.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory build = builder.build(reader);
            SqlSession sqlSession = build.openSession();
            List<User> userList=sqlSession.selectList("com.example.demo.dao.UserDao.findAllUser");
            if(userList.isEmpty()){
                return "no user";
            }
            for(User user : userList){
                dorm=sqlSession.selectOne("com.example.demo.dao.DormDao.findByDormId",user.getDormId());
                message+="user:\n";
                if(dorm!=null) {
                    message += "uid:" + user.getUid() + "\nname:" + user.getName() + "\ndorm:" + dorm.getLocation() + "\n";
                }
                else{
                    message+="no dorm\n";
                }
                message+="*--------------------------------------------*\n";
            }
            reader.close();
            sqlSession.close();
        }
        catch (IOException e){
        }
        return message;
    }
}
