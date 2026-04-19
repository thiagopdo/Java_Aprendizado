package helloworldfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StackPaneExample extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    // criar botoes VBox
    Button button1 = new Button("Botão 1");
    Button button2 = new Button("Botão 2");
    Button button3 = new Button("Botão 3");
    Button button4 = new Button("Botão 4");
    Button button5 = new Button("Botão 5");

    // criar stackpane
    StackPane stackPane = new StackPane();

    stackPane.getChildren().addAll(button1, button2, button3, button4, button5);

    Scene stackScene = new Scene(stackPane, 400, 300);

    // criar anchorpane
    AnchorPane anchorPane = new AnchorPane();

    AnchorPane.setTopAnchor(button3, 10.0);
    AnchorPane.setLeftAnchor(button3, 10.0);

    anchorPane.getChildren().add(button3);

    Scene anchorScene = new Scene(anchorPane, 400, 300);

    // configurando stage
    primaryStage.setTitle("Exemplo VBox e HBox");
    primaryStage.setScene(stackScene);
    primaryStage.show();

    // mudar cena
    new Thread(() -> {
      try {
        Thread.sleep(2000);

        Platform.runLater(() -> {
          primaryStage.setScene(anchorScene);
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
