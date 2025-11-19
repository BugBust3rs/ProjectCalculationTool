package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Repository.TaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public TaskService(TaskRepository taskRepository, SubtaskRepository subtaskRepository) {
        this.taskRepository = taskRepository;
        this.subtaskRepository = subtaskRepository;
    }

    public List<Task> getTasksByProkectId(int projectId) {
        List<Task> tasks = taskRepository.getAllTasksWithProjectId(projectId);
        for (Task task : tasks) {
            task.setSubtasks(subtaskRepository.getAllSubtasksWithTaskId(task.getTaskId()));
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
        List<Task> tasks = getTasksByProkectId(projectId);
        int overallEstimatedTime = 0;
        for (Task task : tasks){
            if (task.getSubtasks() == null){
                overallEstimatedTime += task.getEstimatedTime();
            } else {
                for (Subtask subtask : task.getSubtasks()){
                    overallEstimatedTime += subtask.getEstimatedTime();
                }
            }
        }
        return overallEstimatedTime;
    }

    public void createTask(Task task) {
        taskRepository.createTask(task);
    }
}
