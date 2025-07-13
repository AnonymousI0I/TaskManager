package edu.todo.test;

import edu.todo.model.Task;
import edu.todo.model.TaskList;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    private TaskList list;
    private Path tempFile;

    @BeforeEach void setUp() throws Exception {
        tempFile = Files.createTempFile("tasks", ".csv");
        list = new TaskList(
                "JUnit",
                tempFile,
                5,
                Comparator.comparing(Task::getDueDate));
    }

    @Test void capacityIsEnforced() {
        for (int i = 0; i < 5; i++)
            list.addTask(new Task(i, "t"+i, LocalDate.now()));
        assertThrows(IllegalStateException.class,
                     () -> list.addTask(new Task(99,"boom", LocalDate.now())));
    }

    @Test void saveAndLoadRoundTrip() throws Exception {
        list.addTask(new Task(1, "SaveMe", LocalDate.now()));
        list.save();

        TaskList loaded = new TaskList(
                "JUnit", tempFile, 5, Comparator.comparing(Task::getDueDate));
        loaded.load();

        assertEquals(1, loaded.size());
        assertEquals("SaveMe", loaded.getTasks().get(0).getTitle());
    }
}
