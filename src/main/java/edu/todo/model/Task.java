package edu.todo.model;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/** Represents a single to-do item. */
public class Task {
    private final int id;
    private final StringProperty description = new SimpleStringProperty();
    private LocalDate dueDate = LocalDate.now();
    private final BooleanProperty completed = new SimpleBooleanProperty(false);

    public Task(int id, String desc, LocalDate due) {
        this.id = id;
        setDescription(desc);
        setDueDate(due == null ? LocalDate.now() : due);
    }
    public Task(int id, String desc) { this(id, desc, LocalDate.now()); }

    /* JavaFX-friendly getters / setters */
    public int getId() { return id; }

    public String getDescription() { return description.get(); }
    public void setDescription(String desc) { this.description.set(desc == null ? "" : desc.trim()); }
    public StringProperty descriptionProperty() { return description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate d) { this.dueDate = (d == null ? LocalDate.now() : d); }

    public boolean isCompleted() { return completed.get(); }
    public void setCompleted(boolean v) { completed.set(v); }
    public BooleanProperty completedProperty() { return completed; }

    /* simple domain rule */
    public boolean validate() {
        return !getDescription().isBlank() && !getDueDate().isBefore(LocalDate.now());
    }

    @Override public String toString() {
        return "%d,%s,%s,%b".formatted(id, getDescription(), getDueDate(), isCompleted());
    }
    @Override public boolean equals(Object o) { return (o instanceof Task t) && t.id == id; }
    @Override public int hashCode() { return Objects.hash(id); }
}
