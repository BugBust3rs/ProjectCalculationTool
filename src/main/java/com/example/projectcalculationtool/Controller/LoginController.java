package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Service.LoginService;
import com.example.projectcalculationtool.Service.MemberService;
import com.example.projectcalculationtool.Model.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

    public LoginController(MemberService memberService, LoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;

    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute Member member,
                              HttpSession session,
                              Model model) {

        // kigger i DB gennem service
        Member memberFromDB = memberService.getMember(member.getEmail(), member.getPassword());

        if (memberFromDB == null) {
            model.addAttribute("error", "Forkert email eller password");
            return "login";
        }

        // Laver en session med memberId
        session.setAttribute("memberId", memberFromDB.getMemberId());

        // giver en timeout tid som er sat til 300 sekunder
        session.setMaxInactiveInterval(300);

        // redirecter til dashboard hvis login lykkes
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        loginService.InvalidateSession(session);
        return "redirect:/login";
    }

}
