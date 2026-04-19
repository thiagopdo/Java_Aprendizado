package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StageSceneExample extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    // criar botao
    Button btn = new Button("Clique aqui");

    // criar layout
    StackPane root = new StackPane();
    root.getChildren().add(btn);

    // criar scena
    Scene scene = new Scene(root, 400, 300);

    // configurando stage
    primaryStage.setTitle("Exemplo de Stage e Scene");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}