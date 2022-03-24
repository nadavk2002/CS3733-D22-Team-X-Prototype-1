package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class AppController {
  @FXML private Button ReqLangButton;
  @FXML private Button mealReqButton;

  @FXML
  void ReqLangButton() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/ReqLang.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void mealReqButton() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/mealRequest.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }
}
