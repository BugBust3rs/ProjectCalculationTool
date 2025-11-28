package com.example.projectcalculationtool.Exceptions;

public class MemberAlreadyAddedException extends RuntimeException {
    private final int projectId;

    public MemberAlreadyAddedException(String message, int projectId) {
        super(message);
        this.projectId = projectId;

    }

    public int getProjectId(){
        return projectId;
    }
}
