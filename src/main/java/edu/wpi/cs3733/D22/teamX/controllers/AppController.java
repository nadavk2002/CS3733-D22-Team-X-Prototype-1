package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
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
  @FXML private Button graphicalMapEditorButton;
  @FXML private Button EquipReqTableButton;

  @FXML
  void ReqLangButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ReqLang.fxml")));
    // Parent root =
    // FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/views/ReqLang.fxml"));
    // App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void ReqLaundryButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ReqLaundry.fxml")));
  }

  @FXML
  void ReqMedicineDelivery() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/Medicine_Delivery.fxml")));
  }

  @FXML
  void ReqInTransportButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ReqInTransport.fxml")));
  }

  @FXML
  void mealReqButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/mealRequest.fxml")));
  }

  @FXML
  void LabRequestButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/LabRequest.fxml")));
  }

  @FXML
  void equipmentRequestButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/equipmentDelivery.fxml")));
  }

  @FXML
  void graphicalMapEditorButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
  }

  @FXML
  void EquipReqTableButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/EquipReqTable.fxml")));
  }

  @FXML
  void ExitApplication() throws IOException {
    Platform.exit();
  }
}
