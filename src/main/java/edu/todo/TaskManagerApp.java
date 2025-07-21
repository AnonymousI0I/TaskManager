package edu.todo;

import edu.todo.model.Task;
import edu.todo.model.TaskList;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/** Simple JavaFX Task‑Manager demo. */
public class TaskManagerApp extends Application {

    /* ---------- model ---------- */
    private final TaskList taskList = new TaskList();
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    /* ---------- UI ------------- */
    private final ListView<Task> lvTasks   = new ListView<>();
    private final TextField      tfNewTask = new TextField();
    private final Button         btAdd     = new Button("Add");

    @Override public void start(Stage stage) {
        /* seed demo data */
        taskList.add("Finish GUI assignment");
        taskList.add("Push repo to GitHub");
        tasks.setAll(taskList.getAll());

        /* centre: ListView with checkbox renderer – NO custom bindings */
        lvTasks.setItems(tasks);
        lvTasks.setCellFactory(
            CheckBoxListCell.forListView(
                Task::completedProperty,          // supplies BooleanProperty
                new StringConverter<>() {
                    @Override public String toString(Task t) {
                        return t == null ? "" : t.getDescription();
                    }
                    @Override public Task fromString(String s) { return null; }
                }));

        /* bottom bar: add‑task controls */
        btAdd.setDefaultButton(true);
        btAdd.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> tfNewTask.getText().trim().isEmpty(),
                tfNewTask.textProperty()));
        btAdd.setOnAction(e -> addNewTask());

        HBox addBar = new HBox(8, tfNewTask, btAdd);
        addBar.setPadding(new Insets(10));

        /* layout */
        BorderPane root = new BorderPane(lvTasks);
        root.setBottom(addBar);
        root.setPadding(new Insets(10));

        stage.setScene(new Scene(root, 460, 320));
        stage.setTitle("Simple Task Manager");
        stage.show();
    }

    /* -------- event helper ------ */
    private void addNewTask() {
        String desc = tfNewTask.getText().trim();
        if (!desc.isEmpty()) {
            taskList.add(desc);
            tasks.setAll(taskList.getAll());
            tfNewTask.clear();
        }
    }

    public static void main(String[] args) { launch(args); }
}

