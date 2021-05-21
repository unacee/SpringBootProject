package com.example.demo.control;

import com.example.demo.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageController {
    @GetMapping(path="/home")
    public String home(){
        return "home";
    }

    @GetMapping(path="/managePage")
    public String managePage(){
        return "dormAssign";
    }

    @GetMapping(path="/dormAssign")
    public String dormAssign(@RequestParam(required=false) Integer uid, @RequestParam(required=false) Integer dormId, Model model){
        String message;
        if((message=ManageService.assignDorm(uid,dormId)).equals("error")){
            return "error";
        }
        else{
            model.addAttribute("message",message);
            return "dormAssign";
        }
    }
}
