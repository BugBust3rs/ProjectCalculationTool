package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Model.Project;
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

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model, HttpSession session) {
//        if (!isLoggedIn(session)) {
//            return "redirect:/login";
//        }
        //husk og fjern session.setattr den er for at kunne teste
        session.setAttribute("memberId", 1);
        int memberId = (int) session.getAttribute("memberId");
        List<Project> projects = projectService.getAllProjectsWithMemberId(memberId);
        model.addAttribute("projects", projects);
        return "dashboard";
    }

    @PostMapping("/deleteProject/{projectId}")
    public String deleteProject(@PathVariable int projectId, HttpSession session) {
        int memberId = (int) session.getAttribute("memberId");
//        if (!isLoggedIn(session) || !projectService.memberHasProject(projectId, memberId)) {
//            return "redirect:/login";
//        }
        projectService.deleteProject(projectId);
        return "redirect:/dashboard";

    }

    @GetMapping("/createProject")
    public String createProject(Model model, HttpSession session) {
        Project project = new Project();
        Member member = (Member) session.getAttribute("member");
        project.setProjectId(member.getChosenProject());
        model.addAttribute("project", project);

        return "createProject";
    }

    @PostMapping("/createProject")
    public String createProject(ModelAttribute Project project, HttpSession session) {

        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        projectService.saveProject(project);
        return "redirect:/dashboard" + project.getProjectId();

    }

}
