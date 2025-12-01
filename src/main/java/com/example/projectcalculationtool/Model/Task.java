package com.example.projectcalculationtool.Model;

import java.util.List;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private int estimatedTime;
    private int projectId;
    private List<Subtask> subtasks;
    private String projectTitle;

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public Task() {}

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}
