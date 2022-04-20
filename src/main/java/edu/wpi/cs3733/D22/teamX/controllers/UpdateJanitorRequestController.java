package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateJanitorRequestController implements Initializable {
  @FXML private Button submitButton;
  @FXML private ChoiceBox<String> roomNum, serviceStatus, assignStaff;
  @FXML private TextField serviceType;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private ServiceRequestDAO janitorDAO = ServiceRequestDAO.getDAO();
  private List<Location> locations;
  private List<Employee> employees;
  private EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private TableColumn<JanitorServiceRequest, String> idColumn = new TableColumn("Request ID");
  private TableColumn<JanitorServiceRequest, String> assigneeColumn = new TableColumn("Assignee");
  private TableColumn<JanitorServiceRequest, String> locationColumn = new TableColumn("Location");
  private TableColumn<JanitorServiceRequest, String> statusColumn =
      new TableColumn("Request Status");
  private TableColumn<JanitorServiceRequest, String> serviceTypeColumn =
      new TableColumn("Service Type");

  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  private JanitorServiceRequest request;

  public UpdateJanitorRequestController(JanitorServiceRequest request) {
    this.request = request;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();
    resetFields();
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    serviceType.setText("");
    assignStaff.setItems(getEmployeeIDs());
    //    assignStaff.getItems().addAll("Janitor 1", "Janitor 2", "Janitor 3", "Janitor 4");
    roomNum.setItems(getLocationNames());
    roomNum.setOnAction((ActionEvent event) -> enableSubmitButton());
    serviceType.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
    // serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());

    roomNum.setItems(this.getLocationNames());
    serviceType.setText(this.request.getDescription());
    assignStaff.setValue(this.request.getAssigneeID());
    roomNum.setValue(request.getLocationShortName());
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
    submitButton.setDisable(
        roomNum.getValue().equals("")
            || serviceType.getText().equals("")
            || serviceStatus.getValue().equals("")
            || assignStaff.getValue().equals(""));
  }

  /** Resets all fields on the page. */
  @FXML
  public void resetFields() {
    serviceType.setText("");
    roomNum.setValue("");
    serviceStatus.setValue("");
    assignStaff.setValue("");
  }

  /** Creates a service request from the fields on the javafx page */
  @FXML
  public void submitButton() {
    JanitorServiceRequest request = new JanitorServiceRequest();

    request.setRequestID(this.request.getRequestID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(emplDAO.getRecord(assignStaff.getValue()));
    request.setDescription(serviceType.getText());
    // janitorDAO.updateRecord(request);
    ServiceRequestDAO.getDAO().updateRecord(request);
    Stage stage = (Stage) submitButton.getScene().getWindow();
    stage.close();
    this.resetFields();
  }
}
