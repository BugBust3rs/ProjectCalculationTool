package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.*;
import com.example.projectcalculationtool.Service.*;
import jakarta.servlet.http.HttpSession;
import com.example.projectcalculationtool.Model.Task;
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
    private final MemberService memberService;
    public TaskController(LoginService loginService, TaskService taskService, ProjectService projectService, MemberService memberService) {
        this.loginService = loginService;
        this.taskService = taskService;
        this.projectService = projectService;
        this.memberService = memberService;
    }

    @GetMapping("/createTask/{projectId}")
    public String createTask(@PathVariable int projectId, Model model, HttpSession session) {
        if (!loginService.isLoggedIn(session)) {
            return "redirect:/login";
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
            return "redirect:/login";
        }

        taskService.createTask(task);

        // gemme tasken i task repo
//        taskService.add
        return "redirect:/taskOverview/" + task.getProjectId();
    }

    @GetMapping("/createSubtask/{taskId}")
    public String createSubTask(@PathVariable int taskId, Model model, HttpSession session) {
        if (!loginService.isLoggedIn(session)) {
            return "redirect:/login";
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
            return "redirect:/login";
        }

        taskService.createSubtask(subtask);
        return "redirect:/taskOverview/" + projectId;
    }

    @GetMapping("/taskOverview/{projectId}")
    public String getTaskOverview(@PathVariable int projectId, Model model, HttpSession session) {
        int memberId = (int) session.getAttribute("memberId");
        if (!loginService.isLoggedIn(session) || !projectService.memberHasProject(projectId, memberId)) {
//            return "redirect:/login";
//
        }
        Project project = projectService.getProject(projectId, memberId);
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        int overallEstimatedTime = taskService.getOverallEstimatedTime(projectId);
        model.addAttribute("projectTitle", project.getTitle());
        model.addAttribute("overallEstimatedTime", overallEstimatedTime);
        model.addAttribute("projectId", project.getProjectId());
        model.addAttribute("tasks",tasks);
        Member member = new Member();
        model.addAttribute("member", member);
        return "taskOverview";
    }

    @PostMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable int taskId, HttpSession session){
        int memberId = (int) session.getAttribute("memberId");
        Task task = taskService.getTaskById(taskId);
        if (!loginService.isLoggedIn(session) || !projectService.memberHasProject(task.getProjectId(), memberId)) {
//            return "redirect:/login";
//
        }
        taskService.deleteTask(taskId);
        return "redirect:/taskOverview/" + task.getProjectId();
    }

    @PostMapping("/deleteSubtask/{subtaskId}")
    public String deleteSubtask(@PathVariable int subtaskId, HttpSession session){
        int memberId = (int) session.getAttribute("memberId");
        int projectId = taskService.getProjectId(subtaskId);
        if (!loginService.isLoggedIn(session) || !projectService.memberHasProject(projectId, memberId)) {
//            return "redirect:/login";
//
        }
        taskService.deleteSubtask(subtaskId);

        return "redirect:/taskOverview/" + projectId;
    }

    @PostMapping("/inviteMember/{projectId}")
    public String inviteMemberToProject(@PathVariable int projectId, @ModelAttribute Member member, HttpSession session) {

        if (!loginService.isLoggedIn(session)) {
            return "redirect:/login";
        }

        int memberId = (int) session.getAttribute("memberId");


        // add member to the project by email
        // hvis member er == null så return til taskoverview med en bruger findes ikke message,
        // lav et tjek om memberen allerede har projektet.
        Member m = memberService.getMemberWithEmail(member.getEmail());

        if(m == null ){
            return "redirect:/taskOverview/";
        }


        // associate member to the chosen project
        projectService.addMemberToProject(projectId, m.getMemberId());

        // Redirect to project overview page
        return "redirect:/taskOverview/" + projectId;
    }


}

