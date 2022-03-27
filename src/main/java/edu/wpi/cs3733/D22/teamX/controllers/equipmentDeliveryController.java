package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import java.awt.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;

public class equipmentDeliveryController {
  @FXML private Button ToMainMenu;
  @FXML private ChoiceBox<String> selectEquipmentType;

  @FXML
  public void initialize() {
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
}
