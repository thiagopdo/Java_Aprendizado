
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora
    extends Application {

  private Label display = new Label("");
  private String currentInput = "";
  private String operator = "";
  private double priviousValue = 0;

  @Override
  public void start(Stage primaryState) throws Exception {
    primaryState.setTitle("Calculadora Simples");

    // layout
    VBox root = new VBox();
    root.setPadding(new Insets(20));
    root.setSpacing(10);

    // display
    display.setId("display");
    display.setMinSize(200, 50);
    display.setMaxSize(200, 50);
    display.setMaxWidth(Double.MAX_VALUE);
    VBox.setVgrow(display, Priority.NEVER);
    root.getChildren().add(display);

    // layout dos botoes
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(10));

    String[] buttons = {
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "0", "C", "=", "+"
    };

    int row = 0;
    int col = 0;
    for (String text : buttons) {
      Button button = new Button(text);
      button.setMinSize(50, 50);

      // evento
      button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleButtonPress(text));
      grid.add(button, col, row);

      col++;
      if (col > 3) {
        col = 0;
        row++;
      }
    }

    root.getChildren().add(grid);

    // scene
    Scene scene = new Scene(root, 300, 400);
    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    primaryState.setScene(scene);
    primaryState.show();

  }

  // logica de calculo
  private void handleButtonPress(String value) {
    switch (value) {
      case "C":
        currentInput = "";
        operator = "";
        priviousValue = 0;
        display.setText("");
        break;
      case "=":
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
          double currentValue = Double.parseDouble(currentInput);
          double result = calculate(priviousValue, currentValue, operator);
          display.setText(String.valueOf(result));
          currentInput = String.valueOf(result);
          operator = "";
          priviousValue = 0;
          return;
        }
        break;
      case "+", "-", "*", "/":
        if (!currentInput.isEmpty()) {
          operator = value;
          priviousValue = Double.parseDouble(currentInput);
          currentInput = "";

        }
        break;
      default:
        currentInput += value;
        display.setText(currentInput);
        break;
    }
  }

  // realizar calculo
  private double calculate(double a, double b, String op) {
    double result = 0;
    switch (op) {
      case "+":
        result = a + b;
        break;
      case "-":
        result = a - b;
        break;
      case "*":
        result = a * b;
        break;
      case "/":
        if (b != 0) {
          result = a / b;
        } else {
          display.setText("Erro: Divisão por zero");
          return 0;
        }
        break;
    }
    display.setText(Double.toString(result));
    currentInput = Double.toString(result);
    operator = "";
    priviousValue = 0;
    return result;
  }

  public static void main(String[] args) {
    launch(args);
  }
}