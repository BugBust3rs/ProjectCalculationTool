package com.example.projectcalculationtool.Model;

public class Subtask {
    private int subtaskID;
    private String title;
    private String description;
    private int estimatedTime;
    private int taskId;
    private Integer memberId;
    private String memberName;
    private Status status;
    private String projectTitle;

    public Subtask () {}

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public int getSubtaskID() {
        return subtaskID;
    }

    public void setSubtaskID(int subtaskID) {
        this.subtaskID = subtaskID;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
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

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Integer getMemberId() { return memberId; }

    public void setMemberId(Integer memberId) { this.memberId = memberId; }

    public String getMemberName() { return memberName;}

    public void setMemberName (String memberName) { this.memberName = memberName; }
}
