package com.example.projectcalculationtool.Repository;

import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Model.Task;
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
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void getAllTasksWithProjectId() {
        List<Task> tasks = taskRepository.getAllTasksWithProjectId(1);
        assertNotNull(tasks);
        assertEquals(2 , tasks.size());
    }

    @Test
    void delete() {
        taskRepository.deleteTaskById(1);
        List<Task> tasks = taskRepository.getAllTasksWithProjectId(1);
        assertEquals(1,tasks.size());
    }

    @Test
    void getTaskById() {
        Task task = taskRepository.getTaskById(1);
        assertEquals("Design Mockups" , task.getTitle());
        assertEquals("Create UI/UX mockups" , task.getDescription());
        assertEquals(20 , task.getEstimatedTime());
        assertEquals(1 , task.getProjectId());

    }
}