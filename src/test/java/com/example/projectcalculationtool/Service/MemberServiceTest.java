package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void loginReturnsMemberOnValidCredentials() {
        Member member = memberService.login("adam@hoppe.dk", "1234");

        assertNotNull(member);
        assertEquals("Adam Hoppe", member.getName());
        assertEquals(1, member.getMemberId());
    }

    @Test
    void loginReturnsNullOnInvalidCredentials() {
        Member member = memberService.login("ForkertEmail@adam.dk", "1234");

        assertNull(member);
    }
}