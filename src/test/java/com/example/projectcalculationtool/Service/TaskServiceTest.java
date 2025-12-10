package com.example.projectcalculationtool.Service;

import com.example.projectcalculationtool.Model.Subtask;
import com.example.projectcalculationtool.Model.Task;
import com.example.projectcalculationtool.Repository.SubtaskRepository;
import com.example.projectcalculationtool.Repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {


    @InjectMocks
    private TaskService taskService;

    @Mock
    private MemberService memberService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SubtaskRepository subtaskRepository;

    private Task taskNosubtask;
    private Task taskWithSubtask;

    private Subtask subtask1;
    private Subtask subtask2;

    @BeforeEach
    void setup() {
        taskNosubtask = new Task();
        taskNosubtask.setTaskId(1);
        taskNosubtask.setEstimatedTime(5);
        taskNosubtask.setMemberId(1);


        taskWithSubtask = new Task();
        taskWithSubtask.setTaskId(2);
        taskWithSubtask.setEstimatedTime(99);
        taskWithSubtask.setMemberId(1);

        subtask1 = new Subtask();
        subtask1.setSubtaskID(1);
        subtask1.setTaskId(2);
        subtask1.setEstimatedTime(5);
        subtask1.setMemberId(2);

        subtask2 = new Subtask();
        subtask2.setSubtaskID(2);
        subtask2.setTaskId(2);
        subtask2.setEstimatedTime(5);
        subtask2.setMemberId(2);
    }
    @Test
    void getTasksByProjectId() {
        int projectId = 1;

        when(taskRepository.getAllTasksWithProjectId(projectId)).thenReturn(List.of(taskNosubtask, taskWithSubtask));
        when(subtaskRepository.getAllSubtasksWithTaskId(1))
                .thenReturn(List.of());

        when(subtaskRepository.getAllSubtasksWithTaskId(2))
                .thenReturn(List.of(subtask1, subtask2));

        when(memberService.getMemberName(1)).thenReturn("Alice Johnson");
        when(memberService.getMemberName(2)).thenReturn("Bob Smith");

        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        assertEquals(2, tasks.size());

        Task t1 = tasks.get(0);
        Task t2 = tasks.get(1);


        assertTrue(t1.getSubtasks().isEmpty());
        assertEquals(5, t1.getEstimatedTime());
        assertEquals("Alice Johnson", t1.getMemberName());



        assertEquals(2, t2.getSubtasks().size());
        assertEquals(10, t2.getEstimatedTime());
        assertEquals("Alice Johnson", t2.getMemberName());
        assertEquals("Bob Smith", t2.getSubtasks().get(0).getMemberName());
        assertEquals("Bob Smith", t2.getSubtasks().get(1).getMemberName());
    }
}