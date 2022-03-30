package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.entity.LaundyServiceRequest;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class reqLaundryController {
  @FXML private Button ToMainMenu;
  @FXML private ChoiceBox<String> selectLaundryType;
  @FXML private TextField roomField, assignStaff, serviceStatus;

  @FXML
  public void initialize() {
    selectLaundryType
        .getItems()
        .addAll(new String[] {"Linens", "Gowns", "Bedding", "Scrubs", "Coats"});
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

  @FXML
  void resetFields() {
    roomField.setText("");
    selectLaundryType.setValue("");
    assignStaff.setText("");
    serviceStatus.setText("");
  }

  @FXML
  void submitRequest() {
    LaundyServiceRequest request = new LaundyServiceRequest();
    request.setRequestID("SAMPLE12");
    request.setDestination(new Location());
    request.setStatus(serviceStatus.getText());
    this.resetFields();
  }
}
