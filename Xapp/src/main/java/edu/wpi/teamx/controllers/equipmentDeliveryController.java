package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/edu/wpi/teamx/views/App.fxml")));
    App.getPrimaryStage().getScene().setRoot(root);
  }
}
