package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class UpdateMealRequestController implements Initializable {
  @FXML private Button submitButton;
  @FXML
  private ChoiceBox<String> patientNames,
      drinkSel,
      mainSel,
      sideSel,
      assignStaff,
      serviceStatus,
      destinationDrop;

  private final LocationDAO locationDAO = LocationDAO.getDAO();
  // private final ServiceRequestDAO mealDAO = ServiceRequestDAO.getDAO();
  private List<Location> locations;
  private List<Employee> employees;
  private final EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private final ObservableList<MealServiceRequest> mealList = FXCollections.observableArrayList();
  private final ObservableList<String> employeeIDs = FXCollections.observableArrayList();
  private final ObservableList<String> locationNames = FXCollections.observableArrayList();
  private final ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  private MealServiceRequest request;

  public UpdateMealRequestController(MealServiceRequest request) {
    this.request = request;
  }

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    employees = emplDAO.getAllRecords();
    resetFields();
    submitButton.setDisable(false);
    // status choice box ----------------------------------------------------
    serviceStatus.getItems().addAll(" ", "DONE", "PROC");
    // patient names choice box---------------------------------------
    List<MealServiceRequest> meals = getMealRequests();
    for (MealServiceRequest meal : meals) {
      patientNames.getItems().add(meal.getPatientFor());
    }

    // drinks choice box---------------------------------------
    List<String> mealDrinks = new ArrayList<String>();
    for (MealServiceRequest meal : meals) {
      mealDrinks.add(meal.getDrink());
    }
    drinkSel.getItems().addAll(mealDrinks.stream().distinct().collect(Collectors.toList()));

    // sides choice box---------------------------------------
    List<String> mealSides = new ArrayList<String>();
    for (MealServiceRequest meal : meals) {
      mealSides.add(meal.getSide());
    }
    sideSel.getItems().addAll(mealSides.stream().distinct().collect(Collectors.toList()));

    // main course choice box---------------------------------------
    List<String> mealMains = new ArrayList<String>();
    for (MealServiceRequest meal : meals) {
      mealMains.add(meal.getMainCourse());
    }
    mainSel.getItems().addAll(mealMains.stream().distinct().collect(Collectors.toList()));

    // assign staff choice box ---------------------------------------
    assignStaff.setItems(getEmployeeIDs());
    // status choice box----------------------------------------------------
    destinationDrop.setItems(getLocationNames());
    patientNames.setOnAction((ActionEvent event) -> enableSubmitButton());
    drinkSel.setOnAction((ActionEvent event) -> enableSubmitButton());
    sideSel.setOnAction((ActionEvent event) -> enableSubmitButton());
    mainSel.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
    destinationDrop.setOnAction((ActionEvent event) -> enableSubmitButton());
    serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());

    patientNames.setValue(this.request.getPatientFor());
    drinkSel.setValue(this.request.getDrink());
    assignStaff.setValue(this.request.getAssigneeID());
    sideSel.setValue(this.request.getSide());
    mainSel.setValue(this.request.getMainCourse());
    destinationDrop.setItems(this.getLocationNames());
    destinationDrop.setValue(request.getLocationShortName());
    serviceStatus.setValue(this.request.getStatus());
  }

  public void enableSubmitButton() {
    submitButton.setDisable(
        patientNames.getValue().equals("")
            || drinkSel.getValue().equals("")
            || mainSel.getValue().equals("")
            || sideSel.getValue().equals("")
            || assignStaff.getValue().equals("")
            || serviceStatus.getValue().equals("")
            || destinationDrop.getValue().equals(""));
  }

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

  @FXML
  void resetFields() {
    patientNames.setValue("");
    drinkSel.setValue("");
    mainSel.setValue("");
    sideSel.setValue("");
    assignStaff.setValue("");
    serviceStatus.setValue("");
    destinationDrop.setValue("");
  }

  private ObservableList<MealServiceRequest> getMealRequests() {
    mealList.addAll(requestDAO.getAllMealServiceRequests());
    return mealList;
  }

  @FXML
  void submitRequest() throws IOException {
    MealServiceRequest request = new MealServiceRequest();
    request.setRequestID(this.request.getRequestID());
    request.setDestination(locations.get(destinationDrop.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(emplDAO.getRecord(assignStaff.getValue()));
    request.setMainCourse(mainSel.getValue());
    request.setDrink(drinkSel.getValue());
    request.setSide(sideSel.getValue());
    request.setPatientFor(patientNames.getValue());

    // requestDAO.addRecord(request);
    ServiceRequestDAO.getDAO().updateRecord(request);
    Stage stage = (Stage) submitButton.getScene().getWindow();
    stage.close();
    this.resetFields();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  }
}
