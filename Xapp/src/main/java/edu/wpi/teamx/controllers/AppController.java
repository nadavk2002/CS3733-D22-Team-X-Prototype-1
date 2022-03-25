package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/ReqLang.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void ReqLaundryButton() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/ReqLaundry.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void ReqMedicineDelivery() throws IOException {
    Parent root =
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/Medicine_Delivery.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void ReqInTransportButton() throws IOException {
    Parent root =
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/ReqInTransport.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void mealReqButton() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/mealRequest.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void LabRequestButton() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/LabRequest.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void equipmentRequestButton() throws IOException {
    Parent root =
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/equipmentDelivery.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }
}
