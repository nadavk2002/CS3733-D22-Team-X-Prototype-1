package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MealReqController implements Initializable {
  @FXML private VBox fullSelBox, seventhRowButtons;
  @FXML private Button submitButton;
  @FXML
  private HBox topRowNames,
      secondRowBoxes,
      thirdRowNames,
      fourthRowBoxes,
      fifthRowNames,
      sixthRowBoxes;
  @FXML
  private JFXComboBox<String> patientNames,
      drinkSel,
      mainSel,
      sideSel,
      assignStaff,
      serviceStatus,
      destinationDrop;

  private final LocationDAO locationDAO = LocationDAO.getDAO();
  private final ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  //  private MealServiceRequestDAO MealDAO = MealServiceRequestDAO.getDAO();
  private List<Location> locations;
  //  private final LocationDAO locationDAO = LocationDAO.getDAO();
  //  private final MealServiceRequestDAO mealDAO = MealServiceRequestDAO.getDAO();
  private final EmployeeDAO emplDAO = EmployeeDAO.getDAO();

  private final ObservableList<MealServiceRequest> mealList = FXCollections.observableArrayList();
  private final ObservableList<String> employeeIDs = FXCollections.observableArrayList();
  private final ObservableList<String> locationNames = FXCollections.observableArrayList();
  private final ObservableList<String> patientNamesList = FXCollections.observableArrayList();

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    resetFields();
    submitButton.setDisable(true);
    // Formatting---------------------------------------------------
    //    fullSelBox.setSpacing(20);
    //    seventhRowButtons.setSpacing(20);
    //    topRowNames.setSpacing(80);
    //    secondRowBoxes.setSpacing(20);
    //    thirdRowNames.setSpacing(80);
    //    fourthRowBoxes.setSpacing(20);
    //    fifthRowNames.setSpacing(80);
    //    sixthRowBoxes.setSpacing(20);
    //    seventhRowButtons.setSpacing(20);

    // status choice box ----------------------------------------------------
    serviceStatus.getItems().addAll(" ", "DONE", "PROC");
    // patient names choice box---------------------------------------
    // patientNames.getItems().addAll("Patient 1", "Patient 2", "Patient 3", "Patient 4", "Patient
    // 5");
    patientNames.setItems(getPatients());

    List<MealServiceRequest> meals = getMealRequests();

    // drinks choice box---------------------------------------
    // drinkSel.getItems().addAll("Water", "Orange Juice", "Apple Juice", "Gatorade");
    List<String> mealDrinks = new ArrayList<String>();
    for (MealServiceRequest meal : meals) {
      mealDrinks.add(meal.getDrink());
    }
    drinkSel.getItems().addAll(mealDrinks.stream().distinct().collect(Collectors.toList()));

    // sides choice box---------------------------------------
    // sideSel.getItems().addAll("Fruit Cup", "Mashed Potatoes", "Mixed Vegetables");
    List<String> mealSides = new ArrayList<String>();
    for (MealServiceRequest meal : meals) {
      mealSides.add(meal.getSide());
    }
    sideSel.getItems().addAll(mealSides.stream().distinct().collect(Collectors.toList()));

    // main course choice box---------------------------------------
    // mainSel.getItems().addAll("Chicken", "Fish", "Vegeterian");
    List<String> mealMains = new ArrayList<String>();
    for (MealServiceRequest meal : meals) {
      mealMains.add(meal.getMainCourse());
    }
    mainSel.getItems().addAll(mealMains.stream().distinct().collect(Collectors.toList()));

    // assign staff choice box ---------------------------------------
    assignStaff.setItems(getEmployeeIDs());
    //    assignStaff
    //        .getItems()
    //        .addAll("Doctor 1", "Doctor 2", "Doctor 3", "Nurse 1", "Nurse 2", "Nurse 3");
    // status choice box----------------------------------------------------
    destinationDrop.setItems(getLocationNames());
    patientNames.setOnAction((ActionEvent event) -> enableSubmitButton());
    drinkSel.setOnAction((ActionEvent event) -> enableSubmitButton());
    sideSel.setOnAction((ActionEvent event) -> enableSubmitButton());
    mainSel.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
    destinationDrop.setOnAction((ActionEvent event) -> enableSubmitButton());
    serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());
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
    List<Location> locations = locationDAO.getAllRecords();
    List<String> locationShortNames = new ArrayList<String>();
    for (Location location : locations) {
      locationShortNames.add(location.getShortName());
    }
    locationNames.addAll(locationShortNames.stream().distinct().collect(Collectors.toList()));
    return locationNames;
  }

  public ObservableList<String> getEmployeeIDs() {
    List<Employee> employees = emplDAO.getAllRecords();
    for (Employee employee : employees) {
      employeeIDs.add(employee.getFirstName() + " " + employee.getLastName());
    }
    return employeeIDs;
  }

  @FXML
  void returnHomeButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  void resetFields() {
    patientNames.setValue("");
    drinkSel.setValue("");
    mainSel.setValue("");
    sideSel.setValue("");
    // addedToQueue.setText("");
    assignStaff.setValue("");
    serviceStatus.setValue("");
    destinationDrop.setValue("");
  }

  private ObservableList<MealServiceRequest> getMealRequests() {
    mealList.addAll(requestDAO.getAllMealServiceRequests());
    return mealList;
  }

  private ObservableList<String> getPatients() {
    List<Patient> patients = PatientDAO.getDAO().getAllRecords();
    for (Patient patient : patients) {
      patientNamesList.add(patient.getFirstName() + " " + patient.getLastName());
    }
    return patientNamesList;
  }

  @FXML
  void submitButton() {
    MealServiceRequest request = new MealServiceRequest();
    request.setRequestID(requestDAO.makeMealServiceRequestID());
    request.setDestination(
        locationDAO.getAllRecords().get(destinationDrop.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(
        emplDAO.getAllRecords().get(assignStaff.getSelectionModel().getSelectedIndex()));
    request.setMainCourse(mainSel.getValue());
    request.setDrink(drinkSel.getValue());
    request.setSide(sideSel.getValue());
    request.setPatientFor(
        PatientDAO.getDAO()
            .getAllRecords()
            .get(patientNames.getSelectionModel().getSelectedIndex())
            .getPatientID());

    requestDAO.addRecord(request);
    this.resetFields();
  }
}
