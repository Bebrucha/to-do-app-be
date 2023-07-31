package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TaskControllerUnitTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testGetTasks() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", "Description 1", true));
        tasks.add(new Task(2L, "Task 2", "Description 2", false));

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].status").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Task 2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[1].status").value(false));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void testGetTaskById() throws Exception {
        Long taskId = 1L;
        Task task = new Task(taskId, "Task 1", "Description 1", true);

        when(taskService.getTaskById(taskId)).thenReturn(task);

        mockMvc.perform(get("/api/todos/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.status").value(true));

        verify(taskService, times(1)).getTaskById(taskId);
    }

   @Test
    public void testCreateTask() throws Exception {
        Task savedTask = new Task(1L, "New Task", "New Description", false);

        when(taskService.createTask(any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Task\",\"description\":\"New Description\",\"status\":false}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.description").value("New Description"))
                .andExpect(jsonPath("$.status").value(false));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void testUpdateTask() {
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Task 1", "Description for Task 1", false);
        Task updatedTask = new Task(taskId, "Updated Task 1", "Updated Description for Task 1", true);

        when(taskService.getTaskById(taskId)).thenReturn(existingTask);
        when(taskService.updateTask(taskId, updatedTask)).thenReturn(updatedTask);

        Task result = taskController.updateTask(taskId, updatedTask);
        assertEquals(updatedTask, result);
    }

    @Test
    public void testDeleteTask() throws Exception {
        Long taskId = 1L;

        mockMvc.perform(delete("/api/todos/{id}", taskId))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(taskId);
    }
}

