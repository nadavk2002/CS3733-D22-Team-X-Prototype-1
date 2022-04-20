package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import edu.wpi.cs3733.D22.teamX.entity.GiftDeliveryRequest;
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

public class UpdateGiftRequestController implements Initializable {
  @FXML private TextField giftNoteField;
  @FXML
  private ChoiceBox<String> selectGiftDestination, selectAssignStaff, selectStatus, selectGiftType;
  @FXML private Button submitButton;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  // private ServiceRequestDAO giftDAO = ServiceRequestDAO.getDAO();
  private List<Location> locations;
  private List<Employee> employees;
  private TableColumn<GiftDeliveryRequest, String> idColumn = new TableColumn("Request ID");
  private TableColumn<GiftDeliveryRequest, String> assigneeColumn = new TableColumn("Assignee");
  private TableColumn<GiftDeliveryRequest, String> locationColumn = new TableColumn("Location");
  private TableColumn<GiftDeliveryRequest, String> statusColumn = new TableColumn("Request Status");
  private TableColumn<GiftDeliveryRequest, String> giftTypeColumn = new TableColumn("gift Type");
  private TableColumn<GiftDeliveryRequest, String> giftNoteColumn = new TableColumn("Notes");

  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  private GiftDeliveryRequest request;

  public UpdateGiftRequestController(GiftDeliveryRequest request) {
    this.request = request;
  }

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();
    resetFields();
    submitButton.setDisable(false);
    selectStatus.getItems().addAll("", "PROC", "DONE");
    giftNoteField.setText("");
    selectAssignStaff.setItems(this.getEmployeeIDs());
    //    selectAssignStaff.getItems().addAll("Staff1", "Staff2", "Staff3", "Staff4");
    selectGiftDestination.getItems().addAll("Room1", "Room2", "Room3");
    selectGiftType.getItems().addAll("Toy", "Flower", "Chocolate");
    selectGiftDestination.setItems(getLocationNames());

    selectGiftType.setOnAction((ActionEvent event) -> enableSubmitButton());
    selectGiftDestination.setOnAction((ActionEvent event) -> enableSubmitButton());
    selectAssignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
    // giftNoteField.setOnAction((ActionEvent event) -> enableSubmitButton());
    //    selectStatus.setOnAction((ActionEvent event) -> enableSubmitButton());

    selectGiftType.setValue(this.request.getGiftType());
    selectGiftDestination.setItems(this.getLocationNames());
    selectAssignStaff.setValue(this.request.getAssigneeID());
    selectGiftDestination.setValue(request.getLocationShortName());
    giftNoteField.setText(this.request.getNotes());
    selectStatus.setValue(this.request.getStatus());
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
        selectGiftType.getValue().equals("")
            || selectGiftDestination.getValue().equals("")
            || selectAssignStaff.getValue().equals("")
            || giftNoteField.getText().equals(""));
  }

  @FXML
  public void resetFields() {
    selectGiftType.setValue("");
    giftNoteField.setText("");
    selectGiftDestination.setValue("");
    selectAssignStaff.setValue("");
    selectStatus.setValue("");
  }

  @FXML
  public void submitButton() throws IOException {
    GiftDeliveryRequest request = new GiftDeliveryRequest();
    request.setRequestID(this.request.getRequestID());
    request.setDestination(
        locations.get(selectGiftDestination.getSelectionModel().getSelectedIndex()));
    request.setAssignee(emplDAO.getRecord(selectAssignStaff.getValue()));
    request.setStatus(selectStatus.getValue());
    request.setGiftType(selectGiftType.getValue());
    request.setNotes(giftNoteField.getText());
    ServiceRequestDAO.getDAO().updateRecord(request);
    Stage stage = (Stage) submitButton.getScene().getWindow();
    stage.close();
    this.resetFields();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  }
}
