package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    public void create(Project project) {

    }


    public List<Project> getAllProjectsWithMemberId(int memberId)  {

        final String sql = """
                Select p.project_id, p.title, p.description, p.estimated_time
                FROM member_project mp 
                    JOIN project p ON p.project_id = mp.project_id 
                         WHERE member_id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Project project = new Project();
            project.setProjectId(rs.getInt("project_id"));
            project.setTitle(rs.getString("title"));
            project.setDescription(rs.getString("description"));
            project.setEstimatedTime(rs.getInt("estimated_time"));
            return project;
        }, memberId);
    }


    public void update(Object o) {

    }


    public void delete(int projectId) {
        String SQL = "DELETE FROM project WHERE project_id = ?";
        jdbcTemplate.update(SQL, projectId);
    }
}
