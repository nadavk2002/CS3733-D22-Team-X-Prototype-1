package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class equipmentDeliveryController {
  @FXML private Button ToMainMenu;
  @FXML private ChoiceBox<String> selectEquipmentType;
  @FXML private TextField amountField, roomField;
  @FXML private Label requestStatus;
  private EquipmentServiceRequest request;

  @FXML
  public void initialize() {
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
    roomField.setText("");
    selectEquipmentType.setValue("");
  }

  @FXML
  public void submitRequest() {
    // request.setAssignee(""); // empty for now
    // request.setRequestingUser(""); // empty for now
    request.setEquipmentType(selectEquipmentType.getValue());
    // Cant be text, must be selected from drop-down of nodeID's
    // request.setDestination(roomField.getText());
    request.setQuantity(Integer.parseInt(amountField.getText()));
    this.resetFields();
  }
}
