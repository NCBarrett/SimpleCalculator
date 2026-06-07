package com.example.simplecalculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        // Create UI elements
        VBox root = new VBox();
        root.setPrefWidth(250);
        VBox outputBox = new VBox();
        outputBox.setSpacing(10);
        outputBox.setPadding(new Insets(10));
        outputBox.setAlignment(Pos.BASELINE_RIGHT);

        //Maybe add a string to capture number as it grows and changes?

        Label outputField = new Label("");
        Label operationsField = new Label("Operations label");
        Button button = new Button("1");
        Button clearBtn = new Button("C");

        // Create a formatter
        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        button.setOnAction(event -> {
            String safeNumber = outputField.getText().replaceAll(",", "");
            long number = Long.parseLong(safeNumber + "1");
            outputField.setText(numberFormat.format(number));
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
        primaryStage.setTitle("Calculator");
        primaryStage.show();
    }

    //    public boolean operationComplete(String input) {
//
//        return input.matches(".*\\d$");
//    }
}