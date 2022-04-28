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
import javafx.stage.Stage;

public class UpdateLaundryRequestController implements Initializable {
  @FXML private Button submitButton;
  @FXML private JFXComboBox<String> selectLaundryType, roomNum, serviceStatus, assignStaff;
  // @FXML private TextField assignStaff;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  //  private LaundryServiceRequestDAO laundryDAO = LaundryServiceRequestDAO.getDAO();
  private List<Location> locations;

  private EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private List<Employee> employees;

  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  private LaundyServiceRequest request;

  public UpdateLaundryRequestController(LaundyServiceRequest request) {
    this.request = request;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();
    resetFields();
    submitButton.setDisable(false);
    roomNum.setItems(getLocationNames());
    assignStaff.setItems(this.getEmployeeIDs());
    selectLaundryType
        .getItems()
        .addAll(new String[] {"Linens", "Gowns", "Bedding", "Scrubs", "Coats"});
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    //    assignStaff.getItems().addAll("Staff1", "Staff2", "Staff3");

    selectLaundryType.setOnAction((ActionEvent event) -> enableSubmitButton());
    roomNum.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
    serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());

    selectLaundryType.setValue(this.request.getService());
    roomNum.setItems(this.getLocationNames());
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
    //    submitButton.setDisable(
    //        assignStaff.getValue().equals("")
    //            || roomNum.getValue().equals("")
    //            || selectLaundryType.getValue().equals(""));
  }

  @FXML
  public void submitRequest() throws IOException {
    LaundyServiceRequest request = new LaundyServiceRequest();

    request.setRequestID(this.request.getRequestID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(emplDAO.getRecord(assignStaff.getValue())); // FIX
    request.setService(selectLaundryType.getValue());
    // requestDAO.addRecord(request);
    ServiceRequestDAO.getDAO().updateRecord(request);
    Stage stage = (Stage) submitButton.getScene().getWindow();
    stage.close();
    this.resetFields();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  }

  @FXML
  public void resetFields() {
    selectLaundryType.setValue("");
    roomNum.setValue("");
    assignStaff.setValue("");
    serviceStatus.setValue("");
  }
}
