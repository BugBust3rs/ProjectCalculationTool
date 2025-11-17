package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Repository.MemberRepository;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Member getMember(String email, String password) {
        List<Member> members = repository.getMembers();
        for (Member member : members){
            if (member.getEmail().equals(email) && member.getPassword().equals(password)){
                return member;
            }
        }
        return null;
    }

    public boolean doesMemberExists(String email){
        List<Member> members = repository.getMembers();
        for(Member member : members){
            if(member.getEmail().equals(email)){
                return  true;
            }
        }
        return false;
    }

    public void createMember(Member member){
        repository.createMember(member);
    }
}
