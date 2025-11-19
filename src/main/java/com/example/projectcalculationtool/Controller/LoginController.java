package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.projectcalculationtool.Service.MemberService;

@Controller
public class LoginController {
    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;

}
@GetMapping("/createMember")
public String showCreateMember(Model model) {
    Member member = new Member();
    model.addAttribute("member", member);
    return "createMember";
}

@PostMapping("/register")
public String registerMember(@ModelAttribute Member member) {
    if (memberService.doesMemberExists(member.getEmail())) {
        return "redirect:/createMember";
    }
    memberService.createMember(member);
    return "redirect:/login";

    }
}