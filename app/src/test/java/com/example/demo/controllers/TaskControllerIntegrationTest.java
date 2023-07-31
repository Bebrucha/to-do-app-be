package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void testGetTasks() throws Exception {

        Task task1 = new Task(1L, "Task 1", "Description 1", false);
        Task task2 = new Task(2L, "Task 2", "Description 2", true);
        when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(task1.getId()))
                .andExpect(jsonPath("$[0].title").value(task1.getTitle()))
                .andExpect(jsonPath("$[0].description").value(task1.getDescription()))
                .andExpect(jsonPath("$[0].status").value(task1.getStatus()))
                .andExpect(jsonPath("$[1].id").value(task2.getId()))
                .andExpect(jsonPath("$[1].title").value(task2.getTitle()))
                .andExpect(jsonPath("$[1].description").value(task2.getDescription()))
                .andExpect(jsonPath("$[1].status").value(task2.getStatus()))
                .andReturn();
    }

    @Test
    public void testGetTaskById() throws Exception {
        Long taskId = 1L;
        Task task = new Task(taskId, "Task 1", "Description 1", false);
        when(taskService.getTaskById(taskId)).thenReturn(task);

        mockMvc.perform(get("/api/todos/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.status").value(task.getStatus()))
                .andReturn();
    }

      @Test
    public void testCreateTask() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Task\",\"description\":\"New Description\",\"status\":false}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateTask() throws Exception {
        Long taskId = 1L;
        Task updatedTask = new Task(taskId, "Updated Task", "Updated Description", true);
        when(taskService.updateTask(taskId, updatedTask)).thenReturn(updatedTask);

        mockMvc.perform(put("/api/todos/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Task\",\"description\":\"Updated Description\",\"status\":true}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isOk());
    }
}
