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

        Button clearBtn = new Button("C");

        clearBtn.setOnAction(event -> {
            outputField.setText("");
        });

        GridPane buttonGrid = new GridPane();
        buttonGrid.setPadding(new Insets(10, 10, 10, 10));
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);

        outputBox.getChildren().addAll(operationsField, outputField);
        buttonGrid.add(clearBtn, 0, 0);

        //        Button button = new Button("1");
//        button.setOnAction(event -> {
//            outputField.setText(outputField.getText() + "1");
//        });
//        buttonGrid.add(button, 0, 1);

        int startInt = 7;
        for (int i = 0; i < 3; i++) {
            String btnText = String.valueOf(i + startInt);
        // System.out.println("button text = " + btnText);
            Button numberButton = new Button(btnText);
            numberButton.setOnAction(event -> {
                outputField.setText(outputField.getText() + btnText);
            });
            buttonGrid.add(numberButton, i, 1);
        }

        startInt = 4;
        for (int i = 0; i < 3; i++) {
            String btnText = String.valueOf(i + startInt);
            // System.out.println("button text = " + btnText);
            Button numberButton = new Button(btnText);
            numberButton.setOnAction(event -> {
                outputField.setText(outputField.getText() + btnText);
            });
            buttonGrid.add(numberButton, i, 2);
        }

        startInt = 1;
        for (int i = 0; i < 3; i++) {
            String btnText = String.valueOf(i + startInt);
            // System.out.println("button text = " + btnText);
            Button numberButton = new Button(btnText);
            numberButton.setOnAction(event -> {
                outputField.setText(outputField.getText() + btnText);
            });
            buttonGrid.add(numberButton, i, 3);
        }

        Button zeroBtn = new Button("0");
        zeroBtn.setOnAction(event -> {
            outputField.setText(outputField.getText() + "0");
        });
        zeroBtn.setMaxWidth(Double.MAX_VALUE);
        buttonGrid.add(zeroBtn, 0, 4, 2, 1);

        Button decimalBtn = new Button(" .");
        decimalBtn.setOnAction(event -> {
            outputField.setText(outputField.getText() + ".");
        });
        buttonGrid.add(decimalBtn, 2, 4);

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