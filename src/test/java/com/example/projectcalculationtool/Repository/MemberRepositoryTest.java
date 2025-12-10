package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    void createMember() {
        Member member = new Member();
        member.setEmail("morten@raissnia.dk");
        member.setName("morten");
        member.setPassword("1234");

        memberRepository.createMember(member);

        Member member1 = memberRepository.getMember(3);
        assertNotNull(member1);
        assertEquals("morten", member1.getName());
        assertEquals("morten@raissnia.dk", member1.getEmail());
        assertEquals("1234", member1.getPassword());
    }

    @Test
    void getMembers() {
        List<Member> members = memberRepository.getMembers();

        assertNotNull(members);
        assertEquals(2, members.size());
    }

    @Test
    void getMembersWithProjectId() {
        // project should have 2 members
        List<Member> members = memberRepository.getMembersWithProjectId(3);
        assertNotNull(members);
        assertEquals(2 ,members.size());

        // project should have 1 member
        List<Member> members1 = memberRepository.getMembersWithProjectId(1);
        assertNotNull(members);
        assertEquals(1, members1.size());
    }
}