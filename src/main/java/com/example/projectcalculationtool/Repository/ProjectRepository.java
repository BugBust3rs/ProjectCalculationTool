package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository{

    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    public void create(Project project) {

    }


    public List<Object> getAll() {
        return List.of();
    }


    public void update(Object o) {

    }


    public void delete(int id) {

    }
}
