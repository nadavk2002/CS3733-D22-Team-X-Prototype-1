package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class MedicineDeliveryController implements Initializable {
  @FXML private Button ReturnToMain, submitRequest;
  @FXML private ChoiceBox<String> patientName, roomNum, serviceStatus, assignStaff;
  @FXML private TextField rxNum;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private MedicineDeliverServiceRequestDAO medicineDAO = MedicineDeliverServiceRequestDAO.getDAO();
  private List<Location> locations;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    resetFields();
    submitRequest.setDisable(true);
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    assignStaff.getItems().addAll("Staff 1", "Staff 2", "Staff 3", "Staff 4");
    patientName.getItems().addAll("Patient 1", "Patient 2", "Patient 3", "Patient 4");
    roomNum.setItems(getLocationNames());
    patientName.setOnAction((ActionEvent event) -> enableSubmitButton());
    roomNum.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
    rxNum.setOnAction((ActionEvent event) -> enableSubmitButton());
  }

  /**
   * Creates a list of all locations with their short names.
   *
   * @return List of short names of all destinations
   */
  public ObservableList<String> getLocationNames() {
    ObservableList<String> locationNames = FXCollections.observableArrayList();
    for (int i = 0; i < locations.size(); i++) {
      locationNames.add(locations.get(i).getShortName());
    }
    return locationNames;
  }

  /** Checks if the submit button can be enabled depending on the inputs in fields on the page. */
  public void enableSubmitButton() {
    submitRequest.setDisable(
        roomNum.getValue().equals("")
            || rxNum.getText().equals("")
            || rxNum.getText().length() > 8
            || serviceStatus.getValue().equals("")
            || assignStaff.getValue().equals(""));
  }

  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException
   */
  @FXML
  public void ReturnToMain() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  /** resets all fields on the page. */
  @FXML
  public void resetFields() {
    patientName.setValue("");
    rxNum.setText("");
    roomNum.setValue("");
    assignStaff.setValue("");
    serviceStatus.setValue("");
  }

  /** Creates a service request from the fields on the javafx page */
  @FXML
  public void submitForm() {
    MedicineServiceRequest request = new MedicineServiceRequest();

    request.setRequestID(medicineDAO.makeID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(assignStaff.getValue());
    request.setRxNum(rxNum.getText());
    medicineDAO.addRecord(request);
    this.resetFields();
  }
}
