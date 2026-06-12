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
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        StringBuilder mathString = new StringBuilder();
        //String exprString = "";
        //Expression expression = new Expression(mathString);

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
        Label operationsField = new Label("");

        GridPane buttonGrid = new GridPane();
        buttonGrid.setPadding(new Insets(10, 10, 10, 10));
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);

        outputBox.getChildren().addAll(operationsField, outputField);

        Button clearBtn = new Button("C");
        Button zeroBtn = new Button("0");
        Button decimalBtn = new Button(" .");
        Button equalsBtn = new Button("=");
        Button backspaceBtn = new Button("->");
        Button plusBtn = new Button("+");
        Button minusBtn = new Button("-");
        Button timesBtn = new Button("*");
        Button divideBtn = new Button("/");
        Button leftParensBtn = new Button("(");
        Button rightParensBtn = new Button(")");

        // Setup button styling
        clearBtn.setFont(new Font("arial", 20));
        zeroBtn.setFont(new Font("arial", 20));
        decimalBtn.setFont(new Font("arial", 20));
        zeroBtn.setMaxWidth(Double.MAX_VALUE);
        backspaceBtn.setFont(new Font("arial", 20));
        equalsBtn.setFont(new Font("arial", 20));
        plusBtn.setFont(new Font("arial", 20));
        minusBtn.setFont(new Font("arial", 20));
        timesBtn.setFont(new Font("arial", 20));
        divideBtn.setFont(new Font("arial", 20));
        leftParensBtn.setFont(new Font("arial", 20));
        rightParensBtn.setFont(new Font("arial", 20));

        // Set up formatting for decimals
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(16);

        // Loop to set up number buttons except zero
//        int startInt = 7;
        int rowIdx = 3;
        for (int startInt = 7; startInt > 0; startInt = startInt - 3) {
            for (int colIdx = 0; colIdx < 3; colIdx++) {
                String btnText = String.valueOf(colIdx + startInt);
                Button numberBtn = new Button(btnText);
                numberBtn.setFont(new Font("arial", 20));
                numberBtn.setOnAction(event -> {
                    addNumberToField(outputField, operationsField, btnText, numberFormat);
                });
                buttonGrid.add(numberBtn, colIdx, rowIdx);
            }
            rowIdx++;
        }

        // Setup zero button
        zeroBtn.setOnAction(event -> {
            outputField.setText(outputField.getText() + "0");
        });

        // Setup decimal button
        decimalBtn.setOnAction(event -> {
//            System.out.println("outputField.getText() = " + outputField.getText());
            if (!outputField.getText().contains(".")) {
//                System.out.println("outputField does not contain '.'");
                String number = outputField.getText().replace(",", "");
                long safeNumber =  Long.parseLong(number);
                outputField.setText(numberFormat.format(safeNumber) + ".");
            } /*else {
                System.out.println("oops!");
            }*/
        });

        // Setup clear button
        clearBtn.setOnAction(event -> {
            outputField.setText("");
            operationsField.setText("");
//            System.out.println("\n");
        });

        backspaceBtn.setOnAction(event -> {
            if (outputField.getText() != null &&
                    !outputField.getText().isEmpty()) {
                String truncated = outputField.getText().substring(0,
                        outputField.getText().length() - 1);
                outputField.setText(truncated);
            }
        });

        plusBtn.setOnAction(event -> {
            if (!(mathString.toString().endsWith("[-*/+]s?"))) {
                mathString.append(" + ");
                operationsField.setText(mathString.toString());
            }
        });

        timesBtn.setOnAction(event -> {
            if (!(mathString.toString().endsWith("[-*/+]s?"))) {
                mathString.append(" * ");
                operationsField.setText(mathString.toString());
            }
        });

        minusBtn.setOnAction(event -> {
            if (!(mathString.toString().endsWith("[-*/+]s?"))) {
                mathString.append(" - ");
                operationsField.setText(mathString.toString());
            }
        });

        divideBtn.setOnAction(event -> {
            if (!(mathString.toString().endsWith("[-*/+]s?"))) {
                mathString.append(" / ");
                operationsField.setText(mathString.toString());
            }
        });

        leftParensBtn.setOnAction(event -> {

        });

        equalsBtn.setOnAction(event -> {
            if ((mathString.toString().endsWith("s?[(0-9)|)]s?"))) {
                String math = mathString.toString();
                Expression expression = new ExpressionBuilder(math).build();
                outputField.setText(String.valueOf(expression.evaluate()));
            }
        });

        // Add interface buttons to UI component
        buttonGrid.add(clearBtn, 0, 0);
        buttonGrid.add(backspaceBtn, 1, 0);
        buttonGrid.add(equalsBtn, 2, 0);
        buttonGrid.add(plusBtn, 0, 1);
        buttonGrid.add(timesBtn, 1, 1);
        buttonGrid.add(divideBtn, 2, 1);
        buttonGrid.add(minusBtn, 0, 2);
        buttonGrid.add(zeroBtn, 0, 7, 2, 1);
        buttonGrid.add(decimalBtn, 2, 7);

        root.getChildren().addAll(outputBox, buttonGrid);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calculator");
        primaryStage.setWidth(300);
        primaryStage.show();
    }

    private static void addNumberToField(Label outputField, Label operationsField, String btnText,
                                         NumberFormat numberFormat) {
        String output = "";
        if (outputField.getText().isEmpty() || outputField.getText() == null) { // outputField is empty
            outputField.setText(btnText);
        } else {
            String number = outputField.getText().replace(",", "");
            if (!outputField.getText().contains(".")) { // outputField contains no decimal
                number = number + btnText;
                long safeNumber = Long.parseLong(number);
                output = numberFormat.format(safeNumber);
                outputField.setText(output);
            } else if (outputField.getText().endsWith(".")) { // outputField contains only a decimal point
                number = number.replace(".", "");
                long safeNumberLong = Long.parseLong(number);
                output = numberFormat.format(safeNumberLong) + "." + btnText;
                outputField.setText(output);
            } else { // outputField has a decimal and trailing numbers
                number = number + btnText;
                BigDecimal safeNumberBigDecimal = new BigDecimal(number);
                output = numberFormat.format(safeNumberBigDecimal);
                outputField.setText(output);
            }
        }
    }
}


//    public boolean operationComplete(String input) {
//
//        return input.matches(".*\\d$");
//    }
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