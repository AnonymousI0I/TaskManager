package edu.todo.model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

/** Very small, in‑memory list that limits the number of tasks. */
public class TaskList extends AbstractTaskCollection {

    private final int capacity;
    private final Comparator<Task> order;
    private final AtomicInteger idSeq = new AtomicInteger(1);

    /* real constructor -------------------------------------------------- */
    public TaskList(String ownerName, int capacity,
                    Comparator<Task> ordering) {
        super(ownerName);
        this.capacity = capacity;
        this.order    = ordering;
    }

    /* convenience no‑arg ctor used by the GUI --------------------------- */
    public TaskList() {
        this(System.getProperty("user.name"),   // owner
             100,                               // max 100 tasks
             Comparator.comparing(Task::getId));
    }

    /* ------------ “rich” helpers the GUI calls ------------------------ */

    /** Add quickly from a description. */
    public void add(String desc) {
        if (size() >= capacity)
            throw new IllegalStateException("Too many tasks (capacity " + capacity + ")");
        addTask(new Task(nextId(), desc, LocalDate.now().plusDays(7)));
        tasks.sort(order);
    }

    /** Gives a *mutable* list – internal use only. */
    public java.util.List<Task> getAll() {
        return tasks;
    }

    /* private */ int nextId() { return idSeq.getAndIncrement(); }
}
