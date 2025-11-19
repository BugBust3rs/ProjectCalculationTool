package com.example.projectcalculationtool.Service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("memberId") != null;
    }

    public void InvalidateSession(HttpSession session){
        session.invalidate();
    }
}
