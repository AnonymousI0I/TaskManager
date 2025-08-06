package edu.todo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Base class for any list of tasks (home, work, project …). */
public abstract class AbstractTaskCollection {

    protected final ObservableList<Task> tasks = FXCollections.observableArrayList();
    protected final String ownerName;

    protected AbstractTaskCollection(String ownerName) {
        this.ownerName = ownerName;
    }

    /* ---- basic CRUD wrappers ---- */
    public void addTask(Task t) { tasks.add(t); }
    public void removeTask(Task t) { tasks.remove(t); }

    public ObservableList<Task> getTasks() { return tasks; }

    public int size() { return tasks.size(); }

    /** Override if a concrete collection has stricter rules. */
    public boolean isValid() { return ownerName != null && !ownerName.isBlank(); }
}
