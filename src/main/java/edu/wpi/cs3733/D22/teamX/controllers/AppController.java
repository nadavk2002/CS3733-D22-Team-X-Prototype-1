package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class AppController implements Initializable {
  @FXML TextField searchBox;
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
  @FXML private ToggleButton nameToggle;
  @FXML
  private Label mainTitle,
      nameTextOne,
      nameTextTwo,
      nameTextThree,
      nameTextFour,
      nameTextFive,
      nameTextSix,
      nameTextSeven,
      nameTextEight,
      nameTextNine;
  ObservableList<Button> buttonList = FXCollections.observableArrayList();

  @FXML
  void nameToggleButton(ActionEvent event) {
    if (nameTextOne.isVisible()) {
      nameTextOne.setVisible(false);
      nameTextTwo.setVisible(false);
      nameTextThree.setVisible(false);
      nameTextFour.setVisible(false);
      nameTextFive.setVisible(false);
      nameTextSix.setVisible(false);
      nameTextSeven.setVisible(false);
      nameTextEight.setVisible(false);
      nameTextNine.setVisible(false);
    } else {
      nameTextOne.setVisible(true);
      nameTextTwo.setVisible(true);
      nameTextThree.setVisible(true);
      nameTextFour.setVisible(true);
      nameTextFive.setVisible(true);
      nameTextSix.setVisible(true);
      nameTextSeven.setVisible(true);
      nameTextEight.setVisible(true);
      nameTextNine.setVisible(true);
    }
  }

  //  void searchButtons() {
  //
  //    FilteredList<Button> filteredButtons = new FilteredList<>(buttonList, b -> true);
  //    searchBox
  //        .textProperty()
  //        .addListener(
  //            (observable, oldValue, newValue) -> {
  //              filteredButtons.setPredicate(
  //                  button -> {
  //                    if (newValue == null || newValue.isEmpty()) {
  //                      button.setVisible(true);
  //                      return true;
  //                    }
  //                    String lowerCaseFilter = newValue.toLowerCase();
  //                    if (button.getText().toLowerCase().contains(lowerCaseFilter)) {
  //                      button.setVisible(true);
  //                      searchBox.setOnKeyPressed(
  //                          event -> {
  //                            if (event.getCode() == KeyCode.ENTER) {
  //                              button.fire();
  //                            }
  //                          });
  //                      return true;
  //                    } else {
  //                      button.setVisible(false);
  //
  //                      return false;
  //                    }
  //                  });
  //            });
  //  }

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

  //  @FXML
  //  void graphicalMapEditorButton() throws IOException {
  //    App.switchScene(
  //        FXMLLoader.load(
  //            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
  //  }
  //
  //  @FXML
  //  void EquipReqTableButton() throws IOException {
  //    App.switchScene(
  //        FXMLLoader.load(
  //
  // getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  //  }

  @FXML
  void GiftDeliveryButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GiftDelivery.fxml")));
  }

  //  @FXML
  //  void ExitApplication() throws IOException {
  //    App.switchScene(
  //        FXMLLoader.load(
  //            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CSVFileSaver.fxml")));
  //    CSVFileSaverController.loaded = true;
  //    // Platform.exit();
  //  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    buttonList.addAll(
        mealReq,
        GiftDelivery,
        ReqJanitor,
        ReqLaundry,
        ReqLang,
        ReqInTransport,
        LabRequest,
        ReqMedicineDelivery,
        // EquipReqTable,
        // graphicalMapEditor,
        equipmentRequest);
    searchBox.setVisible(false);
    // searchBox.setPromptText("Search Here");
    //    firstRow.getChildren().addAll(equipmentRequest, graphicalMapEditor, EquipReqTable);
    //    secondRow.getChildren().addAll(ReqMedicineDelivery, LabRequest, ReqInTransport, ReqLang);
    //    thirdRow.getChildren().addAll(ReqLaundry, ReqJanitor, GiftDelivery, mealReq);
    //    mainBox.getChildren().addAll(mainTitle, firstRow, secondRow, thirdRow);
    //    mainBox.setMargin(mainTitle, new Insets(40, 0, 40, 0));
    // searchButtons();
    nameToggle.setOnAction(this::nameToggleButton);
  }
}
