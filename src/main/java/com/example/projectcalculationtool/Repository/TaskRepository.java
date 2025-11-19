package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository{

    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public void createTask(Task task) {
        String sql = "INSERT INTO task (title, description, estimatedTime, project_id (?, ?, ?, ?)";

        jdbcTemplate.update(sql,task.getTitle(), task.getDescription(), task.getEstimatedTime(), task.getProjectId());
    }


    public List<Object> getAll() {
        return List.of();
    }


    public void update(Object o) {

    }

}
