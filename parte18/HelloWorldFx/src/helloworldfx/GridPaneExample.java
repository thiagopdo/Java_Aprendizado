package parte18.HelloWorldFx.src.helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GridPaneExample extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// criar botoes VBox
		Button button1 = new Button("Botão 1");
		Button button2 = new Button("Botão 2");
		Button button3 = new Button("Botão 3");
		Button button4 = new Button("Botão 4");
		Button button5 = new Button("Botão 5");

		// criar gridpane
		GridPane gridPane = new GridPane();

		gridPane.add(button1, 2, 3);
		gridPane.add(button2, 0, 0);
		gridPane.add(button3, 1, 1);
		gridPane.add(button4, 2, 1);
		gridPane.add(button5, 2, 2);

		// criar scena
		Scene scene = new Scene(gridPane, 400, 300);

		// configurando stage
		primaryStage.setTitle("Exemplo VBox e HBox");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
