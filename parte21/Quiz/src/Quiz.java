package parte21.Quiz.src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Quiz extends Application {

  private int currentQuestionIndex = 0;
  private int score = 0;

  private String[] questions = {
      "What is the capital of France?",
      "What is 2 + 2?",
      "What is the largest planet in our solar system?"
  };

  private String[][] options = {
      { "Paris", "London", "Berlin", "Madrid" },
      { "3", "4", "5", "6" },
      { "Earth", "Mars", "Jupiter", "Saturn" }
  };

  private int[] correctAnswers = { 0, 1, 2 };

  private Label questionLabel;
  private ToggleGroup optionsGroup;
  private VBox root;

  @Override
  public void start(Stage primaryState) throws Exception {
    primaryState.setTitle("Quiz");

    // layout
    root = new VBox(10);
    root.setPadding(new Insets(20));
    root.setSpacing(10);

    // mostrar perguntas

    questionLabel = new Label(questions[currentQuestionIndex]);
    questionLabel.getStyleClass().add("question-label");
    root.getChildren().add(questionLabel);

    // add opções
    optionsGroup = new ToggleGroup();
    for (String option : options[currentQuestionIndex]) {
      RadioButton radioButton = new javafx.scene.control.RadioButton(option);
      radioButton.setToggleGroup(optionsGroup);
      radioButton.getStyleClass().add("option-button");
      root.getChildren().add(radioButton);
    }

    // prox pergunta

    Button nextButton = new Button("Next");
    nextButton.setOnAction(e -> handleNextQuestion());
    nextButton.getStyleClass().add("next-button");
    root.getChildren().add(nextButton);

    // cena
    Scene scene = new Scene(root, 400, 450);
    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    primaryState.setScene(scene);
    primaryState.show();
  }

  private void handleNextQuestion() {
    RadioButton selectedRadionButton = (RadioButton) optionsGroup.getSelectedToggle();
    if (selectedRadionButton != null) {
      int selectedIndex = getSelectedOptionIndex();

      if (selectedIndex == correctAnswers[currentQuestionIndex]) {
        score++;
      }

      // mudar de perguntar
      currentQuestionIndex++;

      if (currentQuestionIndex < questions.length) {
        updateQuestion();
      } else {
        showFinalScore();
      }

    }
  }

  private void showFinalScore() {
    root.getChildren().clear();
    Label scoreLabel = new Label("Your final score: " + score + "/" + questions.length);
    scoreLabel.getStyleClass().add("score-label");
    root.getChildren().add(scoreLabel);
  }

  private void updateQuestion() {
    questionLabel.setText(questions[currentQuestionIndex]);

    // remover as opcoes anterioes
    root.getChildren().removeIf(node -> node instanceof RadioButton);

    // adicionar as novas opções
    optionsGroup = new ToggleGroup();
    for (String option : options[currentQuestionIndex]) {
      RadioButton radioButton = new javafx.scene.control.RadioButton(option);
      radioButton.setToggleGroup(optionsGroup);
      radioButton.getStyleClass().add("option-button");
      root.getChildren().add(1, radioButton);
    }

  }

  private int getSelectedOptionIndex() {
    for (int i = 0; i < optionsGroup.getToggles().size(); i++) {
      if (optionsGroup.getToggles().get(i).isSelected()) {
        return i;
      }
    }
    return -1; // No option selected
  }

  public static void main(String[] args) {
    launch(args);
  }
}
