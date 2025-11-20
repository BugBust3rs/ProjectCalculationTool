package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository{

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Task> taskRowMapper = (rs, rowNum) -> {
        Task task = new Task();
        task.setTaskId(rs.getInt("task_id"));
        task.setProjectId(rs.getInt("project_id"));
        task.setEstimatedTime(rs.getInt("estimated_time"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        return task;
    };

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public void createTask(Task task) {
        String sql = "INSERT INTO task (title, description, estimated_time, project_id) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                task.getTitle(),
                task.getDescription(),
                task.getEstimatedTime(),
                task.getProjectId());
    }

    public List<Task> getAllTasksWithProjectId(int projectId) {
        final String sql = "SELECT * FROM task WHERE project_id = ?";

        return jdbcTemplate.query(sql, taskRowMapper, projectId);
    }


    public void update(Object o) {

    }


    public void delete(int id) {

    }

    public Task getTaskById(int taskId) {
        final String sql = "SELECT * FROM task WHERE task_id = ?";

        List<Task> tasks = jdbcTemplate.query(sql, taskRowMapper, taskId);
        return tasks.isEmpty() ? null : tasks.get(0);
    }

    public void deleteTaskById(int taskId) {
        final String sql = "DELETE FROM task WHERE task_id = ?";
        jdbcTemplate.update(sql, taskId);
    }
}
