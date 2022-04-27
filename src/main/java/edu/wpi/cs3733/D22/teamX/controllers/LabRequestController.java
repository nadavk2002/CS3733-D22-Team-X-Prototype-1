package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class LabRequestController implements Initializable {
  @FXML private TableColumn<LabServiceRequest, String> requestID;
  @FXML private TableColumn<LabServiceRequest, String> patientID;
  @FXML private TableColumn<LabServiceRequest, String> assigneeTable;
  @FXML private TableColumn<LabServiceRequest, String> service;
  @FXML private TableColumn<LabServiceRequest, String> status;
  @FXML private TableColumn<LabServiceRequest, String> destination;
  @FXML private Button ReturnToMain;
  @FXML private Button submitRequest;
  @FXML
  private JFXComboBox<String> selectLab,
      patientName,
      assigneeDrop,
      serviceStatus,
      selectDestination;
  private List<Location> locations;
  private LocationDAO locationDAO = LocationDAO.getDAO();
  private EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private List<Employee> employees;
  //  private LabServiceRequestDAO labDAO = LabServiceRequestDAO.getDAO()
  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();

    selectDestination.setItems(this.getLocationNames());
    submitRequest.setDisable(true);
    selectLab.getSelectionModel().select("");
    patientName.getSelectionModel().select("");
    assigneeDrop.getSelectionModel().select("");
    serviceStatus.getSelectionModel().select("");
    selectDestination.getSelectionModel().select("");

    checkAllBoxes(selectLab);
    checkAllBoxes(patientName);
    checkAllBoxes(assigneeDrop);
    checkAllBoxes(serviceStatus);
    checkAllBoxes(selectDestination);
    // FORMATTING----------------------------------------------------
    serviceStatus.getItems().addAll(" ", "PROC", "DONE");
    patientName.getItems().addAll("Patient 1", "Patient 2", "Patient 3", "Patient 4", "Patient 5");
    assigneeDrop.setItems(this.getEmployeeIDs());
    //    assigneeDrop
    //        .getItems()
    //        .addAll("Doctor 1", "Doctor 2", "Doctor 3", "Nurse 1", "Nurse 2", "Nurse 3");
    selectLab
        .getItems()
        .addAll("Blood Work", "MRI", "Urine Sample", "Stool Sample", "Saliva Sample");

    // TABLE COLUMN PLACING----------------------------------------------------

  }

  public ObservableList<String> getLocationNames() {
    ObservableList<String> locationNames = FXCollections.observableArrayList();
    for (int i = 0; i < locations.size(); i++) {
      locationNames.add(locations.get(i).getShortName());
    }
    return locationNames;
  }

  public ObservableList<String> getEmployeeIDs() {
    ObservableList<String> employeeIDs = FXCollections.observableArrayList();
    for (int i = 0; i < employees.size(); i++) {
      employeeIDs.add(employees.get(i).getEmployeeID());
    }
    return employeeIDs;
  }

  /** When Reset Fields button is pressed, the fields on the screen are reset to default. */
  @FXML
  public void resetFields() {
    patientName.setValue("");
    assigneeDrop.setValue("");
    serviceStatus.setValue("");
    selectLab.setValue("");
    selectDestination.setValue("");
  }

  public void checkAllBoxes(JFXComboBox<String> choiceBox) {
    choiceBox
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                submitRequest.setDisable(
                    patientName.getValue().matches("")
                        || selectLab.getValue().matches("")
                        || assigneeDrop.getValue().matches("")
                        //                        || serviceStatus.getValue().matches("")
                        || selectDestination.getValue().matches("")));
  }

  private ObservableList<LabServiceRequest> labDeliveryList() {
    ObservableList<LabServiceRequest> labList = FXCollections.observableArrayList();
    //    LabServiceRequestDAO allLabs = LabServiceRequestDAO.getDAO();
    List<LabServiceRequest> inpLabsList = requestDAO.getAllLabServiceRequests();
    labList.addAll(inpLabsList);
    return labList;
  }

  @FXML
  public void submitRequest() {
    LabServiceRequest request = new LabServiceRequest();

    request.setRequestID(requestDAO.makeLabServiceRequestID()); //
    request.setPatientFor(patientName.getValue());
    request.setAssignee(emplDAO.getRecord(assigneeDrop.getValue()));
    request.setService(selectLab.getValue());
    request.setStatus(serviceStatus.getValue());
    request.setDestination(
        locations.get(selectDestination.getSelectionModel().getSelectedIndex())); //
    requestDAO.addRecord(request);
    this.resetFields();
  }

  @FXML
  public void ReturnToMain() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml"))));
  }
}
