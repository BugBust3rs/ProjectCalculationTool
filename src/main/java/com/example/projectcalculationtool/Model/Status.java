package com.example.projectcalculationtool.Model;

public enum Status {
    INPROGRESS("In progress"),
    DONE("Done"),
    BACKLOG("Backlog");

    private final String label;

    Status(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
