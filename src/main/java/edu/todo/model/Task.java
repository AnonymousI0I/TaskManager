package edu.todo.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a single to-do item.
 */
public class Task {

    private final int id;
    private String title;
    private LocalDate dueDate;
    private boolean completed;

    public Task(int id, String title, LocalDate dueDate) {
        this.id = id;
        this.setTitle(title);
        this.setDueDate(dueDate);
        this.completed = false;
    }

    /* ---------------- getters / setters ---------------- */

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must be non-empty");
        }
        this.title = title.trim();
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date required");
        }
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /* ---------------- business helpers ---------------- */

    /** Simple domain check: title present & due date today or in future. */
    public boolean validate() {
        return !title.isBlank() && !dueDate.isBefore(LocalDate.now());
    }

    @Override public String toString() {
        return "%d,%s,%s,%b".formatted(id, title, dueDate, completed);
    }

    @Override public boolean equals(Object o) {
        return (o instanceof Task t) && t.id == id;
    }
    @Override public int hashCode() {
        return Objects.hash(id);
    }
}
