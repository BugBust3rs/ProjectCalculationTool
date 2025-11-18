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

    public void create() {

    }

    public List<Task> getAllTasks() {
        final String sql = "SELECT * FROM task";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Task task = new Task();
            task.setTaskId(rs.getInt("task_id"));
            task.setProjectId(rs.getInt("project_id"));
            task.setEstimatedTime(rs.getInt("estimated_time"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            return task;
        });
    }


    public void update(Object o) {

    }


    public void delete(int id) {

    }
}
