package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class LabRequestController implements Initializable {
  @FXML private Button ReturnToMain;
  @FXML private ChoiceBox<String> SelectLab;
  @FXML private TextField patientName, assignStaff, serviceStatus;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    SelectLab.getItems().addAll("Blood Sample", "Urine Sample", "X-Ray", "CAT Scan", "MRI");
  }

  @FXML
  public void ReturnToMain() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  /** When Reset Fields button is pressed, the fields on the screen are reset to default. */
  @FXML
  public void resetFields() {
    patientName.setText("");
    assignStaff.setText("");
    serviceStatus.setText("");
    SelectLab.setValue("");
  }
}
