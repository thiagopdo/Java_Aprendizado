
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Cronometro
    extends Application {

  private Label timeLabel = new Label("00:00:00");
  private int secondsElapsed = 0;
  private Timeline timeline;

  @Override
  public void start(Stage primaryState) throws Exception {
    primaryState.setTitle("Cronometro");

    // layout
    VBox root = new VBox();
    root.setPadding(new Insets(20));
    root.setSpacing(10);

    // display do tempo
    timeLabel.setId("timeLabel");
    root.getChildren().add(timeLabel);

    // layout botoes
    HBox buttonBox = new HBox();
    buttonBox.setSpacing(10);

    Button startButton = new Button("Start");
    startButton.setMinSize(100, 50);
    startButton.setId("startButton");
    startButton.setOnAction(e -> startTimer());

    Button stopButton = new Button("Stop");
    stopButton.setMinSize(100, 50);
    stopButton.setId("stopButton");
    stopButton.setOnAction(e -> stopTimer());
    Button resetButton = new Button("Reset");
    resetButton.setMinSize(100, 50);
    resetButton.setId("resetButton");
    resetButton.setOnAction(e -> resetTimer());

    startButton.setId("startButton");
    stopButton.setId("stopButton");
    resetButton.setId("resetButton");

    buttonBox.getChildren().addAll(startButton, stopButton, resetButton);

    root.getChildren().addAll(buttonBox);

    // scene
    Scene scene = new Scene(root, 375, 200);
    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
    primaryState.setScene(scene);
    primaryState.show();

  }

  private void startTimer() {
    if (timeline == null || timeline.getStatus() != Timeline.Status.RUNNING) {
      timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.play();
    }
  }

  private void stopTimer() {
    if (timeline != null) {
      timeline.stop();
    }
  }

  private void resetTimer() {
    stopTimer();
    secondsElapsed = 0;
    updateTimerDisplay();
  }

  private void updateTimer() {
    secondsElapsed++;
    updateTimerDisplay();
  }

  private void updateTimerDisplay() {
    int hours = secondsElapsed / 3600;
    int minutes = (secondsElapsed % 3600) / 60;
    int seconds = secondsElapsed % 60;
    timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
  }

  public static void main(String[] args) {
    launch(args);
  }
}