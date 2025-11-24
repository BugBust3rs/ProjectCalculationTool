package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Repository.MemberRepository;
import org.springframework.stereotype.Service;

import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Repository.MemberRepository;

import java.util.List;


import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMember(String email, String password) {
        List<Member> members = memberRepository.getMembers();
        for (Member member : members){
            if (member.getEmail().equals(email) && member.getPassword().equals(password)) {
                return member;
            }
        }

        return null;
    }

    public boolean doesMemberExists(String email) {

        List<Member> members = memberRepository.getMembers();

        for (Member member : members) {
            if (member.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void createMember(Member member) {
        memberRepository.create(member);
    }
}


