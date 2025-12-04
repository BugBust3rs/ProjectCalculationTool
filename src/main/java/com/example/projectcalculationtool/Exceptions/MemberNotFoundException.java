package com.example.projectcalculationtool.Exceptions;

public class MemberNotFoundException extends RuntimeException {
    private final int projectId;

    public MemberNotFoundException(String message, int projectId) {
        super(message);
        this.projectId = projectId;
    }

    public int getProjectId(){
        return projectId;
    }

}
