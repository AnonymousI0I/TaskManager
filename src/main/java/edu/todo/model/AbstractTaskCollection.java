package edu.todo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Base class for any concrete collection of {@link Task}s. */
public abstract class AbstractTaskCollection {

    /** live list the GUI can observe */
    protected final ObservableList<Task> tasks =
            FXCollections.observableArrayList();

    protected final String ownerName;

    protected AbstractTaskCollection(String ownerName) {
        this.ownerName = ownerName;
    }

    /* ---------- basic CRUD wrappers ---------- */

    public void addTask(Task t)    { tasks.add(t); }
    public void removeTask(Task t) { tasks.remove(t); }

    /** Unmodifiable view for callers. */
    public ObservableList<Task> getTasks() {
        return FXCollections.unmodifiableObservableList(tasks);
    }

    public int size() { return tasks.size(); }

    /** Override if a concrete collection has stricter rules. */
    public boolean isValid() { return !ownerName.isBlank(); }
}
