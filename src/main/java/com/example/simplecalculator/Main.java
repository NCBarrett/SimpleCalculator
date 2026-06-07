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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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

        Label outputField = new Label("");
        Label operationsField = new Label("Operations label");
        Button buttonOne = new Button("1");
        Button buttonPoint = new Button(" .");
        Button clearBtn = new Button("C");

        // Create a formatter
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(15);

        buttonOne.setOnAction(event -> {

            if (outputField.getText().isEmpty() || outputField.getText() == null) { // watch for a blank outputField
                outputField.setText(outputField.getText() + "1");
            } else if (outputField.getText().endsWith(".")) {  // no numbers after the decimal point
                //System.out.println("no numbers after the decimal point");
                String safeNumber = outputField.getText().replaceAll(",", "");
                safeNumber = safeNumber.replace(".", "");
                long number = Long.parseLong(safeNumber);
                outputField.setText(numberFormat.format(number) + ".1");
            } else if (!outputField.getText().contains(".")) { // no decimal point
                //System.out.println("no decimal point");
                String safeNumber = outputField.getText().replaceAll(",", "");
                long number = Long.parseLong(safeNumber + 1);
                outputField.setText(numberFormat.format(number));
            } else { // (implied) *any* numbers after the decimal point
                //System.out.println("(implied) *any* numbers after the decimal point");
                String safeNumber = outputField.getText().replaceAll(",", "");
                BigDecimal number = new BigDecimal(safeNumber + 1);
                outputField.setText(numberFormat.format(number));
            }
        });

        buttonPoint.setOnAction(event -> {
            if (!outputField.getText().contains(".")) {
                String safeNumber = outputField.getText().replaceAll(",", "");
                long number = Long.parseLong(safeNumber);
                outputField.setText(numberFormat.format(number) + ".");
            }
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
        buttonGrid.add(buttonOne, 0, 1);
        buttonGrid.add(buttonPoint, 1, 2);

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