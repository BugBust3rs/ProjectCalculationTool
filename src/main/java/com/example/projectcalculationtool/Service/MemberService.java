package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMember(String email, String password) {
        List<Member> members = memberRepository.getMembers();
        for (Member member : members) {
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

    public Member getMemberWithEmail(String email) {
        //Find eksisterende member
        List<Member> members = memberRepository.getMembers();

        //Tjek om et med den givende email allerede findes
        for (Member member : members) {
            if (member.getEmail().equalsIgnoreCase(email)) {
                return member;
            }
        }
        return null;
    }

    public List<Member> getMembersWithProjectId(int projectId){
        return memberRepository.getMembersWithProjectId(projectId);
    }
}


