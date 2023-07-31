package com.example.demo.service;
import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.services.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceUnitTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", "Description 1", true));
        tasks.add(new Task(2L, "Task 2", "Description 2", false));

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        verify(taskRepository, times(1)).findAll();

        assertEquals(2, result.size());
    }
    @Test
    public void testGetTaskById() {
        Long taskId = 1L;
        Task task = new Task(taskId, "Task 1", "Description 1", true);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(taskId);

        verify(taskRepository, times(1)).findById(taskId);

        assertEquals(task, result);
    }

    @Test
    public void testCreateTask() {
        Task newTask = new Task(null, "New Task", "New Description", false);

        when(taskRepository.save(newTask)).thenReturn(new Task(1L, "New Task", "New Description", false));

        Task result = taskService.createTask(newTask);

        verify(taskRepository, times(1)).save(newTask);

        assertEquals(1L, result.getId());
        assertEquals("New Task", result.getTitle());
        assertEquals("New Description", result.getDescription());
        assertEquals(false, result.getStatus());
    }

    @Test
    public void testUpdateTask() {
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Existing Task", "Existing Description", true);
        Task updatedTask = new Task(null, "Updated Task", "Updated Description", false);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        when(taskRepository.save(existingTask)).thenReturn(updatedTask);

        Task result = taskService.updateTask(taskId, updatedTask);

        verify(taskRepository, times(1)).findById(taskId);

        assertEquals(updatedTask, result);
    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }
}




