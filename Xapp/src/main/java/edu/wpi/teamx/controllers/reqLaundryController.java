package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.awt.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;

public class reqLaundryController {
  @FXML private Button ToMainMenu;
  @FXML private ChoiceBox<String> selectLaundryType;

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
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/app.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }
}
