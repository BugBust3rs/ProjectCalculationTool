package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Exceptions.UnauthorizedAccessException;
import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjectsWithMemberId(int memberId) {
        return projectRepository.getAllProjectsWithMemberId(memberId);
    }

    public void checkIfMembersProject(int projectId, int memberId, String exceptionMessage) {
//        List<Project> projects = projectRepository.getAllProjectsWithMemberId(memberId);
//
//        for (Project project : projects) {
//            if (project.getProjectId() == projectId){
//                return;
//            }
//        }
//        throw new UnauthorizedAccessException(exceptionMessage);
//
        Project project = projectRepository.getProjectWithProjectId(projectId);
        if (project == null){
            throw new UnauthorizedAccessException(exceptionMessage);
        }

    }

    public void deleteProject(int projectId) {
        projectRepository.delete(projectId);
    }

    public Project getProject(int projectId, int memberId) {
        List<Project> projects = projectRepository.getAllProjectsWithMemberId(memberId);
        for (Project project : projects){
            if (project.getProjectId() == projectId){
                return project;
            }
        }
        return null;
    }

    public void saveProject(Project project, int memberId){
        projectRepository.addProject(project, memberId);
    }


}
