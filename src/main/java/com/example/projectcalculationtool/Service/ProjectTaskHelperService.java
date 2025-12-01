package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskHelperService {

    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectTaskHelperService(ProjectService projectService, TaskService taskService){
        this.projectService = projectService;
        this.taskService = taskService;
    }

    public List<Task> getTasksByMemberId(int memberId) {
        List<Task> tasks = taskService.getAllTasksWithMemberId(memberId);
        for(Task task : tasks) {
            Project project = projectService.getProjectById((task.getProjectId()));
            if (project != null) {
                task.setProjectTitle(project.getTitle());
            }
        }
        return tasks;
    }

    public List<Project> getAllProjectsWithMemberId(int memberId) {
        List<Project> projects = projectService.getAllProjectsWithMemberId(memberId);
        return setOverallEstimatedTimeForEveryProject(projects);
    }

    private List<Project> setOverallEstimatedTimeForEveryProject(List<Project> projects){
        for (Project project : projects){
            project.setEstimatedTime(taskService.getOverallEstimatedTime(project.getProjectId()));
        }
        return projects;
    }

}
