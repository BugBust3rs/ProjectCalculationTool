package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Service.LoginService;
import com.example.projectcalculationtool.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@Import(GlobalExceptionHandlerController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private MemberService memberService;
    @MockitoBean
    private LoginService loginService;

    @Test
    void loginSuccessRedirectsAndSetsSession() throws Exception {
        Member member = new Member();
        member.setMemberId(1);
        member.setEmail("alice@example.com");
        member.setPassword("password123");

        when(memberService.getMember("alice@example.com", "password123")).thenReturn(member);


        mockMvc.perform(post("/login")
                        .param("email", "alice@example.com")
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"))
                .andExpect(request().sessionAttribute("memberId", 1));
    }

    @Test
    void loginFailureShowsError() throws Exception {
        when(memberService.getMember("", "")).thenReturn(null);
        mockMvc.perform(post("/login")
                        .param("email", "ForkerEmail@alice.dk")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attribute("error", "Wrong email or password"));

    }
}