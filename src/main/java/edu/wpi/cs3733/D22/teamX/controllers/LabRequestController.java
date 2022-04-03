package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.entity.LabServiceRequest;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LabRequestController implements Initializable {
  @FXML private VBox dropdownCol;
  @FXML private VBox labelCol;
  @FXML private VBox submitCol;
  @FXML private HBox buttonRow;
  @FXML private Button ReturnToMain;
  @FXML private ChoiceBox<String> SelectLab, patientName, assignee, serviceStatus;
  LabServiceRequest request;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    submitCol.setSpacing(20);
    buttonRow.setSpacing(20);
    dropdownCol.setSpacing(20);
    labelCol.setSpacing(28);
    SelectLab.getItems().addAll("Blood Sample", "Urine Sample", "X-Ray", "CAT Scan", "MRI");
  }

  @FXML
  public void submitRequest() {
    request = new LabServiceRequest();
    request.setRequestID("SAMPLE12");
    request.setDestination(new Location());
    // request.setStatus(serviceStatus.getText());
    this.resetFields();
  }

  @FXML
  public void ReturnToMain() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  /** When Reset Fields button is pressed, the fields on the screen are reset to default. */
  @FXML
  public void resetFields() {
    patientName.setValue("");
    assignee.setValue("");
    serviceStatus.setValue("");
    SelectLab.setValue("");
  }
}
