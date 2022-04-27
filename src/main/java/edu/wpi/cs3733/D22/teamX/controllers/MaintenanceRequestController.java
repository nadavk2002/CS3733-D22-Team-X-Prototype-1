package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXComboBox;
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

public class MaintenanceRequestController implements Initializable {
  @FXML private Button mainMenu, submitButton;
  @FXML private JFXComboBox<String> roomNum, serviceStatus, assignee;
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();
    resetFields();
    submitButton.setDisable(true);
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    assignee.setItems(getEmployeeIDs());
    roomNum.setItems(getLocationNames());
    roomNum.setOnAction((ActionEvent event) -> enableSubmitButton());
    // description.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignee.setOnAction((ActionEvent event) -> enableSubmitButton());
    serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());
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
            || serviceStatus.getValue().equals("")
            || assignee.getValue().equals("")
            || description.getLength() <= 0
            || description.getLength() > 140);
  }

  /** Resets all fields on the page. */
  @FXML
  public void resetFields() {
    assignee.setValue("");
    roomNum.setValue("");
    serviceStatus.setValue("");
    description.clear();
  }

  /** Creates a service request from the fields on the javafx page */
  @FXML
  public void submitButton() {
    MaintenanceServiceRequest request = new MaintenanceServiceRequest();
    request.setRequestID(maintenanceDAO.makeMaintenanceServiceRequestID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(emplDAO.getRecord(assignee.getValue()));
    request.setDescription(description.getText());
    maintenanceDAO.addRecord(request);
    this.resetFields();
  }

  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException
   */
  @FXML
  public void mainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }
}
