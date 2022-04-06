package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamX.entity.MealServiceRequest;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
      sixthRowBoxes,
      masterBox;
  @FXML
  private ChoiceBox<String> patientNames,
      drinkSel,
      mainSel,
      sideSel,
      assignStaff,
      serviceStatus,
      destinationDrop;
  @FXML
  private TableColumn<MealServiceRequest, String> reqID,
      patID,
      assignee,
      reqType,
      status,
      destination;
  @FXML private TableView<MealServiceRequest> table;
  private LocationDAO locationDAO;
  private List<Location> locations;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    locationDAO = new LocationDAOImpl();
    locations = locationDAO.getAllLocations();
    resetFields();
    submitButton.setDisable(true);
    // Formatting---------------------------------------------------
    fullSelBox.setSpacing(20);
    seventhRowButtons.setSpacing(20);
    topRowNames.setSpacing(80);
    secondRowBoxes.setSpacing(20);
    thirdRowNames.setSpacing(80);
    fourthRowBoxes.setSpacing(20);
    fifthRowNames.setSpacing(80);
    sixthRowBoxes.setSpacing(20);
    seventhRowButtons.setSpacing(20);
    masterBox.setSpacing(40);
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    reqID.setStyle("-fx-alignment: TOP-CENTER;");
    patID.setStyle("-fx-alignment: TOP-CENTER;");
    assignee.setStyle("-fx-alignment: TOP-CENTER;");
    reqType.setStyle("-fx-alignment: TOP-CENTER;");
    status.setStyle("-fx-alignment: TOP-CENTER;");
    destination.setStyle("-fx-alignment: TOP-CENTER;");
    // status choice box ----------------------------------------------------
    serviceStatus.getItems().addAll(" ", "DONE", "PROC");
    // patient names choice box---------------------------------------
    patientNames.getItems().addAll("Patient 1", "Patient 2", "Patient 3", "Patient 4", "Patient 5");
    // drinks choice box---------------------------------------
    drinkSel.getItems().addAll("Water", "Orange Juice", "Apple Juice", "Gatorade");
    // sides choice box---------------------------------------
    sideSel.getItems().addAll("Fruit Cup", "Mashed Potatoes", "Mixed Vegetables");
    // main course choice box---------------------------------------
    mainSel.getItems().addAll("Chicken", "Fish", "Vegeterian");
    // assign staff choice box ---------------------------------------
    assignStaff
        .getItems()
        .addAll("Doctor 1", "Doctor 2", "Doctor 3", "Nurse 1", "Nurse 2", "Nurse 3");
    // status choice box----------------------------------------------------
    destinationDrop.setItems(getLocationNames());
    patientNames.setOnAction((ActionEvent event) -> enableSubmitButton());
    drinkSel.setOnAction((ActionEvent event) -> enableSubmitButton());
    sideSel.setOnAction((ActionEvent event) -> enableSubmitButton());
    mainSel.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
    destinationDrop.setOnAction((ActionEvent event) -> enableSubmitButton());
    serviceStatus.setOnAction((ActionEvent event) -> enableSubmitButton());

    reqID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    patID.setCellValueFactory(new PropertyValueFactory<>("patientID"));
    assignee.setCellValueFactory(new PropertyValueFactory<>("status"));
    reqType.setCellValueFactory(new PropertyValueFactory<>("mealType"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    destination.setCellValueFactory(new PropertyValueFactory<>("locationShortName"));
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

  @FXML
  void submitButton() {
    MealServiceRequest request = new MealServiceRequest();
    request.setRequestID(request.makeRequestID());
    request.setDestination(locations.get(destinationDrop.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(assignStaff.getValue());
    request.setMealType(
        mainSel.getValue() + "\n" + sideSel.getValue() + "\n" + drinkSel.getValue());
    request.setPatientID(patientNames.getValue());
    this.resetFields();
    //
    table.getItems().add(request);
  }
}
