package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model, HttpSession session) {
//        if (!isLoggedIn(session)) {
//            return "redirect:/login";
//        }
        session.setAttribute("memberId", 1);
        int memberId = (int) session.getAttribute("memberId");
        List<Project> projects = projectService.getAllProjectsWithMemberId(memberId);
        model.addAttribute("projects", projects);
        return "dashboard";
    }

    @PostMapping("/deleteProject/{projectId}")
    public String deleteWishlist(@PathVariable int projectId, HttpSession session) {
        int memberId = (int) session.getAttribute("memberId");
//        if (!isLoggedIn(session) || !projectService.memberHasProject(projectId, memberId)) {
//            return "redirect:/login";
//        }
        projectService.deleteProject(projectId);
        return "redirect:/dashboard";

    }

}
