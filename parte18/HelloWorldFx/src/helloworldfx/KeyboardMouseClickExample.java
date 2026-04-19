package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class KeyboardMouseClickExample extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    Label label = new Label("Clique no botão ou pressione Enter");
    StackPane root = new StackPane();

    root.getChildren().add(label);

    // capturar eventos do teclado
    root.setOnKeyPressed(event -> {
      String key = event.getCode().toString();
      label.setText("Tecla pressionada " + key);
    });

    //evento de mouse
    root.setOnMouseClicked(event-> {
      double x = event.getSceneX();
      double y = event.getSceneY();
      label.setText("Mouse clicado em (" + x + ", " + y + ")");
    });

    // cena
    Scene scene = new Scene(root, 300, 200);
    scene.setOnKeyPressed(root.getOnKeyPressed());
    scene.setOnMouseClicked(root.getOnMouseClicked());
    // configurando stage
    primaryStage.setTitle("Exemplo VBox e HBox");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
