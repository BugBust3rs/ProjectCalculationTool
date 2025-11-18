package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Subtask;
import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Repository.SubtaskRepository;
import com.example.projectcalculationtool.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;

    public TaskService(TaskRepository taskRepository, SubtaskRepository subtaskRepository){
        this.taskRepository = taskRepository;
        this.subtaskRepository = subtaskRepository;
    }

    public List<Task> getTasks(int projectId) {
        List<Task> tasks = taskRepository.getAllTasksWithProjectId(projectId);
        for (Task task : tasks){
            task.setSubtasks(subtaskRepository.getAllSubtasksWithTaskId(task.getTaskId()));
        }

        return tasks;
    }
}
