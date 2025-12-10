package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Exceptions.UnauthorizedAccessException;
import com.example.projectcalculationtool.Model.Member;
import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Service.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private MemberService memberService;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private LoginService loginService;

    @MockitoBean
    private ProjectTaskHelperService projectTaskHelperService;


    @Test
    void shouldShowTaskOverview() throws Exception {

        Project project = new Project();
        project.setProjectId(1);
        project.setTitle("Website Redesign");
        List<Task> tasks = taskService.getTasksByProjectId(1);
        int projectId = 1;
        int memberId = 1;
        List<Member> members = memberService.getMembersWithProjectId(projectId);

        when(projectService.getProject(projectId,memberId)).thenReturn(project);
        when(taskService.getTasksByProjectId(projectId)).thenReturn(tasks);

        when(memberService.getMembersWithProjectId(projectId))
                .thenReturn(members);


        mockMvc.perform(get("/taskOverview/1")
                        .sessionAttr("memberId", memberId))
                .andExpect(status().isOk())
                .andExpect(view().name("taskOverview"))
                .andExpect(model().size(8));
    }
    @Test
    void ShouldDeleteTask() throws Exception {

        Task task = new Task();
        task.setProjectId(1);

        when(taskService.getTaskById(1)).thenReturn(task);

        mockMvc.perform(post("/deleteTask/{taskId}", 1)
                        .sessionAttr("memberId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/taskOverview/1"));

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(taskService).deleteTask(captor.capture());

        int captured = captor.getValue();
        assertEquals(1, captured);
    }


    @Test
    void ShouldDeleteSubtask() throws Exception{

        when(taskService.getProjectIdBySubtaskId(1)).thenReturn(1);

        mockMvc.perform(post("/deleteSubtask/{subtaskId}", 1)
                        .sessionAttr("memberId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/taskOverview/1"));

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(taskService).deleteSubtask(captor.capture());

        int captured = captor.getValue();
        assertEquals(1, captured);
    }


    @Test
    void shouldNotDeleteTask() throws Exception{
        Task task = new Task();
        task.setProjectId(1);
        int memberId = 2;
        int taskId = 1;
        when(taskService.getTaskById(1)).thenReturn(task);
        //when member not allowed to delete others task, throw exception
        doThrow(new UnauthorizedAccessException("You do not have permission to delete this task."))
                .when(projectService)
                .checkIfMembersProject(eq(task.getProjectId()), eq(memberId), anyString());


        mockMvc.perform(post("/deleteTask/{taskId}", taskId)
                        .sessionAttr("memberId", memberId))
                .andExpect(status().isOk())
                .andExpect(view().name("error/unauthorized"));

        // Make sure delete was NOT called
        verify(taskService, never()).deleteTask(anyInt());
    }


    @Test
    void shouldNotDeleteSubtask() throws Exception{
        int projectId = 1;
        int subtaskId = 1;
        int memberId = 2;
        when(taskService.getProjectIdBySubtaskId(subtaskId)).thenReturn(projectId);
        //when member not allowed to delete others subtask, throw exception
        doThrow(new UnauthorizedAccessException("You do not have permission to delete this task."))
                .when(projectService)
                .checkIfMembersProject(eq(projectId), eq(memberId), anyString());

        mockMvc.perform(post("/deleteSubtask/{subtaskId}", subtaskId)
                        .sessionAttr("memberId", memberId))
                .andExpect(status().isOk())
                .andExpect(view().name("error/unauthorized"));

        // Make sure delete was NOT called
        verify(taskService, never()).deleteSubtask(anyInt());
    }

}





