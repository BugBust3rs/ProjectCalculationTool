package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Repository.TaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository,JdbcTemplate jdbcTemplate){
        this.taskRepository = taskRepository;
    }

    public void createTask(Task task) {
        taskRepository.createTask(task);
    }
}
