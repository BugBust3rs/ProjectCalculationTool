package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Exceptions.MemberAlreadyAddedException;
import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Exceptions.UnauthorizedAccessException;
import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Repository.ProjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    public ProjectService(ProjectRepository projectRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }

    public List<Project> getAllProjectsWithMemberId(int memberId) {
        List<Project> projects = projectRepository.getAllProjectsWithMemberId(memberId);
        return setOverallEstimatedTimeForEveryProject(projects);
    }

    private List<Project> setOverallEstimatedTimeForEveryProject(List<Project> projects){
        for (Project project : projects){
            project.setEstimatedTime(taskService.getOverallEstimatedTime(project.getProjectId()));
        }
        return projects;
    }

    public void checkIfMembersProject(int projectId, int memberId, String exceptionMessage) {
        try {
            // if member does not have project, jdbcTemplate.queryForObject throws a DataAccessException
            projectRepository.memberHasProject(projectId, memberId);
        } catch (DataAccessException e){
            throw new UnauthorizedAccessException(exceptionMessage);
        }

    }

    public void deleteProject(int projectId) {
        projectRepository.delete(projectId);
    }

    public Project getProject(int projectId, int memberId) {
        List<Project> projects = projectRepository.getAllProjectsWithMemberId(memberId);
        for (Project project : projects) {
            if (project.getProjectId() == projectId) {
                return project;
            }
        }
        return null;
    }

    public void saveProject(Project project, int memberId) {
        projectRepository.addProject(project, memberId);
    }

    public void addMemberToProject(int projectId, int memberId) {
        try {
            projectRepository.addMemberToProject(projectId, memberId);
        } catch (DataAccessException e) {
            throw new MemberAlreadyAddedException("Member already added to that project", projectId);
        }
    }

    public Project getProjectById(int projectId) {
        return projectRepository.getProjectById(projectId);
    }


}
