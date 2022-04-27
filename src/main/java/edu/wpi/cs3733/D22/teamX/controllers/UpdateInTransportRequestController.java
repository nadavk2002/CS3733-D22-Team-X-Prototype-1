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
import javafx.stage.Stage;

public class UpdateInTransportRequestController implements Initializable {
  @FXML private Button resetFields, submitButton;
  @FXML
  private ChoiceBox<String> selectPatient,
      selectStartLocation,
      serviceStatus,
      assignStaff,
      destination;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private List<Location> locations;
  private List<Employee> employees;
  private EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  private List<Patient> patients;
  private PatientDAO patientDAO = PatientDAO.getDAO();

  private InTransportServiceRequest request;

  public UpdateInTransportRequestController(InTransportServiceRequest request) {
    this.request = request;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();
    patients = patientDAO.getAllRecords();
    resetFields();
    submitButton.setDisable(false);
    selectPatient.setItems(this.getPatientIDs());
    assignStaff.setItems(this.getEmployeeIDs());
    //    selectLang.getItems().addAll(new String[] {"English", "Spanish", "French"});
    serviceStatus.getItems().addAll(" ", "PROC", "DONE");
    //    assignStaff.getItems().addAll("Staff1", "Staff2", "Staff3");
    selectStartLocation.setItems(getLocationNames());
    destination.setItems(getLocationNames());
    selectPatient.setOnAction((ActionEvent event) -> enableSubmitButton());
    selectStartLocation.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
    // serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());
    destination.setOnAction((ActionEvent event) -> enableSubmitButton());

    selectPatient.setValue(request.getPatientName().getPatientID());
    selectStartLocation.setItems(this.getLocationNames());
    destination.setItems(this.getLocationNames());
    assignStaff.setValue(this.request.getAssigneeID());
    selectStartLocation.setValue(request.getLocationShortName());
    destination.setValue(request.getLocationShortName());
    serviceStatus.setValue(this.request.getStatus());
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

  public ObservableList<String> getPatientIDs() {
    ObservableList<String> patientIDs = FXCollections.observableArrayList();
    for (int i = 0; i < patients.size(); i++) {
      patientIDs.add(patients.get(i).getPatientID());
    }
    return patientIDs;
  }

  public ObservableList<String> getEmployeeIDs() {
    ObservableList<String> employeeNames = FXCollections.observableArrayList();
    for (int i = 0; i < employees.size(); i++) {
      employeeNames.add(employees.get(i).getEmployeeID());
    }
    return employeeNames;
  }

  /** Checks if the submit button can be enabled depending on the inputs in fields on the page. */
  public void enableSubmitButton() {
    //    submitButton.setDisable(
    //        selectPatient.getValue().equals("")
    //            || selectStartLocation.getValue().equals("")
    //            || assignStaff.getValue().equals("")
    //            || destination.getValue().equals(""));
  }

  /** Resets all fields on the page. */
  @FXML
  public void resetFields() {
    selectPatient.setValue("");
    selectStartLocation.setValue("");
    serviceStatus.setValue(" ");
    assignStaff.setValue("");
    destination.setValue("");
  }

  /** Creates a service request from the fields on the javafx page */
  @FXML
  public void submitRequest() throws IOException {
    InTransportServiceRequest request = new InTransportServiceRequest();

    request.setRequestID(this.request.getRequestID());
    request.setPatientName(patientDAO.getRecord(selectPatient.getValue()));
    request.setTransportFrom(selectStartLocation.getValue());
    request.setAssignee(emplDAO.getRecord(assignStaff.getValue()));
    request.setStatus(serviceStatus.getValue());
    request.setDestination(locations.get(destination.getSelectionModel().getSelectedIndex()));
    ServiceRequestDAO.getDAO().updateRecord(request);
    Stage stage = (Stage) submitButton.getScene().getWindow();
    stage.close();
    this.resetFields();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  }
}
