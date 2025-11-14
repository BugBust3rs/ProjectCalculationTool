package com.example.projectcalculationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    private boolean isLoggedIn(HttpSession session) {
        session.getAttribute("Member");
        return session.getAttribute("Member") != null;
    }

}
