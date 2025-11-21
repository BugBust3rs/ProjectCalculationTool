package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Service.LoginService;
import com.example.projectcalculationtool.Service.ProjectService;
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
    public ProjectController(ProjectService projectService, LoginService loginService) {
        this.projectService = projectService;
        this.loginService = loginService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model, HttpSession session) {
        if (!loginService.isLoggedIn(session)) {
            return "redirect:/login";
        }

        int memberId = (int) session.getAttribute("memberId");
        List<Project> projects = projectService.getAllProjectsWithMemberId(memberId);
        model.addAttribute("projects", projects);
        return "dashboard";
    }


    @PostMapping("/deleteProject/{projectId}")
    public String deleteProject(@PathVariable int projectId, HttpSession session) {
        int memberId = (int) session.getAttribute("memberId");
        if (!loginService.isLoggedIn(session)|| !projectService.memberHasProject(projectId, memberId)) {
            return "redirect:/login";
        }
        projectService.deleteProject(projectId);
        return "redirect:/dashboard";

    }

    @GetMapping("/createProject")
    public String createProject(Model model, HttpSession session) {
        Project project = new Project();
        model.addAttribute("project", project);

        return "createProject";
    }

    @PostMapping("/createProject")
    public String createProject(@ModelAttribute Project project, HttpSession session) {

        if (!loginService.isLoggedIn(session)) {
            return "redirect:/login";
        }
        int memberId = (int) session.getAttribute("memberId");
        projectService.saveProject(project, memberId);
        return "redirect:/dashboard";

    }

}
