package com.example.demo.control;

import com.example.demo.entity.User;

import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class UserController {
    @GetMapping(path="/userInsertPage")
    public String userInsertPage(){
        return "userInsert";
    }

    @GetMapping(path="/userInsert")
    public String userInsert(@RequestParam String name, @RequestParam(required=false) Integer dormId, Model model){
        User user=new User();
        user.setName(name);
        user.setDormId(dormId);
        String message;
        if((message=UserService.insert(user))=="error"){
            return "error";
        }
        else{
            model.addAttribute("message",message);
            return "userInsert";
        }
    }

    @GetMapping(path="/userShow")
    public String userShow(Model model){
        model.addAttribute("message", UserService.selectAll());
        return "userShow";
    }
}
