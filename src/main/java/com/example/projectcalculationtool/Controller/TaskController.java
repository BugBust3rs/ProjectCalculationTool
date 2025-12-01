package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.*;
import com.example.projectcalculationtool.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    private final LoginService loginService;
    private final TaskService taskService;
    private final ProjectService projectService;
    private final MemberService memberService;
    private final ProjectTaskHelperService projectTaskHelperService;

    public TaskController(LoginService loginService, TaskService taskService, ProjectService projectService,
                          MemberService memberService, ProjectTaskHelperService projectTaskHelperService) {
        this.loginService = loginService;
        this.taskService = taskService;
        this.projectService = projectService;
        this.memberService = memberService;
        this.projectTaskHelperService = projectTaskHelperService;
    }

    @GetMapping("/createTask/{projectId}")
    public String createTask(@PathVariable int projectId, Model model, HttpSession session) {
        loginService.checkIfLoggedIn(session);
        Task task = new Task();
        task.setProjectId(projectId);
        task.setStatus(Status.BACKLOG);
        model.addAttribute("task", task);
        List<Member> members = memberService.getMembersWithProjectId(projectId);
        model.addAttribute("members", members);
        model.addAttribute("projectId", projectId);

        return "createTask";

    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        loginService.checkIfLoggedIn(session);

        int memberId = (int) session.getAttribute("memberId");
        task.setMemberId(memberId);

        taskService.createTask(task);
        return "redirect:/taskOverview/" + task.getProjectId();
    }

    @GetMapping("/createSubtask/{taskId}")
    public String createSubTask(@PathVariable int taskId, Model model, HttpSession session) {
        loginService.checkIfLoggedIn(session);
        Subtask subtask = new Subtask();
        subtask.setTaskId(taskId);
        subtask.setStatus(Status.BACKLOG);
        model.addAttribute("subtask", subtask);
        Task task = taskService.getTaskById(taskId);

        model.addAttribute("projectId", task.getProjectId());
        List<Member> members = memberService.getMembersWithProjectId(task.getProjectId());
        model.addAttribute("members", members);
        model.addAttribute("projectId", task.getProjectId());
        return "createSubtask";
    }

    @PostMapping("/createSubtask/{projectId}")
    public String createSubTask(@PathVariable int projectId, @ModelAttribute Subtask subtask, HttpSession session) {

        loginService.checkIfLoggedIn(session);

        int memberId = (int) session.getAttribute("memberId");
        subtask.setMemberId(memberId);

        taskService.createSubtask(subtask);
        return "redirect:/taskOverview/" + projectId;
    }

    @GetMapping("/taskOverview/{projectId}")
    public String getTaskOverview(@PathVariable int projectId, Model model, HttpSession session) {
        loginService.checkIfLoggedIn(session);

        int memberId = (int) session.getAttribute("memberId");

        projectService.checkIfMembersProject(
                projectId, memberId, "You do not have permission to see this project.");

        Project project = projectService.getProject(projectId, memberId);
        model.addAttribute("projectTitle", project.getTitle());
        model.addAttribute("projectId", project.getProjectId());
        model.addAttribute("memberId", memberId);

        int overallEstimatedTime = taskService.getOverallEstimatedTime(projectId);
        model.addAttribute("overallEstimatedTime", overallEstimatedTime);

        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        model.addAttribute("tasks",tasks);

        model.addAttribute("statuses", Status.values());

        List<Member> members = memberService.getMembersWithProjectId(projectId);
        model.addAttribute("members", members);

        Member member = new Member();
        model.addAttribute("member", member);

        return "taskOverview";
    }

    @PostMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable int taskId, HttpSession session){
        loginService.checkIfLoggedIn(session);
        int memberId = (int) session.getAttribute("memberId");
        Task task = taskService.getTaskById(taskId);

        projectService.checkIfMembersProject(
                task.getProjectId(), memberId, "You do not have permission to delete this task.");

        taskService.deleteTask(taskId);
        return "redirect:/taskOverview/" + task.getProjectId();
    }

    @PostMapping("/deleteSubtask/{subtaskId}")
    public String deleteSubtask(@PathVariable int subtaskId, HttpSession session){
        loginService.checkIfLoggedIn(session);
        int memberId = (int) session.getAttribute("memberId");
        int projectId = taskService.getProjectIdBySubtaskId(subtaskId);

        projectService.checkIfMembersProject(
                projectId, memberId, "You do not have permission to delete this subtask.");

        taskService.deleteSubtask(subtaskId);

        return "redirect:/taskOverview/" + projectId;
    }

    @PostMapping("/inviteMember/{projectId}")
    public String inviteMemberToProject(@PathVariable int projectId, @ModelAttribute Member member, HttpSession session) {

        loginService.checkIfLoggedIn(session);

        int memberId = (int) session.getAttribute("memberId");


        // add member to the project by email
        // hvis member er == null så return til taskoverview med en bruger findes ikke message,
        // lav et tjek om memberen allerede har projektet.
        Member m = memberService.getMemberWithEmail(member.getEmail());

        if(m == null ){
            return "redirect:/taskOverview/" + projectId;
        }


        // associate member to the chosen project
        projectService.addMemberToProject(projectId, m.getMemberId());

        // Redirect to project overview page
        return "redirect:/taskOverview/" + projectId;
    }

    @PostMapping("/saveTaskStatus")
    public String updateTask(@RequestParam int taskId,
                             @RequestParam Status status,
                             @RequestParam int projectId, HttpSession session){
        loginService.checkIfLoggedIn(session);
        int memberId = (int) session.getAttribute("memberId");
        projectService.checkIfMembersProject(
                projectId, memberId, "You do not have permission to change this task.");

        taskService.updateTaskStatus(taskId, status);

        return "redirect:/taskOverview/" + projectId;


    }

    @PostMapping("/saveSubtaskStatus")
    public String updateSubtask(@RequestParam int subtaskId,
                                @RequestParam Status status,
                                @RequestParam int projectId, HttpSession session){
        loginService.checkIfLoggedIn(session);
        int memberId = (int) session.getAttribute("memberId");
        projectService.checkIfMembersProject(
                projectId, memberId, "You do not have permission to change this task.");

        taskService.updateSubtaskStatus(subtaskId, status);

        return "redirect:/taskOverview/" + projectId;


    }

    @GetMapping("/showAllocatedTasks/{memberId}")
    public String showAllocatedTasks(@PathVariable int memberId, Model model, HttpSession session) {
        loginService.checkIfLoggedIn(session);
        // check om seesion.memberID fra session er == memberID fra Pathvariable
        // int memberId = (int) session.getAttribute("memberId");
        List<Task> tasks = projectTaskHelperService.getTasksByMemberId(memberId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("tasks", tasks);
        return "showAllocatedTasks";
    }


}

