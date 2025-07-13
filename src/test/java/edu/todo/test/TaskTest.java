package edu.todo.test;

import edu.todo.model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test void validateRejectsPastDate() {
        Task t = new Task(1, "Past", LocalDate.now().minusDays(1));
        assertFalse(t.validate());
    }

    @Test void validateAcceptsToday() {
        Task t = new Task(2, "Today", LocalDate.now());
        assertTrue(t.validate());
    }
}
