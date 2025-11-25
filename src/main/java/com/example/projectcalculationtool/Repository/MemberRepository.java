package com.example.projectcalculationtool.Repository;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.projectcalculationtool.Model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    private final RowMapper<Member> memberRowMapper = new RowMapper<Member>() {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member m = new Member();
            m.setMemberId(rs.getInt("member_id"));
            m.setName(rs.getString("name"));
            m.setEmail(rs.getString("email"));
            m.setPassword(rs.getString("password"));
            return m;
        }
    };


    public List<Member> getMembers(){
        String sql = "SELECT * FROM Member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    public Member saveMember(Member member){
        String sql = "INSERT INTO Member (name, email, password) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getName());
            ps.setString(3, member.getPassword());
            return ps;
        }), keyHolder);

        member.setMemberId(keyHolder.getKey().intValue());
        return member;
    }

    public List<Member> getMembersWithProjectId(int projectId){
        final String sql = """
                Select m.member_id , m.name, m.email, m.password
                FROM member_project mp 
                    JOIN member m ON m.member_id = mp.member_id 
                         WHERE project_id = ?
                """;

        return jdbcTemplate.query(sql, memberRowMapper, projectId);
    }
}
