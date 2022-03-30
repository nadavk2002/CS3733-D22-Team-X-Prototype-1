package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.entity.MedicineServiceRequest;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MedicineDeliveryController {
  @FXML private Button ReturnToMain;
  @FXML private TextField patientName, rxNum, roomNum, serviceStatus, assignStaff;

  @FXML
  public void ReturnToMain() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  public void resetFields() {
    patientName.setText("");
    rxNum.setText("");
    roomNum.setText("");
    assignStaff.setText("");
    serviceStatus.setText("");
  }

  @FXML
  public void submitForm() {
    MedicineServiceRequest request = new MedicineServiceRequest();
    request.setRequestID("SAMPLE12");
    request.setDestination(new Location());
    request.setStatus(serviceStatus.getText());
    this.resetFields();
  }
}
