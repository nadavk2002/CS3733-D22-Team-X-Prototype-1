package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.GiftDeliveryRequest;
import edu.wpi.cs3733.D22.teamX.entity.Location;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class GiftDeliveryController implements Initializable {
  @FXML private Button ReturnToMain;
  @FXML private TextField patientNameField, giftNoteField;
  @FXML private ChoiceBox<String> selectGiftDestination, selectAssignStaff, selectStatus;
  @FXML private Button submitButton;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    submitButton.setDisable(true);
    selectStatus.getItems().addAll("", "PROC", "DONE");
    selectAssignStaff.getItems().addAll("B", "X", "PPump", "R");
    selectGiftDestination.getItems().addAll("Room1", "Room2", "Room3");
  }

  @FXML
  public void ReturnToMain() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  public void resetFields() {
    patientNameField.setText("");
    giftNoteField.setText("");
    selectGiftDestination.setValue("");
    selectAssignStaff.setValue("");
    selectStatus.setValue("");
  }

  @FXML
  public void submitButton() {
    GiftDeliveryRequest request = new GiftDeliveryRequest();
    request.setRequestID("SAMPLE12");
    request.setDestination(new Location());
    request.setStatus(selectStatus.getValue());
    request.setNotes(giftNoteField.getText());
    request.setPatient(patientNameField.getText());
    request.setAssignee(selectAssignStaff.getValue());

    this.resetFields();
  }
}
