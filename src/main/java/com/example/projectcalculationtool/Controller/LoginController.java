package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Service.MemberService;
import com.example.projectcalculationtool.Model.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model) {

        // kigger i DB gennem service
        Member member = memberService.getMember(email, password);

        if (member == null) {
            model.addAttribute("error", "Forkert email eller password");
            return "login";
        }

        // Laver en session med memberId
        session.setAttribute("memberId", member.getMemberId());

        // giver en timeout tid som er sat til 300 sekunder
        session.setMaxInactiveInterval(300);

        // redirecter til dashboard hvis login lykkes
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
