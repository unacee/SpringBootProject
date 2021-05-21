package com.example.demo.control;

import com.example.demo.entity.Dorm;

import com.example.demo.service.DormService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DormController {
    @GetMapping(path="/dormInsertPage")
    public String dormInsertPage(){
        return "dormInsert";
    }

    @GetMapping(path="/dormInsert")
    public String dormInsert(@RequestParam String location, Model model){
        String message;
        Dorm dorm=new Dorm();
        dorm.setLocation(location);
        if((message=DormService.insert(dorm)).equals("error")){
            return "error";
        }
        else{
            model.addAttribute("message",message);
            return "dormInsert";
        }
    }

    @GetMapping(path="/dormShow")
    public String dormShow(Model model){
        model.addAttribute("message",DormService.selectAll());
        return "dormShow";
    }
}
