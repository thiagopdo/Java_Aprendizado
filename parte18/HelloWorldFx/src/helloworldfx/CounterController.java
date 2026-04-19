package helloworldfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CounterController {

  private CounterModel model;

  @FXML
  private Label counterLabel;

  public CounterController() {
    model = new CounterModel();
  }

  @FXML
  private void initialize() {
    updateView();
  }

  @FXML
  private void handleIncrement() {
    model.increment();
    updateView();
  }
  @FXML
  private void handleDecrement() {
    model.decrement();
    updateView();
  }

  @FXML
  private void handleReset() {
    model.reset();
    updateView();
  }

  private void updateView() {
    counterLabel.setText(Integer.toString(model.getCount()));
  }

}
