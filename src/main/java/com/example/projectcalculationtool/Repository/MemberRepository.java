package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Member;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    public void create() {

    }


    public List<Object> getAll() {
        return List.of();
    }


    public void update(Object o) {

    }


    public void delete(int id) {

    }

    public void createMember(Member member){
        String sql = "INSERT INTO User (name, email, password) VALUES (?,?,?)";
        jdbcTemplate.update(sql,member.getName(),member.getEmail(),member.getPassword());
    }

    public List<Member> getMembers(){
        final String sql = "SELECT * FROM MEMBER";

        return jdbcTemplate.query(sql, (rs, rowNum) ->{
            Member member = new Member();
            member.setMemberId(rs.getInt("member_id"));
            member.setName(rs.getString("name"));
            member.setPassword(rs.getString("password"));
            member.setEmail(rs.getString("email"));
            return member;
        } );

    }




}
