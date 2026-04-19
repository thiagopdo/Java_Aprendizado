package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CSSStylingExample extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    Label label = new Label("Label com estilo CSS");

    label.getStyleClass().add("custom-label");

    Button button = new Button("Botão estilizado");
    button.getStyleClass().add("button-label");

    VBox vbox = new VBox(10);
    vbox.getChildren().addAll(label, button);

    Scene scene = new Scene(vbox, 300, 200);
    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

    // configurando stage
    primaryStage.setTitle("Exemplo VBox e HBox");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
