package com.example.projectcalculationtool.Repository;

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


    public List<Object> getAll() {
        return List.of();
    }


    public void update(Object o) {

    }


    public void delete(int id) {

    }
}
