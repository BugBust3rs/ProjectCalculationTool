package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Subtask;
import com.example.projectcalculationtool.Model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubtaskRepository{

    private final JdbcTemplate jdbcTemplate;

    public SubtaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    public void create() {

    }


    public List<Subtask> getAllSubtasksWithTaskId(int taskId) {
        final String sql = "SELECT * FROM subTask WHERE task_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Subtask subtask = new Subtask();
            subtask.setSubtaskID(rs.getInt("subtask_id"));
            subtask.setTaskId(rs.getInt("task_id"));
            subtask.setEstimatedTime(rs.getInt("estimated_time"));
            subtask.setTitle(rs.getString("title"));
            subtask.setDescription(rs.getString("description"));
            return subtask;
        }, taskId);
    }


    public void update(Object o) {

    }


    public void delete(int id) {

    }
}
