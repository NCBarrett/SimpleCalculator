package com.example.simplecalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        // Create UI elements
        VBox root = new VBox();
        VBox outputBox = new VBox();
        outputBox.setSpacing(10);
        outputBox.setPadding(new Insets(10));

        Label outputField = new Label("");
        outputBox.setAlignment(Pos.BASELINE_RIGHT);
        Label operationsField = new Label("Operations label");
        Button button = new Button("1");
        Button clearBtn = new Button("C");

        button.setOnAction(event -> {
            outputField.setText(outputField.getText() + "1");
        });

        clearBtn.setOnAction(event -> {
            outputField.setText("");
        });

        GridPane buttonGrid = new GridPane();
        buttonGrid.setPadding(new Insets(10, 10, 10, 10));
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);

        outputBox.getChildren().addAll(operationsField, outputField);
        buttonGrid.add(clearBtn, 0, 0);
        buttonGrid.add(button, 0, 1);

        root.getChildren().addAll(outputBox, buttonGrid);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //    public boolean operationComplete(String input) {
//
//        return input.matches(".*\\d$");
//    }
}