package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Model.Subtask;
import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Service.LoginService;
import com.example.projectcalculationtool.Service.ProjectService;
import com.example.projectcalculationtool.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
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
    private ProjectService projectService;

    @MockitoBean
    private LoginService loginService;


    @Test
    void shouldShowTaskOverview() throws Exception {

        Project project = new Project();
        project.setProjectId(1);
        project.setTitle("Website Redesign");
        List<Task> tasks = taskService.getTasksByProjectId(1);

        when(projectService.memberDoesNotHaveProject(1, 1)).thenReturn(false);
        when(loginService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(projectService.getProject(1,1)).thenReturn(project);
        when(taskService.getTasksByProjectId(1)).thenReturn(tasks);

        mockMvc.perform(get("/taskOverview/1")
                        .sessionAttr("memberId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("taskOverview"))
                .andExpect(model().size(4));
    }
    @Test
    void ShouldDeleteTask() throws Exception {

        Task task = new Task();
        task.setProjectId(1);

        when(loginService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(projectService.memberDoesNotHaveProject(1, 1)).thenReturn(false);
        when(taskService.getTaskById(1)).thenReturn(task);

        mockMvc.perform(post("/deleteTask/{taskId}", 1)
                        .sessionAttr("memberId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/taskOverview/1"));

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(taskService).deleteTask(captor.capture());

        int captured = captor.getValue();
        assertEquals(1, captured);
    }


    @Test
    void ShouldDeleteSubtask() throws Exception{

        when(projectService.memberDoesNotHaveProject(1, 1)).thenReturn(false);
        when(loginService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(taskService.getProjectIdBySubtaskId(1)).thenReturn(1);

        mockMvc.perform(post("/deleteSubtask/{subtaskId}", 1)
                        .sessionAttr("memberId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/taskOverview/1"));

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(taskService).deleteSubtask(captor.capture());

        int captured = captor.getValue();
        assertEquals(1, captured);
    }


    @Test
    void shouldNotDeleteTask() throws Exception{
        Task task = new Task();
        task.setProjectId(1);
        when(taskService.getTaskById(1)).thenReturn(task);
        // member does NOT own project 2
        when(projectService.memberDoesNotHaveProject(1, 2)).thenReturn(true);
        when(loginService.isLoggedIn(any(HttpSession.class))).thenReturn(false);


        mockMvc.perform(post("/deleteTask/{taskId}", 1)
                        .sessionAttr("memberId", 2))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));

        // Make sure delete was NOT called
        verify(taskService, never()).deleteTask(anyInt());
    }


    @Test
    void shouldNotDeleteSubtask() throws Exception{

        when(taskService.getProjectIdBySubtaskId(1)).thenReturn(1);
        // member does NOT own project 2
        when(projectService.memberDoesNotHaveProject(1, 2)).thenReturn(true);
        when(loginService.isLoggedIn(any(HttpSession.class))).thenReturn(false);

        mockMvc.perform(post("/deleteSubtask/{subtaskId}", 1)
                        .sessionAttr("memberId", 2))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));

        // Make sure delete was NOT called
        verify(taskService, never()).deleteSubtask(anyInt());
    }

}





