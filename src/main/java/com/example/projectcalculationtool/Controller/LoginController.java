package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Service.MemberService;
import com.example.projectcalculationtool.Service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("projectplanner")
@Controller
public class LoginController {

    private final ProjectService projectService;
    private final MemberService memberService;

    public LoginController(ProjectService projectService, MemberService memberService) {
        this.projectService = projectService;
        this.memberService = memberService;
    }

    private boolean isLoggedInt(HttpSession session){
      session.getAttribute("member");
      return  session.getAttribute("member") != null;
    }

    @GetMapping("/landingPage")
    public String landingPage() {
        return "login";
    }

    @PostMapping("entrance")
    public String login(@ModelAttribute Member member,
                        HttpSession session){
        Member m1 = memberService.login(member.getEmail(), member.getPassword());
        if(m1 != null){
            session.setAttribute("member", m1);
            session.setMaxInactiveInterval(300);
            return  "redirect:/projectplanner/projectOverview";
        }

        return "redirect:/projectplanner/loging";
    }

    @GetMapping("/login")
    public String login(Model model) {
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
            return "redirect:/projectplanner/createMember";
        }
        memberService.createMember(member);
        return "redirect:/projectplanner/login";

    }




}
