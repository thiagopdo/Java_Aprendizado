package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonClickExam extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    Label label = new Label("Clique no botão para ver o resultado");

    Button button = new Button("Clique aqui");
    button.setOnAction(event->{
      label.setText("Clicou no botao");
    });
   

    VBox vbox = new VBox();
    vbox.getChildren().addAll(button, label);

    // cena
    Scene scene = new Scene(vbox, 400, 400);
    // configurando stage
    primaryStage.setTitle("Exemplo VBox e HBox");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void updateCheckBoxLabel(CheckBox cb1, CheckBox cb2, Label checkboxLabel) {
    String selectedOptions = "Check box selecionado: ";
    if (cb1.isSelected()) {
      selectedOptions += cb1.getText() + ";  ";
    }
    if (cb2.isSelected()) {
      selectedOptions += cb2.getText() + ";  ";
    }
    checkboxLabel.setText(selectedOptions);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
