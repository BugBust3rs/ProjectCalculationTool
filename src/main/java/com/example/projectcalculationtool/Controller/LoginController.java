package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Controller
public class LoginController {

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    private boolean isLoggedIn(HttpSession session){
      return  session.getAttribute("memberId") != null;
    }


    @PostMapping("/login")
    public String handleLogin(@ModelAttribute Member member,
                        HttpSession session, Model model){
        Member member1 = memberService.getMember(member.getEmail(), member.getPassword());
        if(member1 != null){
            session.setAttribute("memberId", member1.getMemberId());
            session.setMaxInactiveInterval(300);
            return  "redirect:/dashboard";
        }
        // error når man ikke kan logge ind
        //model.attribute("errorMessage", "forkert password eller email)"

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        return "login";
    }

    @GetMapping("/createUser")
    public String showCreateUser(Model model) {
        Member member = new Member() ;
        model.addAttribute("member", member);
        return "createMember";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Member member) {
        if (memberService.doesMemberExists(member.getEmail())) {
            return "redirect:/createMember";
        }
        memberService.createMember(member);
        return "redirect:/login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect/login";
    }




}
