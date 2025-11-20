package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Repository.ProjectRepository;
import com.example.projectcalculationtool.Service.LoginService;
import com.example.projectcalculationtool.Service.ProjectService;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(ProjectController.class)
class ProjectControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private LoginService loginService;

    @BeforeAll
    static void setUp(){
    }

    @Test
    void shouldShowDashboard() throws Exception {
        List<Project> projects = projectService.getAllProjectsWithMemberId(1);
        when(loginService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(projectService.getAllProjectsWithMemberId(1)).thenReturn(projects);

        mockMvc.perform(get("/dashboard")
                        .sessionAttr("memberId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }

    @Test
    void shouldDeleteProject() throws Exception{

        when(loginService.isLoggedIn(any(HttpSession.class))).thenReturn(true);
        when(projectService.memberDoesNotHaveProject(2, 1)).thenReturn(false);

        mockMvc.perform(post("/deleteProject/{projectId}",  2)
                        .sessionAttr("memberId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/dashboard"));

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(projectService).deleteProject(captor.capture());

        int captured = captor.getValue();
        assertEquals(2, captured);
    }

    @Test
    void shouldNotDeleteProject() throws Exception{
        // member does NOT own project 2
        when(projectService.memberDoesNotHaveProject(2, 2)).thenReturn(true);
        when(loginService.isLoggedIn(any(HttpSession.class))).thenReturn(false);

        mockMvc.perform(post("/deleteProject/{projectId}",  2)
                        .sessionAttr("memberId", 2))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));

        // Make sure delete was NOT called
        verify(projectService, never()).deleteProject(anyInt());
    }
}