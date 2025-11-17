package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void getAllProjectsWithMemberId() {
        List<Project> projects = projectRepository.getAllProjectsWithMemberId(1);
        String title = projects.get(0).getTitle();
        assertNotNull(projects);
        assertEquals(3 ,projects.size() );
        assertEquals("Website Redesign", title);
    }

    @Test
    void delete() {
        projectRepository.delete(1);
        List<Project> projects = projectRepository.getAllProjectsWithMemberId(1);
        assertEquals(2 ,projects.size());

    }
}