package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AppController implements Initializable {
  @FXML
  private JFXButton ReqLang,
      ReqLaundry,
      ReqMedicineDelivery,
      ReqInTransport,
      mealReq,
      LabRequest,
      equipmentRequest,
      graphicalMapEditor,
      EquipReqTable,
      ReqJanitor,
      GiftDelivery;
  @FXML private HBox firstRow, secondRow, thirdRow;
  @FXML private VBox mainBox;
  @FXML private Label mainTitle;

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
  void ReqMedicineDeliveryButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/Medicine_Delivery.fxml")));
  }

  @FXML
  void ReqJanitorButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/JanitorialRequest.fxml")));
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
  void GiftDeliveryButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GiftDelivery.fxml")));
  }

  @FXML
  void ExitApplication() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CSVFileSaver.fxml")));
    CSVFileSaverController.loaded = true;
    // Platform.exit();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    firstRow.getChildren().addAll(equipmentRequest, graphicalMapEditor, EquipReqTable);
    secondRow.getChildren().addAll(ReqMedicineDelivery, LabRequest, ReqInTransport, ReqLang);
    thirdRow.getChildren().addAll(ReqLaundry, ReqJanitor, GiftDelivery, mealReq);
    mainBox.getChildren().addAll(mainTitle, firstRow, secondRow, thirdRow);
    mainBox.setMargin(mainTitle, new Insets(40, 0, 40, 0));
  }
}
