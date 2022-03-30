package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class MedicalEquipmentDeliveryController {
  @FXML private Button ToMainMenu;
  @FXML private ChoiceBox<String> selectEquipmentType, selectDestination, selectStatus;
  @FXML private TextField amountField;
  @FXML private Button submitButton;
  private EquipmentServiceRequest request;

  @FXML
  public void initialize() {
    submitButton.setDisable(true);
    selectStatus.getItems().addAll("", "Processing", "Done");
    request = new EquipmentServiceRequest();
    selectEquipmentType.getItems().addAll("Beds (20)", "X-Rays (1)", "Pumps (30)", "Recliners (6)");
  }

  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException
   */
  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  /** When Reset Fields button is pressed, the fields on the screen are reset to default. */
  @FXML
  public void resetFields() {
    amountField.setText("");
    selectEquipmentType.setValue("");
    selectDestination.setValue("");
    selectStatus.setValue("");
  }

  @FXML
  public void checkForInt() {
    submitButton.setDisable(
        !amountField.getText().matches("[0-9]+") || amountField.getText().length() == 0);
  }

  @FXML
  public void submitRequest() {
    request.setEquipmentType(selectEquipmentType.getValue());
    request.setRequestID("SAMPLE12");
    request.setDestination(new Location());
    request.setStatus(selectStatus.getValue());
    request.setQuantity(Integer.parseInt(amountField.getText()));
    this.resetFields();
  }
}
