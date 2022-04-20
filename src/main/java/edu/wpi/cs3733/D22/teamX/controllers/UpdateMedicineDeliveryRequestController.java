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

public class UpdateMedicineDeliveryRequestController implements Initializable {
  @FXML private Button submitRequest;
  @FXML private ChoiceBox<String> patientName, roomNum, serviceStatus, assignStaff;
  @FXML private TextField rxNum;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  //  private MedicineDeliverServiceRequestDAO medicineDAO =
  // MedicineDeliverServiceRequestDAO.getDAO();
  private List<Location> locations;
  private EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private List<Employee> employees;
  private TableColumn<MedicineServiceRequest, String> idColumn = new TableColumn("Request ID");
  private TableColumn<MedicineServiceRequest, String> assigneeColumn = new TableColumn("Assignee");
  private TableColumn<MedicineServiceRequest, String> locationColumn = new TableColumn("Location");
  private TableColumn<MedicineServiceRequest, String> rxColumn = new TableColumn("Rx Number");
  private TableColumn<MedicineServiceRequest, String> statusColumn =
      new TableColumn("Request Status");
  private MedicineServiceRequest request;

  public UpdateMedicineDeliveryRequestController(MedicineServiceRequest request) {
    this.request = request;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();
    resetFields();
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    assignStaff.setItems(this.getEmployeeIDs());
    //    assignStaff.getItems().addAll("Staff 1", "Staff 2", "Staff 3", "Staff 4");
    patientName.getItems().addAll("Patient1", "Patient2", "Patient3", "Patient4");
    roomNum.setItems(getLocationNames());
    patientName.setOnAction((ActionEvent event) -> enableSubmitButton());
    roomNum.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
    rxNum.setOnAction((ActionEvent event) -> enableSubmitButton());
    //    serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());

    patientName.setValue(this.request.getPatientFor());
    roomNum.setItems(this.getLocationNames());
    assignStaff.setValue(this.request.getAssigneeID());
    roomNum.setValue(request.getLocationShortName());
    rxNum.setText(this.request.getRxNum());
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

  public ObservableList<String> getEmployeeIDs() {
    ObservableList<String> employeeNames = FXCollections.observableArrayList();
    for (int i = 0; i < employees.size(); i++) {
      employeeNames.add(employees.get(i).getEmployeeID());
    }
    return employeeNames;
  }

  /** Checks if the submit button can be enabled depending on the inputs in fields on the page. */
  public void enableSubmitButton() {
    submitRequest.setDisable(
        roomNum.getValue().equals("")
            || rxNum.getText().equals("")
            || rxNum.getText().length() > 8
            || serviceStatus.getValue().equals("")
            || patientName.getValue().equals("")
            || assignStaff.getValue().equals(""));
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
  public void submitRequest() throws IOException {
    MedicineServiceRequest request = new MedicineServiceRequest();

    request.setRequestID(this.request.getRequestID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(emplDAO.getRecord(assignStaff.getValue()));
    request.setRxNum(rxNum.getText());
    request.setPatientFor(patientName.getValue());
    // requestDAO.addRecord(request);
    ServiceRequestDAO.getDAO().updateRecord(request);
    Stage stage = (Stage) submitRequest.getScene().getWindow();
    stage.close();
    this.resetFields();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  }
}
