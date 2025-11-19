package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Service.LoginService;
import com.example.projectcalculationtool.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {

    private final LoginService loginService;
    private final TaskService taskService;

    public TaskController(LoginService loginService, TaskService taskService) {
        this.loginService = loginService;
        this.taskService = taskService;
    }

    @GetMapping("/createTask/{projectId}")
    public String createTask(@PathVariable int projectId, Model model, HttpSession session) {
//        if (!loginService.isLoggedIn(session)) {
//            return "redirect:/login";
//        }
        Task task = new Task();
        task.setProjectId(projectId);
//        int memberid = (int) session.getAttribute("memberId");
        model.addAttribute("task", task);
        return "createTask";

    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        if (!loginService.isLoggedIn(session)) {
            return "redirect:/login";
        }

        taskService.createTask(task);

        // gemme tasken i task repo
//        taskService.add
        return "redirect:/taskOverview/" + task.getProjectId();
    }
}
