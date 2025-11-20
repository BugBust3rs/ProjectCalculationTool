package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Model.Project;
import com.example.projectcalculationtool.Repository.ProjectRepository;
import com.example.projectcalculationtool.Service.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

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

        mockMvc.perform(post("/deleteProject/{projectId}",  2)
                        .sessionAttr("memberId", 2))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));

        // Make sure delete was NOT called
        verify(projectService, never()).deleteProject(anyInt());
    }
}