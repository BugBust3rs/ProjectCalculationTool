package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Exceptions.NotLoggedInException;
import com.example.projectcalculationtool.Exceptions.UnauthorizedAccessException;
import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Service.LoginService;
import com.example.projectcalculationtool.Service.TaskService;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskController {

    private final LoginService loginService;
    private final TaskService taskService;
    private final ProjectService projectService;

    public TaskController(LoginService loginService, TaskService taskService, ProjectService projectService) {
        this.loginService = loginService;
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping("/createTask/{projectId}")
    public String createTask(@PathVariable int projectId, Model model, HttpSession session) {
        if (!loginService.isLoggedIn(session)) {
            throw new NotLoggedInException(
                    "Your session expired"
            );
        }
        Task task = new Task();
        task.setProjectId(projectId);
//        int memberid = (int) session.getAttribute("memberId");
        model.addAttribute("task", task);
        return "createTask";

    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        if (!loginService.isLoggedIn(session)) {
            throw new NotLoggedInException(
                    "Your session expired"
            );
        }

        taskService.createTask(task);

        // gemme tasken i task repo
//        taskService.add
        return "redirect:/taskOverview/" + task.getProjectId();
    }

    @GetMapping("/createSubtask/{taskId}")
    public String createSubTask(@PathVariable int taskId, Model model, HttpSession session) {
        if (!loginService.isLoggedIn(session)) {
            throw new NotLoggedInException(
                    "Your session expired"
            );
        }
        Subtask subtask = new Subtask();
        subtask.setTaskId(taskId);
        model.addAttribute("subtask", subtask);
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("projectId", task.getProjectId());
        return "createSubtask";
    }

    @PostMapping("/createSubtask/{projectId}")
    public String createSubTask(@PathVariable int projectId, @ModelAttribute Subtask subtask, HttpSession session) {
        if (!loginService.isLoggedIn(session)) {
            throw new NotLoggedInException(
                    "Your session expired"
            );
        }

        taskService.createSubtask(subtask);
        return "redirect:/taskOverview/" + projectId;
    }

    @GetMapping("/taskOverview/{projectId}")
    public String getTaskOverview(@PathVariable int projectId, Model model, HttpSession session) {
        if ((!loginService.isLoggedIn(session))) {
            throw new NotLoggedInException(
                    "Your session expired"
            );
        }
        int memberId = (int) session.getAttribute("memberId");
        if (projectService.memberDoesNotHaveProject(projectId, memberId)){
            throw new UnauthorizedAccessException(
                    "You do not have permission to see this project."
            );
        }
        Project project = projectService.getProject(projectId, memberId);
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        int overallEstimatedTime = taskService.getOverallEstimatedTime(projectId);
        model.addAttribute("projectTitle", project.getTitle());
        model.addAttribute("overallEstimatedTime", overallEstimatedTime);
        model.addAttribute("projectId", project.getProjectId());
        model.addAttribute("tasks",tasks);

        return "taskOverview";
    }

    @PostMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable int taskId, HttpSession session){
        int memberId = (int) session.getAttribute("memberId");
        Task task = taskService.getTaskById(taskId);
        if (!loginService.isLoggedIn(session)) {
            throw new NotLoggedInException(
                    "Your session expired"
            );
        }
        if (projectService.memberDoesNotHaveProject(task.getProjectId(), memberId)){
            throw new UnauthorizedAccessException(
                    "You do not have permission to delete this task."
            );
        }
        taskService.deleteTask(taskId);
        return "redirect:/taskOverview/" + task.getProjectId();
    }

    @PostMapping("/deleteSubtask/{subtaskId}")
    public String deleteSubtask(@PathVariable int subtaskId, HttpSession session){
        int memberId = (int) session.getAttribute("memberId");
        int projectId = taskService.getProjectIdBySubtaskId(subtaskId);
        if (!loginService.isLoggedIn(session)) {
            throw new NotLoggedInException(
                    "Your session expired"
            );
        }
        if (projectService.memberDoesNotHaveProject(projectId, memberId)){
            throw new UnauthorizedAccessException(
                    "You do not have permission to delete this subtask."
            );
        }
        taskService.deleteSubtask(subtaskId);

        return "redirect:/taskOverview/" + projectId;
    }


}

