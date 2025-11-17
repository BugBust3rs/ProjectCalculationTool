package com.example.projectcalculationtool.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.projectcalculationtool.Model.Member;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
