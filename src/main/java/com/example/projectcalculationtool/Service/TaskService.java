package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Status;
import com.example.projectcalculationtool.Model.Subtask;
import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Repository.SubtaskRepository;
import com.example.projectcalculationtool.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;

    public TaskService(TaskRepository taskRepository, SubtaskRepository subtaskRepository) {
        this.taskRepository = taskRepository;
        this.subtaskRepository = subtaskRepository;
    }

    public List<Task> getTasksByProjectId(int projectId) {
        return setTasksEstimatedTime(taskRepository.getAllTasksWithProjectId(projectId));
    }
    private List<Task> setTasksEstimatedTime(List<Task> tasks){
        for (Task task : tasks) {
            List<Subtask> subtasks = subtaskRepository.getAllSubtasksWithTaskId(task.getTaskId());
            task.setSubtasks(subtasks);
            if (!subtasks.isEmpty()){
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

    public int getProjectIdBySubtaskId(int subtaskId) {
        Subtask subtask = getSubtaskById(subtaskId);
        Task task = getTaskById(subtask.getTaskId());

        return task.getProjectId();
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

    public void createSubtask(Subtask subtask) {
        subtaskRepository.createSubTask(subtask);
    }

    public void createTask(Task task) {
        taskRepository.createTask(task);
    }

    public void updateTaskStatus(int taskId, Status status) {
        taskRepository.updateTaskStatus(taskId, status);
    }
    public void updateSubtaskStatus(int subtaskId, Status status) {
        subtaskRepository.updateSubtaskStatus(subtaskId, status);
    }
}
