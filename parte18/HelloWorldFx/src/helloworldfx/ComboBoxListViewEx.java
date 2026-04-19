package helloworldfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComboBoxListViewEx extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    // combobox
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.getItems().addAll("Opção 1", "Opção 2", "Opção 3");
    comboBox.setPromptText("Selecione uma opção");

    Label comboBoxLabel = new Label("Selecione uma opçao: ");
    comboBox.setOnAction(event -> {
      String selected = comboBox.getSelectionModel().getSelectedItem();

      comboBoxLabel.setText("SeleçAo no combobox: " + selected);
    });

    // ListView
    ObservableList<String> items = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3", "Item 4");

    ListView<String> listView = new ListView<>(items);
    listView.setPrefHeight(200);

    Label listViewLabel = new Label("Selecione um item da lista: ");

    listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
      listViewLabel.setText("Item selecionado na lista: " + newVal);
    });

    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    VBox vbox = new VBox();
    vbox.getChildren().addAll(comboBox, comboBoxLabel, listView, listViewLabel);

    // cena
    Scene scene = new Scene(vbox, 400, 400);
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
