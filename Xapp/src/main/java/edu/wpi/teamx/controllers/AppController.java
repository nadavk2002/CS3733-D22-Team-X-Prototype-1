package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class AppController {
  @FXML private Button ReqLangButton;
  @FXML private Button ReqLaundryButton;
  @FXML private Button ReqMedicineDelivery;
  @FXML private Button ReqInTransportButton;
  @FXML private Button mealReqButton;
  @FXML private Button LabRequestButton;
  @FXML private Button equipmentRequestButton;

  @FXML
  void ReqLangButton() throws IOException {
    App.switchScene(FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/ReqLang.fxml")));
    // Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/ReqLang.fxml"));
    // App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void ReqLaundryButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/ReqLaundry.fxml")));
  }

  @FXML
  void ReqMedicineDelivery() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/Medicine_Delivery.fxml")));
  }

  @FXML
  void ReqInTransportButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/ReqInTransport.fxml")));
  }

  @FXML
  void mealReqButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/mealRequest.fxml")));
  }

  @FXML
  void LabRequestButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/LabRequest.fxml")));
  }

  @FXML
  void equipmentRequestButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/equipmentDelivery.fxml")));
  }

  @FXML
  void ExitApplication() throws IOException {
    Platform.exit();
    System.exit(0);
  }
}
