package edu.todo.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * A concrete task list with persistence & capacity limits.
 */
public class TaskList extends AbstractTaskCollection {

    private final Path storagePath;
    private final int capacity;
    private final Comparator<Task> sorter;

    public TaskList(String ownerName,
                    Path storagePath,
                    int capacity,
                    Comparator<Task> sorter) {
        super(ownerName);
        this.storagePath = storagePath;
        this.capacity = capacity;
        this.sorter = sorter;
    }

    /* -------- extended behaviours -------- */

    @Override public void addTask(Task t) {
        if (size() >= capacity) {
            throw new IllegalStateException("List at capacity");
        }
        super.addTask(t);
        tasks.sort(sorter);
    }

    /** Save as CSV: id,title,dueDate,completed */
    public void save() throws IOException {
        try (BufferedWriter out = Files.newBufferedWriter(storagePath)) {
            for (Task t : tasks) out.write(t + System.lineSeparator());
        }
    }

    /** Load and replace current contents. */
    public void load() throws IOException {
        tasks.clear();
        try (BufferedReader in = Files.newBufferedReader(storagePath)) {
            in.lines().forEach(this::parseAndAdd);
        }
        tasks.sort(sorter);
    }

    private void parseAndAdd(String line) {
        String[] p = line.split(",", 4);
        Task t = new Task(Integer.parseInt(p[0]), p[1],
                          java.time.LocalDate.parse(p[2]));
        t.setCompleted(Boolean.parseBoolean(p[3]));
        tasks.add(t);
    }
}
