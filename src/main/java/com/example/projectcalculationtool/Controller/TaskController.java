package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Model.Subtask;
import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Service.ProjectService;
import com.example.projectcalculationtool.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        Project project = projectService.getProject(projectId, memberId);
        List<Task> tasks = taskService.getTasks(projectId);
        model.addAttribute("projectTitle", project.getTitle());
        model.addAttribute("projectId", project.getProjectId());
        model.addAttribute("tasks",tasks);

        return "taskOverview";
    }

    @PostMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable int taskId, HttpSession session){
        int memberId = (int) session.getAttribute("memberId");
        Task task = taskService.getTaskById(taskId);
//        if (!isLoggedIn(session) || !projectService.memberHasProject(task.getProjectId(), memberId)) {
////            return "redirect:/login";
////
//        }
        taskService.deleteTask(taskId);
        return "redirect:/taskOverview/" + task.getProjectId();
    }

    @PostMapping("/deleteSubtask/{subtaskId}")
    public String deleteSubtask(@PathVariable int subtaskId, HttpSession session){
        int memberId = (int) session.getAttribute("memberId");
        int projectId = taskService.getProjectId(subtaskId);
//        if (!isLoggedIn(session) || !projectService.memberHasProject(projectId, memberId)) {
////            return "redirect:/login";
////
//        }
        taskService.deleteSubtask(subtaskId);

        return "redirect:/taskOverview/" + projectId;
    }
}

