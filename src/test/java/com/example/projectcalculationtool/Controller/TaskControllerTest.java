package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Service.ProjectService;
import com.example.projectcalculationtool.Service.TaskService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    void shouldShowTaskOverview() throws Exception{
        mockMvc.perform(get("/taskOverview/1")
                        .sessionAttr("memberId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("taskOverview"));
    }

    @Test
    void ShouldDeleteTask() throws Exception{
        mockMvc.perform(post("/deleteTask/{taskId}", 1)
                        .sessionAttr("memberId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/taskOverview/1"));
    }

    @Test
    void ShouldDeleteSubtask() {
    }
}