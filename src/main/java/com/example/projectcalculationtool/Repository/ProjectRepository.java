package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.GregorianCalendar;
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
    @Transactional
    public void addProject(Project project, int memberId){
        String sql = "INSERT INTO project (project_id, title, description, estimatedTime) VALUES (?, ?, ?, ?)";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, project.getProjectId());
            ps.setString(2, project.getTitle());
            ps.setString(3, project.getDescription());
            ps.setInt(4, project.getEstimatedTime());
            return ps;
        }, keyholder);

        int project_id = keyholder.getKey() != null ? keyholder.getKey().intValue() : -1;

        String sqlMember_project = "INSERT INTO member_project (member_id, project_id) VALUES (?, ?)";

        jdbcTemplate.update(sqlMember_project, memberId, project_id);


    }
}
