package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class LabRequestController {
  @FXML private Button ReturnToMain;

  @FXML
  public void ReturnToMain() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/app.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }
}
