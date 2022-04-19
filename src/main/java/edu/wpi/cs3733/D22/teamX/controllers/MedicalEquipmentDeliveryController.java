package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MedicalEquipmentDeliveryController {
  @FXML private Label quanityGreaterThan;
  @FXML private Label amountAvailable;
  @FXML private Button ToMainMenu;
  @FXML private ChoiceBox<String> selectEquipmentType, selectDestination, selectStatus, selectAssignee;
  @FXML private TextField amountField;
  @FXML private Button submitButton;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private EmployeeDAO employeeDAO = EmployeeDAO.getDAO();
  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  //  private MedicalEquipmentServiceRequestDAO equipmentDAO =
  //      MedicalEquipmentServiceRequestDAO.getDAO();
  private EquipmentTypeDAO eqtDAO = EquipmentTypeDAO.getDAO();
  private List<Location> locations;
  private List<Employee> employees;

  @FXML
  public void initialize() {
    locations = locationDAO.getAllRecords();
    employees = employeeDAO.getAllRecords();
    submitButton.setDisable(true);
    selectStatus.getItems().addAll("", "PROC", "DONE");
    EquipmentTypeDAO eqtDAO = EquipmentTypeDAO.getDAO();
    for (int i = 0; i < eqtDAO.getAllRecords().size(); i++) {
      selectEquipmentType.getItems().add(eqtDAO.getAllRecords().get(i).getModel());
    }
    selectDestination.setItems(this.getLocationNames());
    selectAssignee.setItems(this.getEmployeeIDs());
    updateAvailability();
    quanityGreaterThan.setVisible(false);
  }

  private ObservableList<String> equipDeliveryList() {
    ObservableList<String> nodeID = FXCollections.observableArrayList();
    for (int i = 0; i < locations.size(); i++) {
      nodeID.add(locations.get(i).getNodeID());
    }
    return nodeID;
  }

  private ObservableList<String> getEmployeeIDs(){
    ObservableList<String> employeeID = FXCollections.observableArrayList();
    for (int i = 0; i < employees.size(); i++) {
      employeeID.add(employees.get(i).getEmployeeID());
    }
    return employeeID;
  }
  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException if the main menu is not switched to
   */
  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  /** When Reset Fields button is pressed, the fields on the screen are reset to default. */
  @FXML
  public void resetFields() {
    amountField.setText("");
    selectEquipmentType.setValue("");
    selectDestination.setValue("");
    selectStatus.setValue("");
  }

  @FXML
  public void checkForInt() {
    submitButton.setDisable(
        !amountField.getText().matches("[0-9]+") || amountField.getText().length() == 0);
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

  @FXML
  public void submitRequest() {
    MedicalEquipmentServiceRequest request = new MedicalEquipmentServiceRequest();
    request.setEquipmentType(selectEquipmentType.getValue());
    request.setRequestID(requestDAO.makeMedicalEquipmentServiceRequestID());
    request.setDestination(locations.get(selectDestination.getSelectionModel().getSelectedIndex()));
    request.setStatus(selectStatus.getValue());
    request.setQuantity(Integer.parseInt(amountField.getText()));
    request.setAssignee(employees.get(selectAssignee.getSelectionModel().getSelectedIndex()));
    if (request.getQuantity()
        > eqtDAO.getRecord(request.getEquipmentType()).getNumUnitsAvailable()) {
      quanityGreaterThan.setVisible(true);
      return;
    }
    requestDAO.addRecord(request);
    this.resetFields();
    updateAvailability();
    quanityGreaterThan.setVisible(false);
  }

  public void updateAvailability() {
    EquipmentTypeDAO eqtDAO = EquipmentTypeDAO.getDAO();
    StringBuilder availableStr = new StringBuilder("Equipment Available:");
    String leftPadding = "                    ";
    for (int i = 0; i < eqtDAO.getAllRecords().size(); i++) {
      String model = eqtDAO.getAllRecords().get(i).getModel();
      availableStr.append(
          "\n"
              + model
              + leftPadding.substring(model.length())
              + eqtDAO.getAllRecords().get(i).getNumUnitsAvailable()
              + " units");
    }
    amountAvailable.setText(availableStr.toString());
  }
}
