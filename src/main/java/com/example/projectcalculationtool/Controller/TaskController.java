package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Service.ProjectService;
import com.example.projectcalculationtool.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;

    public TaskController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping("/taskOverview/{projectId}")
    public String getTaskOverview(@PathVariable int projectId, Model model, HttpSession session) {
        int memberId = (int) session.getAttribute("memberId");
//        if (!isLoggedIn(session) || !projectService.memberHasProject(projectId, memberId)) {
////            return "redirect:/login";
////
//        }
        List<Task> tasks = taskService.getTasks(projectId);
        model.addAttribute("tasks",tasks);
        return "taskOverview";
    }
}

