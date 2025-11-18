package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    public List<Task> getTasks(int projectId) {
        List<Task> tasks = taskRepository.getAllTasks();
        List<Task> projectTasks = new ArrayList<>();
        for (Task task : tasks){
            if (task.getProjectId() == projectId){
                projectTasks.add(task);
            }
        }
        return projectTasks;
    }
}
