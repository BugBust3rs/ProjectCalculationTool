package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.*;
import com.example.projectcalculationtool.Repository.TaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepository, SubtaskRepository subtaskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.subtaskRepository = subtaskRepository;
        this.projectService = projectService;
    }

    public List<Task> getTasksByProjectId(int projectId) {
        List<Task> tasks = taskRepository.getAllTasksWithProjectId(projectId);
        for (Task task : tasks) {
            task.setSubtasks(subtaskRepository.getAllSubtasksWithTaskId(task.getTaskId()));
            if (!task.getSubtasks().isEmpty()){
                int overallEstimatedTimeForSubtasks = 0;
                for (Subtask subtask : task.getSubtasks()){
                    overallEstimatedTimeForSubtasks += subtask.getEstimatedTime();
                }
                task.setEstimatedTime(overallEstimatedTimeForSubtasks);
            }
        }

        return tasks;
    }

    public Task getTaskById(int taskId) {
        return taskRepository.getTaskById(taskId);
    }

    public void deleteTask(int taskId) {
        taskRepository.deleteTaskById(taskId);
    }

    public Subtask getSubtaskById(int subtaskId) {
        return subtaskRepository.getSubtaskById(subtaskId);
    }

    public void deleteSubtask(int subtaskId) {
        subtaskRepository.deleteSubTaskById(subtaskId);
    }

    public int getProjectId(int subtaskId) {
        return getTaskById(getSubtaskById(subtaskId).getTaskId()).getProjectId();
    }

    public int getOverallEstimatedTime(int projectId) {
        List<Task> tasks = getTasksByProjectId(projectId);
        int overallEstimatedTime = 0;
        for (Task task : tasks){
            if (task.getSubtasks().isEmpty()){
                overallEstimatedTime += task.getEstimatedTime();
            } else {
                for (Subtask subtask : task.getSubtasks()){
                    overallEstimatedTime += subtask.getEstimatedTime();
                }
            }
        }
        return overallEstimatedTime;
    }
    public void createSubtask(Subtask subtask) {subtaskRepository.createSubTask(subtask);}
    public void createTask(Task task) {
        taskRepository.createTask(task);
    }


    public List<Task> getTasksByMemberId(int memberId) {
        List<Task> tasks = taskRepository.getAllTasksWithMemberId(memberId);
        for(Task task : tasks) {
            Project project = projectService.getProjectById((task.getProjectId()));
            if (project != null) {
                task.setProjectTitle(project.getTitle());
            }
        }
        return tasks;
    }
}
