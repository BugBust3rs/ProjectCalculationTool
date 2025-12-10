package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(3, projects.size());
        assertEquals("Website Redesign", title);
    }

    @Test
    void deleteProject() {
        projectRepository.deleteProject(1);
        List<Project> projects = projectRepository.getAllProjectsWithMemberId(1);
        assertEquals(2, projects.size());
    }

    @Test
    void addProject() {
        Project p = new Project();
        p.setTitle("Test Project");
        p.setDescription("Integration test project");

        int memberId = 1;

        projectRepository.addProject(p, memberId);

        List<Project> projectList = projectRepository.getAllProjectsWithMemberId(memberId);

        assertEquals(4, projectList.size());
        // assertEquals("Test Project", projectList.get(3).getTitle());
        //assertEquals(12, projectList.contains(p.getEstimatedTime()));
        assertTrue(projectList.stream().anyMatch(project -> "Test Project".equals(project.getTitle())));
        assertTrue(projectList.stream().anyMatch(project -> "Integration test project".equals(project.getDescription())));

    }

    @Test
    void addMemberToProject() {

        int projectId = 5;
        int memberId = 1;


        // ACT
        projectRepository.addMemberToProject(projectId, memberId);

        // Hent alle medlemmer på projektet
        List<Project> projectList = projectRepository.getAllProjectsWithMemberId(memberId);

        // ASSERT — samme stil som din addProject() test
        assertEquals(4, projectList.size());
    }
}