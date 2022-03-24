package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ReqInTransportController {
  @FXML private Button AppButton;

  @FXML
  void AppButton() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/app.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }
}
