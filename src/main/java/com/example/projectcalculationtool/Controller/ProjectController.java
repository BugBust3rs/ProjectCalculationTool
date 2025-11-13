package com.example.projectcalculationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ProjectController {



    @GetMapping("/dashboard")
    public String getDashboard(){

        return "dashboard";
    }

}
