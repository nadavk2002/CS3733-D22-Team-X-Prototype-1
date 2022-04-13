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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReqLaundryController implements Initializable {
  @FXML private VBox selectionMenuColumn;
  @FXML private VBox labelColumn;
  @FXML private VBox submitColumn;
  @FXML private HBox buttonRow;
  @FXML private Button ToMainMenu, submitButton;
  @FXML private ChoiceBox<String> selectLaundryType, roomNum, serviceStatus, assignStaff;
  // @FXML private TextField assignStaff;

  @FXML private TableView table;
  private TableColumn<LaundyServiceRequest, String> ID = new TableColumn("Request ID");
  private TableColumn<LaundyServiceRequest, String> assignee = new TableColumn("Assignee");
  private TableColumn<LaundyServiceRequest, String> status = new TableColumn("Status");
  private TableColumn<LaundyServiceRequest, String> locationColumn = new TableColumn("Location");
  private TableColumn<LaundyServiceRequest, String> laundryService =
      new TableColumn("Laundry Service");

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private List<Location> locations;

  private EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private List<Employee> employees;
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    resetFields();
    selectionMenuColumn.setSpacing(20);
    labelColumn.setSpacing(28);
    submitColumn.setSpacing(20);
    buttonRow.setSpacing(20);
    roomNum.setItems(getLocationNames());
    assignStaff.setItems(this.getEmployeeIDs());
    submitButton.setDisable(true);

    selectLaundryType
        .getItems()
        .addAll(new String[] {"Linens", "Gowns", "Bedding", "Scrubs", "Coats"});
    serviceStatus.getItems().addAll("", "PROC", "DONE");
//    assignStaff.getItems().addAll("Staff1", "Staff2", "Staff3");

    selectLaundryType.setOnAction((ActionEvent event) -> disableSubmitButton());
    roomNum.setOnAction((ActionEvent event) -> disableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> disableSubmitButton());
    serviceStatus.setOnAction((ActionEvent event) -> disableSubmitButton());

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table.getColumns().addAll(ID, assignee, locationColumn, status, laundryService);
    ID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    locationColumn.setCellValueFactory(new PropertyValueFactory<>("locationShortName"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    laundryService.setCellValueFactory(new PropertyValueFactory<>("laundry"));
    assignee.setCellValueFactory(new PropertyValueFactory<>("assignee"));
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
  public void disableSubmitButton() {
    submitButton.setDisable(
        assignStaff.getValue().equals("")
            || serviceStatus.getValue().equals("")
            || roomNum.getValue().equals("")
            || selectLaundryType.getValue().equals(""));
  }

  @FXML
  public void submitRequest() {
    LaundyServiceRequest request = new LaundyServiceRequest();

    request.setRequestID(request.makeRequestID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(emplDAO.getRecord(assignStaff.getValue())); // FIX
    request.setService(selectLaundryType.getValue());
    this.resetFields();
    table.getItems().add(request);
  }

  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException if main menu not switched to
   */
  @FXML
  public void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  public void resetFields() {
    selectLaundryType.setValue("");
    roomNum.setValue("");
    assignStaff.setValue("");
    serviceStatus.setValue("");
  }
}
