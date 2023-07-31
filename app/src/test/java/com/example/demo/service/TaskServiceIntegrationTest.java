package com.example.demo.service;

import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.services.TaskService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
public class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1", false);
        Task task2 = new Task(2L, "Task 2", "Description 2", true);
        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> resultTasks = taskService.getAllTasks();

        assertThat(resultTasks).hasSize(2);
        assertThat(resultTasks).containsExactlyInAnyOrder(task1, task2);
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task(1L, "Sample Task", "Sample Description", false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);

        assertThat(foundTask).isNotNull();
        assertThat(foundTask).isEqualTo(task);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task(1L, "Task 1", "Description 1", false);
        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getId()).isEqualTo(task.getId());
        assertThat(createdTask.getTitle()).isEqualTo(task.getTitle());
    }

    @Test
    public void testUpdateTask() {
        Task existingTask = new Task(1L, "Existing Task", "Description", false);
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setStatus(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Updated Task");
        assertThat(result.getStatus()).isTrue();
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task(1L, "Task to Delete", "Description", false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
