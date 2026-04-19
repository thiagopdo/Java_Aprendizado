package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertExam extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    Button buttonInfo = new Button("Alerta de info");
    buttonInfo.setOnAction(event -> {
      showAlert(AlertType.INFORMATION, "Alerta de Informação", "Esta é uma mensagem de informação.");
    });
    
    Button buttonError = new Button("Alerta de erro");
    buttonError.setOnAction(event -> {
      showAlert(AlertType.ERROR, "Alerta de Erro", "Esta é uma mensagem de erro.");
    });

    Button buttonWarning = new Button("Alerta de aviso");
    buttonWarning.setOnAction(event -> {
      showAlert(AlertType.WARNING, "Alerta de Aviso", "Esta é uma mensagem de aviso.");
    });

    Button buttonConfirmation = new Button("Alerta de confirmação");
    buttonConfirmation.setOnAction(event -> {
      showAlert(AlertType.CONFIRMATION, "Alerta de Confirmação", "Esta é uma mensagem de confirmação.");
    });

    VBox vbox = new VBox(10);
    vbox.getChildren().addAll(buttonInfo, buttonError, buttonWarning, buttonConfirmation);
    showAlert(AlertType.INFORMATION, "Alerta de Informação", "Esta é uma mensagem de informação.");

    Scene scene = new Scene(vbox, 300, 200);

    // configurando stage
    primaryStage.setTitle("Exemplo VBox e HBox");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void showAlert(AlertType alertType, String title, String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public static void main(String[] args) {
    launch(args);
  }
}