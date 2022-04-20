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

public class UpdateMaintenanceRequestController implements Initializable {
  @FXML private Button submitButton;
  @FXML private ChoiceBox<String> roomNum, status, assignee;
  @FXML private TextArea description;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private ServiceRequestDAO maintenanceDAO = ServiceRequestDAO.getDAO();
  private List<Location> locations;
  private List<Employee> employees;
  private EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private TableColumn<MaintenanceServiceRequest, String> idColumn = new TableColumn("Request ID");
  private TableColumn<MaintenanceServiceRequest, String> assigneeColumn =
      new TableColumn("Assignee");
  private TableColumn<MaintenanceServiceRequest, String> locationColumn =
      new TableColumn("Location");
  private TableColumn<MaintenanceServiceRequest, String> statusColumn =
      new TableColumn("Request Status");
  private TableColumn<MaintenanceServiceRequest, String> descriptionColumn =
      new TableColumn("Description");
  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  private MaintenanceServiceRequest request;

  public UpdateMaintenanceRequestController(MaintenanceServiceRequest request) {
    this.request = request;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();
    resetFields();
    status.getItems().addAll("", "PROC", "DONE");
    assignee.setItems(getEmployeeIDs());
    roomNum.setItems(getLocationNames());
    roomNum.setOnAction((ActionEvent event) -> enableSubmitButton());
    // description.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignee.setOnAction((ActionEvent event) -> enableSubmitButton());
    status.setOnAction((ActionEvent event) -> enableSubmitButton());

    roomNum.setItems(this.getLocationNames());
    assignee.setValue(this.request.getAssigneeID());
    roomNum.setValue(request.getLocationShortName());
    description.setText(this.request.getDescription());
    status.setValue(this.request.getStatus());
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
            || assignee.getValue().equals("")
            || description.getLength() <= 0
            || description.getLength() > 140);
  }

  /** Resets all fields on the page. */
  @FXML
  public void resetFields() {
    assignee.setValue("");
    roomNum.setValue("");
    status.setValue("");
    description.clear();
  }

  /** Creates a service request from the fields on the javafx page */
  @FXML
  public void submitButton() throws IOException {
    MaintenanceServiceRequest request = new MaintenanceServiceRequest();
    request.setRequestID(this.request.getRequestID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(status.getValue());
    request.setAssignee(emplDAO.getRecord(assignee.getValue()));
    request.setDescription(description.getText());
    // maintenanceDAO.addRecord(request);
    ServiceRequestDAO.getDAO().updateRecord(request);
    Stage stage = (Stage) submitButton.getScene().getWindow();
    stage.close();
    this.resetFields();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  }
}
