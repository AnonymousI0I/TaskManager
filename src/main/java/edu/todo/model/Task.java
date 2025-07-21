package edu.todo.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.Objects;

/** Plain domain object with JavaFX‑friendly properties. */
public class Task {

    /* immutable id */                private final int id;
    /* observable   */                private final StringProperty  description =
                                                 new SimpleStringProperty();
    /* simple date  */                private       LocalDate       dueDate;
    /* observable   */                private final BooleanProperty completed   =
                                                 new SimpleBooleanProperty(false);

    public Task(int id, String desc, LocalDate due) {
        this.id = id;
        setDescription(desc);
        this.dueDate = due;
    }

    /* ---- JavaFX getters / setters ---- */

    public int            getId()              { return id; }

    public String         getDescription()     { return description.get(); }
    public void           setDescription(String d){ description.set(d.trim()); }
    public StringProperty descriptionProperty(){ return description; }

    public LocalDate      getDueDate()         { return dueDate; }
    public void           setDueDate(LocalDate d){ dueDate = d; }

    public boolean        isCompleted()        { return completed.get(); }
    public void           setCompleted(boolean v){ completed.set(v); }
    public BooleanProperty completedProperty() { return completed; }

    /* ---- basic rule ---- */
    public boolean validate() {
        return !getDescription().isBlank()
               && !dueDate.isBefore(LocalDate.now());
    }

    @Override public String toString() {
        return "%d,%s,%s,%b".formatted(id, getDescription(), dueDate, isCompleted());
    }

    @Override public boolean equals(Object o) { return o instanceof Task t && t.id == id; }
    @Override public int hashCode()           { return Objects.hash(id); }
}
