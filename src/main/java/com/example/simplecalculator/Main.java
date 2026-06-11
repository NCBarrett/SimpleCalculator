package com.example.simplecalculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        // Define VBox border
        BorderStroke stroke = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT
        );

        // Create UI elements
        VBox root = new VBox();
        VBox outputBox = new VBox();
        outputBox.setSpacing(10);
        outputBox.setPadding(new Insets(10));
        outputBox.setBorder(new Border(stroke));

        Label outputField = new Label("");
        outputBox.setAlignment(Pos.BASELINE_RIGHT);
        Label operationsField = new Label("Operations label");

        GridPane buttonGrid = new GridPane();
        buttonGrid.setPadding(new Insets(10, 10, 10, 10));
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);

        outputBox.getChildren().addAll(operationsField, outputField);

        Button clearBtn = new Button("C");
        clearBtn.setFont(new Font("arial", 20));
//        Button buttonOne = new Button("1");
        Button zeroBtn = new Button("0");
        zeroBtn.setFont(new Font("arial", 20));
        Button decimalBtn = new Button(" .");
        decimalBtn.setFont(new Font("arial", 20));
        zeroBtn.setMaxWidth(Double.MAX_VALUE);

        // Set up formatting for decimals
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(16);

//        int startInt = 7;
        int rowIdx = 1;
        for (int startInt = 7; startInt > 0; startInt = startInt - 3) {
            for (int colIdx = 0; colIdx < 3; colIdx++) {
                String btnText = String.valueOf(colIdx + startInt);
                Button numberBtn = new Button(btnText);
                numberBtn.setFont(new Font("arial", 20));
                numberBtn.setOnAction(event -> {
                    addNumberToField(outputField, btnText, numberFormat);
                });
                buttonGrid.add(numberBtn, colIdx, rowIdx);

            }
            rowIdx++;
        }

//        int startInt = 7;
//        for (int colIdx = 0; colIdx < 3; colIdx++) {
//            String btnText = String.valueOf(colIdx + startInt);
//            System.out.println(colIdx + " + " + startInt + " = " + btnText);
//            Button numberButton = new Button(btnText);
//            numberButton.setOnAction(event -> {
//                addNumberToField(outputField, btnText, numberFormat);
//            });
//            buttonGrid.add(numberButton, colIdx, 1);
//        }
//
//        startInt = 4;
//        for (int colIdx = 0; colIdx < 3; colIdx++) {
//            String btnText = String.valueOf(colIdx + startInt);
//            // System.out.println("button text = " + btnText);
//            Button numberButton = new Button(btnText);
//            numberButton.setOnAction(event -> {
//               //outputField.setText(outputField.getText() + btnText);
//                addNumberToField(outputField, btnText, numberFormat);
//            });
//            buttonGrid.add(numberButton, colIdx, 2);
//        }
//
//        startInt = 1;
//        for (int colIdx = 0; colIdx < 3; colIdx++) {
//            String btnText = String.valueOf(colIdx + startInt);
//            // System.out.println("button text = " + btnText);
//            Button numberButton = new Button(btnText);
//            numberButton.setOnAction(event -> {
//               //outputField.setText(outputField.getText() + btnText);
//                addNumberToField(outputField, btnText, numberFormat);
//            });
//            buttonGrid.add(numberButton, colIdx, 3);
//        }

        zeroBtn.setOnAction(event -> {
            outputField.setText(outputField.getText() + "0");
        });

        decimalBtn.setOnAction(event -> {
            System.out.println("outputField.getText() = " + outputField.getText());
            if (!outputField.getText().contains(".")) {
                System.out.println("outputField does not contain '.'");
                String number = outputField.getText().replace(",", "");
                long safeNumber =  Long.parseLong(number);
                outputField.setText(numberFormat.format(safeNumber) + ".");
            } /*else {
                System.out.println("oops!");
            }*/
        });

        clearBtn.setOnAction(event -> {
            outputField.setText("");
            System.out.println("\n");
        });

        buttonGrid.add(clearBtn, 0, 0);
        buttonGrid.add(zeroBtn, 0, 4, 2, 1);
        buttonGrid.add(decimalBtn, 2, 4);

        root.getChildren().addAll(outputBox, buttonGrid);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.setWidth(300);
        primaryStage.show();
    }

    private static void addNumberToField(Label outputField, String btnText, NumberFormat numberFormat) {
        if (outputField.getText().isEmpty() || outputField.getText() == null) { // outputField is empty
            outputField.setText(btnText);
        } else if (!outputField.getText().contains(".")) { // outputField contains no decimal
            String number = outputField.getText().replace(",", "");
            number = number + btnText;
            long safeNumber = Long.parseLong(number);
            outputField.setText(numberFormat.format(safeNumber));
        } else if (outputField.getText().endsWith(".")) { // outputField contains only a decimal point
            String safeNumber = outputField.getText().replace(",", "");
            safeNumber = safeNumber.replace(".", "") ;
            long safeNumberLong = Long.parseLong(safeNumber);
            outputField.setText(numberFormat.format(safeNumberLong) + "." + btnText);
        } else { // outputField has a decimal and trailing numbers
            String safeNumber = outputField.getText().replace(",", "");
//            System.out.println("safeNumber = " + safeNumber);
            BigDecimal safeNumberBigDecimal = new BigDecimal(safeNumber);
//            System.out.println("safeNumberBigDecimal = " + safeNumberBigDecimal + "; btnText = " + btnText);
//            System.out.println("numberFormat.format(safeNumberBigDecimal) = " +
//                    numberFormat.format(safeNumberBigDecimal));
            outputField.setText(numberFormat.format(safeNumberBigDecimal) + btnText);
        }
    }
}


//    public boolean operationComplete(String input) {
//
//        return input.matches(".*\\d$");
//    }
