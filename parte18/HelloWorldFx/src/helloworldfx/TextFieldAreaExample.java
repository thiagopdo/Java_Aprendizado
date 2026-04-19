package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextFieldAreaExample extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    TextField textField = new TextField();
    textField.setPromptText("Digite seu nome...");
    TextField textField2 = new TextField();
    textField2.setPromptText("Digite sua idade...");

    // limitar caracteres
    textField2.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.length() > 3) {
        textField2.setText(oldValue);
      }
    });

    TextArea textArea = new TextArea();
    textArea.setPromptText("Digite uma mensagem...");
    textArea.setPrefRowCount(5);

    Label label = new Label("Olá, JavaFX!");

    textArea.textProperty().addListener((observable, oldValue, newValue) -> {
      label.setText("Texto do textArea: " + newValue);
    });


    VBox vbox = new VBox();
    vbox.getChildren().addAll(textField, textField2, textArea, label);

    // cena
    Scene scene = new Scene(vbox, 300, 200);
    // configurando stage
    primaryStage.setTitle("Exemplo VBox e HBox");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
