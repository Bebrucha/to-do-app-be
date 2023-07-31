package com.example.demo.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskUnitTest {

    @Test
    public void testGettersAndSetters() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description for Task 1");
        task.setStatus(true);

        assertEquals(1L, task.getId());
        assertEquals("Task 1", task.getTitle());
        assertEquals("Description for Task 1", task.getDescription());
        assertEquals(true, task.getStatus());
    }

    @Test
    public void testDefaultValues() {
        Task task = new Task();
        assertNull(task.getId());
        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertNull(task.getStatus());

    }
}
