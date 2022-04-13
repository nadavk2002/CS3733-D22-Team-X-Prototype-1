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
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReqInTransportController implements Initializable {
  @FXML private Label l1, l2, l3, l4, l5, l6, title;
  @FXML private Button ToMainMenu, submitButton, resetFields;
  @FXML
  private ChoiceBox<String> transportFrom,
      transportTo,
      addAccommodation,
      serviceStatus,
      assignStaff,
      patientName;
  @FXML private TableView<InTransportServiceRequest> table;
  @FXML
  private HBox labelHBox1, labelHBox2, labelHBox3, inputHBox1, inputHBox2, inputHBox3, tableHBox;
  @FXML private VBox leftSideVBox, pageVBox;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private List<Location> locations;
  private EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private List<Employee> employees;
  private TableColumn<InTransportServiceRequest, String> idColumn = new TableColumn("Request ID");
  private TableColumn<InTransportServiceRequest, String> assigneeColumn =
      new TableColumn("Assignee");
  private TableColumn<InTransportServiceRequest, String> transportFromColumn =
      new TableColumn("Transport From");
  private TableColumn<InTransportServiceRequest, String> transportToColumn =
      new TableColumn("Transport To");
  private TableColumn<InTransportServiceRequest, String> statusColumn =
      new TableColumn("Request Status");
  private TableColumn<InTransportServiceRequest, String> patientNameColumn =
      new TableColumn("Patient Name");
  private TableColumn<InTransportServiceRequest, String> addAccommodationColumn =
      new TableColumn("Accommodations");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();
    resetFields();
    submitButton.setDisable(true);
    transportFrom.setItems(getLocationNames());
    transportTo.setItems(getLocationNames());
    addAccommodation
        .getItems()
        .addAll(
            new String[] {"Bed", "Recliner", "Intubator", "Extra Nurse", "Language Interpreter"});
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    assignStaff.setItems(this.getEmployeeIDs());
    //    assignStaff.getItems().addAll("Staff1", "Staff2", "Staff3");
    patientName.getItems().addAll("Patient1", "Patient2", "Patient3", "Patient4", "Patient5");
    transportFrom.setOnAction((ActionEvent event) -> enableSubmitButton());
    transportTo.setOnAction((ActionEvent event) -> enableSubmitButton());
    addAccommodation.setOnAction((ActionEvent event) -> enableSubmitButton());
    serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());
    patientName.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    table
        .getColumns()
        .addAll(
            idColumn,
            assigneeColumn,
            transportFromColumn,
            transportToColumn,
            statusColumn,
            patientNameColumn,
            addAccommodationColumn);

    idColumn.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    assigneeColumn.setCellValueFactory(new PropertyValueFactory<>("assignee"));
    transportFromColumn.setCellValueFactory(new PropertyValueFactory<>("locationShortName"));
    transportToColumn.setCellValueFactory(new PropertyValueFactory<>("transportToShortName"));
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
    addAccommodationColumn.setCellValueFactory(new PropertyValueFactory<>("addAccommodation"));

    labelHBox1.getChildren().addAll(l2, l3);
    labelHBox1.setSpacing(50);
    inputHBox1.getChildren().addAll(transportFrom, transportTo);
    inputHBox1.setSpacing(50);
    labelHBox2.getChildren().addAll(l1, l5);
    labelHBox2.setSpacing(50);
    inputHBox2.getChildren().addAll(patientName, assignStaff);
    inputHBox2.setSpacing(50);
    labelHBox3.getChildren().addAll(l4, l6);
    labelHBox3.setSpacing(50);
    inputHBox3.getChildren().addAll(addAccommodation, serviceStatus);
    inputHBox3.setSpacing(50);
    leftSideVBox
        .getChildren()
        .addAll(
            labelHBox1,
            inputHBox1,
            labelHBox2,
            inputHBox2,
            labelHBox3,
            inputHBox3,
            submitButton,
            resetFields);
    leftSideVBox.setSpacing(10);
    leftSideVBox.setMargin(submitButton, new Insets(30, 0, 0, 0));
    tableHBox.getChildren().addAll(leftSideVBox, table);
    tableHBox.setMargin(table, new Insets(0, 0, 0, 50));
    pageVBox.getChildren().addAll(title, tableHBox);
    pageVBox.setMargin(title, new Insets(0, 0, 80, 0));
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
        transportTo.getValue().equals("")
            || transportFrom.getValue().equals("")
            || addAccommodation.getValue().equals("")
            || serviceStatus.getValue().equals("")
            || patientName.getValue().equals("")
            || assignStaff.getValue().equals(""));
  }

  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException
   */
  @FXML
  public void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  /** Resets all fields on the page. */
  @FXML
  public void resetFields() {
    assignStaff.setValue("");
    transportFrom.setValue("");
    transportTo.setValue("");
    serviceStatus.setValue("");
    patientName.setValue("");
    addAccommodation.setValue("");
  }

  /** Creates a service request from the fields on the javafx page */
  @FXML
  public void submitRequest() {
    InTransportServiceRequest request = new InTransportServiceRequest();
    request.setRequestID(request.makeRequestID());
    request.setDestination(locations.get(transportFrom.getSelectionModel().getSelectedIndex()));
    request.setTransportTo(locations.get(transportTo.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setPatientName(patientName.getValue());
    request.setAddAccommodation(addAccommodation.getValue());
    request.setAssignee(emplDAO.getRecord(assignStaff.getValue()));
    this.resetFields();
    table.getItems().add(request);
  }
}
