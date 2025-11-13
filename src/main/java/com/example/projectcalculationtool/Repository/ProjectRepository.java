package com.example.projectcalculationtool.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository implements CRUDInterface{

    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public void create(Object o) {

    }

    @Override
    public List<Object> getAll() {
        return List.of();
    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(int id) {

    }
}
