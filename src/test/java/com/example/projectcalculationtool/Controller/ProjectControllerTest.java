package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Exceptions.UnauthorizedAccessException;
import com.example.projectcalculationtool.Service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProjectController.class)
@Import(GlobalExceptionHandlerController.class)
class ProjectControllerTest {



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

    @BeforeAll
    static void setUp(){
    }

    @Test
    void shouldShowDashboard() throws Exception {
        mockMvc.perform(get("/dashboard")
                        .sessionAttr("memberId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }

    @Test
    void shouldDeleteProject() throws Exception{
        mockMvc.perform(post("/deleteProject/{projectId}",  2)
                        .sessionAttr("memberId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(projectService).deleteProject(captor.capture());

        int captured = captor.getValue();
        assertEquals(2, captured);
    }

    @Test
    void shouldNotDeleteProject() throws Exception{
        //when member not allowed to delete others projects, throw exception
        doThrow(new UnauthorizedAccessException("You do not have permission to delete this project."))
                .when(projectService)
                .checkIfMembersProject(eq(2), eq(2), anyString());

        mockMvc.perform(post("/deleteProject/{projectId}",  2)
                        .sessionAttr("memberId", 2))
                .andExpect(status().isOk())
                .andExpect(view().name("error/unauthorized"));

        // Make sure delete was NOT called
        verify(projectService, never()).deleteProject(anyInt());
    }
}