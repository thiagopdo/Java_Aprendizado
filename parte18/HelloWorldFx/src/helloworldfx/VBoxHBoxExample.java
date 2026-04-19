package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VBoxHBoxExample extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    // criar botoes VBox
    Button button1 = new Button("Botão 1");
    Button button2 = new Button("Botão 2");
    Button button3 = new Button("Botão 3");

    VBox vbox = new VBox(15);
    vbox.getChildren().addAll(button1, button2, button3);

    // criar botoes HBox
    Button button4 = new Button("Botão 4");
    Button button5 = new Button("Botão 5");
    Button button6 = new Button("Botão 6");

    HBox hbox = new HBox(25);
    hbox.getChildren().addAll(button4, button5, button6);

    VBox root = new VBox(20);
    root.getChildren().addAll(vbox, hbox);

    // criar scena
    Scene scene = new Scene(root, 400, 300);

    // configurando stage
    primaryStage.setTitle("Exemplo VBox e HBox");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
