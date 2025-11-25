package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Exceptions.NotLoggedInException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public void checkIfLoggedIn(HttpSession session) {
        if(session.getAttribute("memberId") == null){
            throw new NotLoggedInException("You need to login first.");
        }
    }

    public void InvalidateSession(HttpSession session){
        session.invalidate();
    }
}
