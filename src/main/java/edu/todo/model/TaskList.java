package edu.todo.model;

import java.time.LocalDate;
import java.util.Comparator;

/** Concrete collection with simple id generation and sorting. */
public class TaskList extends AbstractTaskCollection {

    private int nextId = 1;
    private Comparator<Task> comparator = Comparator.comparing(Task::getDueDate);

    public TaskList() { super("Default"); }
    public TaskList(String owner) { super(owner); }

    /** Convenience used by the UI. */
    public void add(String desc, LocalDate due) {
        addTask(new Task(nextId++, desc, due));
        sortBy(comparator);
    }

    public void sortBy(Comparator<Task> cmp) {
        this.comparator = cmp;
        tasks.sort(cmp);
    }
}
