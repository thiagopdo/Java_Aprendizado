package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonLabelExample extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    Label label = new Label("Olá, JavaFX!");

    Button button = new Button("Clique aqui");
    button.setOnAction(e -> label.setText("Você clicou no botão!"));

    VBox vbox = new VBox();
    vbox.getChildren().addAll(label, button);

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
