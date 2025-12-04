package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Service.LoginService;
import com.example.projectcalculationtool.Service.ProjectService;
import com.example.projectcalculationtool.Service.ProjectTaskHelperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class ProjectController {

    private final ProjectService projectService;
    private final LoginService loginService;
    private final ProjectTaskHelperService projectTaskHelperService;

    public ProjectController(ProjectService projectService, LoginService loginService,
                             ProjectTaskHelperService projectTaskHelperService) {
        this.projectService = projectService;
        this.loginService = loginService;
        this.projectTaskHelperService = projectTaskHelperService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model, HttpSession session) {
        loginService.checkIfLoggedIn(session);
        int memberId = (int) session.getAttribute("memberId");
        List<Project> projects = projectTaskHelperService.getAllProjectsWithMemberId(memberId);
        model.addAttribute("projects", projects);
        model.addAttribute("memberId", memberId);
        return "dashboard";
    }


    @PostMapping("/deleteProject/{projectId}")
    public String deleteProject(@PathVariable int projectId, HttpSession session) {
        loginService.checkIfLoggedIn(session);
        int memberId = (int) session.getAttribute("memberId");

        projectService.checkIfMembersProject(
                projectId, memberId, "You do not have permission to delete this project.");


        projectService.deleteProject(projectId);
        return "redirect:/dashboard";

    }


    @GetMapping("/createProject")
    public String createProject(Model model, HttpSession session) {
        loginService.checkIfLoggedIn(session);
        Project project = new Project();
        model.addAttribute("project", project);

        return "createProject";
    }

    @PostMapping("/createProject")
    public String createProject(@ModelAttribute Project project, HttpSession session) {

        loginService.checkIfLoggedIn(session);
        int memberId = (int) session.getAttribute("memberId");
        projectService.saveProject(project, memberId);
        return "redirect:/dashboard";

    }



}
