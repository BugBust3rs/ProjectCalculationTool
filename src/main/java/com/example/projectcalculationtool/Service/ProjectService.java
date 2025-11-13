package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjectsWithMemberId(int memberId){
       return projectRepository.getAllProjectsWithMemberId(memberId);
     }

    public boolean isMembersproject(int projectId, int memberId) {
        List<Project> membersprojects = projectRepository.getAllProjectsWithMemberId(memberId);
        for (Project project : membersprojects){
            if (project.getProjectId() == projectId){
                return true;
            }
        }
        return false;
    }

    public void deleteProject(int projectId) {
        projectRepository.delete(projectId);
    }
}
