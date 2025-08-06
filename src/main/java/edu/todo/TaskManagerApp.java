package edu.todo;

import edu.todo.model.Task;
import edu.todo.model.TaskList;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class TaskManagerApp extends Application {

    /* ---------- model ---------- */
    private final TaskList taskList = new TaskList();

    /* ---------- UI ---------- */
    private final ListView<Task> lvTasks   = new ListView<>();
    private final TextField      tfNewTask = new TextField();
    private final DatePicker     dpDue     = new DatePicker();
    private final Button         btAdd     = new Button("Add");
    private final Button         btDelete  = new Button("Delete");
    private final Button         btClear   = new Button("Clear Completed");
    private final Label          status    = new Label();
    private final ComboBox<String> cbSort  = new ComboBox<>();

    @Override
    public void start(Stage stage) {

        /* list directly uses the model's ObservableList */
        lvTasks.setItems(taskList.getTasks());

        /* checkbox cells – no updateItem override */
        lvTasks.setCellFactory(CheckBoxListCell.forListView(
                Task::completedProperty,
                new StringConverter<Task>() {
                    @Override public String toString(Task t) {
                        if (t == null) return "";
                        String base = t.getDescription();
                        return t.isCompleted() ? base + "  (✓)" : base;
                    }
                    @Override public Task fromString(String s) { return null; }
                }
        ));

        /* add bar */
        tfNewTask.setPromptText("What do you need to do?");
        dpDue.setValue(LocalDate.now());
        dpDue.setEditable(false);

        btAdd.setDefaultButton(true);
        btAdd.disableProperty().bind(tfNewTask.textProperty().isEmpty());
        btAdd.setOnAction(e -> onAdd());
        tfNewTask.setOnAction(e -> onAdd()); // press Enter to add

        HBox addBar = new HBox(8, tfNewTask, dpDue, btAdd);
        addBar.setPadding(new Insets(10));

        /* toolbar */
        btDelete.setOnAction(e -> onDeleteSelected());
        btClear.setOnAction(e -> onClearCompleted());

        cbSort.getItems().addAll(
                "Order added",
                "Description (A→Z)",
                "Due date (soonest)",
                "Completed last"
        );
        cbSort.getSelectionModel().selectFirst();
        cbSort.valueProperty().addListener((obs, oldV, newV) -> applySort(newV));

        ToolBar toolbar = new ToolBar(btDelete, btClear, new Separator(), new Label("Sort:"), cbSort);

        /* layout */
        BorderPane root = new BorderPane(lvTasks, toolbar, null, new VBox(addBar, status), null);
        BorderPane.setMargin(lvTasks, new Insets(10));
        BorderPane.setMargin(status, new Insets(0, 10, 10, 10));

        stage.setTitle("Task Manager");
        stage.setScene(new Scene(root, 560, 380));
        stage.show();

        updateStatus();
    }

    /* ---------- actions ---------- */

    private void onAdd() {
        String desc = Optional.ofNullable(tfNewTask.getText()).orElse("").trim();
        LocalDate due = Optional.ofNullable(dpDue.getValue()).orElse(LocalDate.now());

        if (desc.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing description", "Please enter a task description.");
            return;
        }

        taskList.add(desc, due); // uses TaskList.add(String, LocalDate)
        tfNewTask.clear();
        dpDue.setValue(LocalDate.now());
        updateStatus();
    }

    private void onDeleteSelected() {
        Task sel = lvTasks.getSelectionModel().getSelectedItem();
        if (sel != null) {
            taskList.removeTask(sel); // from AbstractTaskCollection
            updateStatus();
        }
    }

    private void onClearCompleted() {
        var done = new ArrayList<Task>();
        for (Task t : taskList.getTasks()) {
            if (t.isCompleted()) done.add(t);
        }
        done.forEach(taskList::removeTask);
        updateStatus();
    }

    private void applySort(String label) {
        Comparator<Task> cmp = switch (label) {
            case "Description (A→Z)"  -> Comparator.comparing(Task::getDescription, String::compareToIgnoreCase);
            case "Due date (soonest)" -> Comparator.comparing(Task::getDueDate);
            case "Completed last"     -> Comparator.comparing(Task::isCompleted)
                                                   .thenComparing(Task::getDescription, String::compareToIgnoreCase);
            case "Order added"        -> Comparator.comparingInt(Task::getId);
            default                   -> Comparator.comparingInt(Task::getId);
        };
        taskList.sortBy(cmp);
    }

    private void updateStatus() {
        long open = taskList.getTasks().stream().filter(t -> !t.isCompleted()).count();
        status.setText(open + " open · " + taskList.getTasks().size() + " total");
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type, msg, ButtonType.OK);
        a.setHeaderText(title);
        a.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}
