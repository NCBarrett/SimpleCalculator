package com.example.simplecalculator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Main extends Application {

    private TextField expressionField;
    private TextField resultField;
    private StringBuilder number = new StringBuilder();
    private StringBuilder currentExpression = new StringBuilder();

    @Override
    public void start(Stage primaryStage) {
        // Initialize Display Fields
        expressionField = new TextField();
        expressionField.setEditable(false);
        expressionField.setStyle("-fx-font-size: 16px; -fx-alignment: CENTER-RIGHT;");

        resultField = new TextField();
        resultField.setEditable(false); // when we modify this, change it to editable
        resultField.setStyle("-fx-font-size: 24px; -fx-alignment: CENTER-RIGHT; -fx-font-weight: bold;");

        VBox displayBox = new VBox(10, expressionField, resultField);
        displayBox.setStyle("-fx-padding: 15;");

        // Button layout
        String[] buttons = {
                "C", "<-", "/", "*",
                "7", "8", "9", "-",
                "4", "5", "6", "+",
                "1", "2", "3", "=",
                "0", ".", "", ""
        };

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-padding: 15;");

        int row = 0;
        int col = 0;
        for (String text : buttons) {
            if (text.isEmpty()) {
                col++;
                continue;
            }

            Button btn = new Button(text);
            btn.setPrefSize(70, 70);
            btn.setStyle("-fx-font-size: 18px;");
            btn.setOnAction(e -> handleButtonAction(text));

            gridPane.add(btn, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }

        VBox mainLayout = new VBox(displayBox, gridPane);
        Scene scene = new Scene(mainLayout, 320, 450);

        primaryStage.setTitle("exp4j JavaFX Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonAction(String command) {
        switch (command) {
            case "C":
                currentExpression.setLength(0);
                expressionField.clear();
                resultField.clear();
                number.setLength(0);
                break;
            case "<-":
                if (!currentExpression.isEmpty()) {
                    currentExpression.deleteCharAt(currentExpression.length() - 1);
                    expressionField.setText(currentExpression.toString());
                }
                break;
            case "=":
                currentExpression.append(number.toString());
                System.out.println("currentExpression = " + currentExpression.toString());
                char lastChar = currentExpression.charAt(currentExpression.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                    System.out.println("currentExpression ends in a math op");
                    currentExpression.deleteCharAt(currentExpression.length() - 1);
                }
                evaluateExpression();
                expressionField.setText(currentExpression.toString());
                number.setLength(0);
                break;
            default:
                System.out.println("command = " + command);
                System.out.println("currentExpression = " + currentExpression.toString());
                if (command.matches("[-+*/]")) {
                    System.out.println("Math op typed: " + command);
                    currentExpression.append(number.toString());
                    evaluateExpression();
                    currentExpression.append(command);
                    number.setLength(0);
                    expressionField.setText(currentExpression.toString());
                } else {
                    System.out.println("Number typed: "  + command);
                    number.append(command);
                    resultField.setText(number.toString());
                    expressionField.setText(currentExpression.toString());
                }
                break;
        }
    }

    private void evaluateExpression() {
        if (currentExpression.isEmpty()) return;

        try {
            String expStr = currentExpression.toString();

            System.out.println("In evaluateExpression(); expStr = " + expStr);
            // exp4j doesn't implicitly handle leading operators or trailing dots gracefully
            // Basic validation can go here

            Expression exp = new ExpressionBuilder(expStr).build();
            double result = exp.evaluate();

            // Format to remove trailing .0 for whole numbers
            if (result % 1 == 0) {
                resultField.setText(String.valueOf((long) result));
            } else {
                resultField.setText(String.valueOf(result));
            }
        } catch (Exception e) {
            resultField.setText("Error");
        }
    }
}

/**
 StringBuilder mathBuilder = new StringBuilder();
 // Expression expression = new Expression(mathBuilder);

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
 addNumberToField(outputField, operationsField, btnText,
 numberFormat, mathBuilder);
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
 }
        });

                // Setup clear button
                clearBtn.setOnAction(event -> {
        outputField.setText("");
            operationsField.setText("");
            mathBuilder.setLength(0);
            System.out.println("mathBuilder: " + mathBuilder.toString() + "\n");
        });

        backspaceBtn.setOnAction(event -> {
        if (outputField.getText() != null &&
        !outputField.getText().isEmpty()) {
String truncated = outputField.getText().substring(0,
        outputField.getText().length() - 1);
                outputField.setText(truncated);
            }
                    });

                    // we start a new number and put it only in outputField
                    // we'll add the new number to builder only if the equalsBtn or an operation button is clicked
                    // StringBuilder is the better option for catenating new pieces of a string, but String is required
                    // for using ExpressionBuilder
                    plusBtn.setOnAction(event -> {
//System.out.println("mathBuilder = " + mathBuilder);

/** THREE OPTIONS
 * 1) operationsField ends in +-/* and number
 * 2) operationsField ends in number alone
 * 3) operationsField ends in +-/*


String output ="";
// -OR- operationsField.getText().endsWith("\\d+")
            if (operationsField.getText().endsWith("\\d$")) { // operationsField ends in a number
        if (operationsField.getText().contains(".")) {
Expression math = new ExpressionBuilder(operationsField.getText()).build();
BigDecimal numberBigDecimal = new BigDecimal(math.evaluate());
                    outputField.setText(numberFormat.format(numberBigDecimal));
        } else {
Expression math = new ExpressionBuilder(operationsField.getText()).build();
long numberLong = Long.parseLong(math.toString());
                    outputField.setText(numberFormat.format(numberLong));
        }

        operationsField.setText(operationsField.getText() + " + ");

        } else { // operationsField is empty or ends in a math operation
        // we start a new number, put it in outputField
        // we'll add the new number to builder only if the equalsBtn or some operation button is clicked

        }

        // if the operationsField has nothing in it
        if (operationsField.getText() == null || operationsField.getText().isEmpty()) {
        //System.out.println("mathBuilder is empty");
        operationsField.setText(mathBuilder.toString());
        } else {
        // if the math string ends in a math operator and space
/**        if (operationsField.getText().endsWith("[+\\-*/  /** =] ")) {
        operationsField.setText(mathBuilder.toString());
        }

        // if the math string ends in a number
        if (operationsField.getText().endsWith("\\d+")) {

        }

        if (!(mathBuilder.toString().endsWith("[-*/  /** +]s?"))) {
        //System.out.println("mathBuilder.toString() = " + mathBuilder.toString());
        mathBuilder.append(" + ");
//System.out.println("mathBuilder.toString() = " + mathBuilder.toString());
                    operationsField.setText(mathBuilder.toString());
        }
        }
        });

        timesBtn.setOnAction(event -> {
        if (!(mathBuilder.toString().endsWith("[-*/ /** +]s?"))) {
        mathBuilder.append(" * ");
                operationsField.setText(mathBuilder.toString());
        }
        });

        minusBtn.setOnAction(event -> {
        if (!(mathBuilder.toString().endsWith("[-*/ /** +]s?"))) {
        mathBuilder.append(" - ");
                operationsField.setText(mathBuilder.toString());
        }
        });

        divideBtn.setOnAction(event -> {
        if (!(mathBuilder.toString().endsWith("[-*/   /** +]s?"))) {
        mathBuilder.append(" / ");
                operationsField.setText(mathBuilder.toString());
        }
        });

        leftParensBtn.setOnAction(event -> {

        });

        equalsBtn.setOnAction(event -> {
        if ((mathBuilder.toString().endsWith("s?[(0-9)|)]s?"))) {
String math = mathBuilder.toString();
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
                                     NumberFormat numberFormat, StringBuilder builder) {
    // StringBuilder is the better option for catenating new pieces of a string, but String is required
    // for using ExpressionBuilder
    String output = "";
    String number = outputField.getText().replace(",", "");
    if (!outputField.getText().contains(".")) { // outputField contains no decimal
        number = number + btnText;
        long safeNumber = Long.parseLong(number);
        output = numberFormat.format(safeNumber);
    } else if (outputField.getText().endsWith(".")) { // outputField contains only a decimal point
        number = number.replace(".", "");
        long safeNumberLong = Long.parseLong(number);
        output = numberFormat.format(safeNumberLong) + "." + btnText;
    } else { // outputField has a decimal and trailing numbers
        number = number + btnText;
        BigDecimal safeNumberBigDecimal = new BigDecimal(number);
        output = numberFormat.format(safeNumberBigDecimal);
    }
    outputField.setText(output);
}


/** String output = "";
        if (operationsField.getText() != null || operationsField.getText().isEmpty()) { // operationsField is empty
        outputField.setText(btnText);
            builder.append(btnText);
//System.out.println("builder = " + builder.toString();
        } else if (operationsField.getText().endsWith) { // operationsField ends in a math operator
        outputField.setText(btnText);
            builder.append(btnText);
        } else { // operationsField ends in a number
String number = outputField.getText().replace(",", "");
            if (!outputField.getText().contains(".")) { // outputField contains no decimal
number = number + btnText;
long safeNumber = Long.parseLong(number);
output = numberFormat.format(safeNumber);
            } else if (outputField.getText().endsWith(".")) { // outputField contains only a decimal point
number = number.replace(".", "");
long safeNumberLong = Long.parseLong(number);
output = numberFormat.format(safeNumberLong) + "." + btnText;
            } else { // outputField has a decimal and trailing numbers
number = number + btnText;
BigDecimal safeNumberBigDecimal = new BigDecimal(number);
output = numberFormat.format(safeNumberBigDecimal);
            }
                    outputField.setText(output);
            builder.append(btnText);
//System.out.println("builder = " + builder.toString());
        } */