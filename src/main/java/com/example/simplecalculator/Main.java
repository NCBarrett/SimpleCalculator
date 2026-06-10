package com.example.simplecalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

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

        GridPane buttonGrid = new GridPane();
        buttonGrid.setPadding(new Insets(10, 10, 10, 10));
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);

        outputBox.getChildren().addAll(operationsField, outputField);

        Button clearBtn = new Button("C");
        Button buttonOne = new Button("1");
        Button zeroBtn = new Button("0");
        Button decimalBtn = new Button(" .");
        zeroBtn.setMaxWidth(Double.MAX_VALUE);

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(16);


        buttonOne.setOnAction(event -> {
            if (outputField.getText().isEmpty() || operationsField.getText() == null) { // outputField is empty
                outputField.setText("1");
            } else if (!outputField.getText().contains(".")) { // outputField contains no decimal
                String number = outputField.getText().replace(",", "");
                number = number + "1";
                long safeNumber = Long.parseLong(number);
                outputField.setText(numberFormat.format(safeNumber));
            } else if (outputField.getText().endsWith(".")) { // outputField contains only a decimal point
                String safeNumber = outputField.getText().replace(",", "");
                safeNumber = safeNumber.replace(".", "") ;
                long safeNumberLong = Long.parseLong(safeNumber);
                outputField.setText(numberFormat.format(safeNumberLong) + ".1");
            } else { // outputField has a decimal and trailing numbers
                String safeNumber = outputField.getText().replace(",", "");
                BigDecimal safeNumberBigDecimal = new BigDecimal(safeNumber);
                outputField.setText(numberFormat.format(safeNumberBigDecimal) + "1");
            }
        });

        decimalBtn.setOnAction(event -> {
            if (!outputField.getText().contains(".")) {
                String number = outputField.getText().replace(",", "");
                long safeNumber =  Long.parseLong(number);
                outputField.setText(numberFormat.format(safeNumber) + ".");
            }
        });

        clearBtn.setOnAction(event -> {
            outputField.setText("");
        });

        buttonGrid.add(clearBtn, 0, 0);
        buttonGrid.add(buttonOne, 0, 1);
        buttonGrid.add(zeroBtn, 0, 4, 2, 1);
        buttonGrid.add(decimalBtn, 2, 4);

        root.getChildren().addAll(outputBox, buttonGrid);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

/**        int startInt = 7;
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
 *
 *
 *
  */
//    public boolean operationComplete(String input) {
//
//        return input.matches(".*\\d$");
//    }
