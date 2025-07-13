package edu.todo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/** Smoke-test GUI that proves JavaFX is working. */
public class TaskManagerApp extends Application {

    @Override
    public void start(Stage stage) {
        // big, obvious message so you can confirm the renderer works
        Label msg = new Label("JavaFX is working 🎉");
        msg.setStyle("-fx-font-size: 32px; -fx-text-fill: teal;");

        StackPane root = new StackPane(msg);     // center the label
        root.setStyle("-fx-background-color: cornsilk;");

        stage.setScene(new Scene(root, 500, 250));
        stage.setTitle("Simple Task Manager – smoke test");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
