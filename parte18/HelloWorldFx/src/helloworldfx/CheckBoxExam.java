package helloworldfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckBoxExam extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    // criando checbox
    CheckBox checkBox1 = new CheckBox("Opção 1");
    CheckBox checkBox2 = new CheckBox("Opção 2");

    Label checkboxLabel = new Label("Check box selecionado: ");

    checkBox1.setOnAction(event -> updateCheckBoxLabel(checkBox1, checkBox2, checkboxLabel));
    checkBox2.setOnAction(event -> updateCheckBoxLabel(checkBox1, checkBox2, checkboxLabel));

    // radiobuttons
    RadioButton radioButton1 = new RadioButton("Opção A");
    RadioButton radioButton2 = new RadioButton("Opção B");

    ToggleGroup toggleGroup = new ToggleGroup();
    radioButton1.setToggleGroup(toggleGroup);
    radioButton2.setToggleGroup(toggleGroup);

    Label radioLabel = new Label("Radio button selecionado: ");

    toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        radioLabel.setText("Radio button selecionado: " + selectedRadioButton.getText());
      }
    });

    VBox vbox = new VBox();
    vbox.getChildren().addAll(checkBox1, checkBox2, checkboxLabel, radioButton1, radioButton2, radioLabel);

    // cena
    Scene scene = new Scene(vbox, 300, 200);
    // configurando stage
    primaryStage.setTitle("Exemplo VBox e HBox");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void updateCheckBoxLabel(CheckBox cb1, CheckBox cb2, Label checkboxLabel) {
    String selectedOptions = "Check box selecionado: ";
    if (cb1.isSelected()) {
      selectedOptions += cb1.getText() + ";  ";
    }
    if (cb2.isSelected()) {
      selectedOptions += cb2.getText() + ";  ";
    }
    checkboxLabel.setText(selectedOptions);
  }

  public static void main(String[] args) {
    launch(args);
  }
}