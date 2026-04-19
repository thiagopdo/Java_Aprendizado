package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MultiWindowsExam extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    Button button = new Button("Clique para abrir nova janela");
    button.setOnAction(event -> openNewWindow());

    StackPane primaryLayout = new StackPane();
    primaryLayout.getChildren().add(button);

    Scene primaryScene = new Scene(primaryLayout, 300, 200);

    // configurando stage
    primaryStage.setTitle("Exemplo VBox e HBox");
    primaryStage.setScene(primaryScene);
    primaryStage.show();
  }

  public void openNewWindow() {
    Stage newWindow = new Stage();
    Label label = new Label("Esta é a nova janela");

    StackPane secondaryLayout = new StackPane();
    secondaryLayout.getChildren().add(label);

    Scene secondScene = new Scene(secondaryLayout, 200, 100);

    newWindow.setTitle("Nova Janela");
    newWindow.setScene(secondScene);
    newWindow.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
