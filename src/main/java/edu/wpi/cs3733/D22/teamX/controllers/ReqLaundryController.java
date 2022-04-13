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

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private LaundryServiceRequestDAO laundryDAO = LaundryServiceRequestDAO.getDAO();
  private List<Location> locations;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    resetFields();
    selectionMenuColumn.setSpacing(20);
    labelColumn.setSpacing(28);
    submitColumn.setSpacing(20);
    buttonRow.setSpacing(20);
    roomNum.setItems(getLocationNames());
    submitButton.setDisable(true);

    selectLaundryType
        .getItems()
        .addAll(new String[] {"Linens", "Gowns", "Bedding", "Scrubs", "Coats"});
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    assignStaff.getItems().addAll("Staff1", "Staff2", "Staff3");

    selectLaundryType.setOnAction((ActionEvent event) -> disableSubmitButton());
    roomNum.setOnAction((ActionEvent event) -> disableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> disableSubmitButton());
    serviceStatus.setOnAction((ActionEvent event) -> disableSubmitButton());
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

    request.setRequestID(laundryDAO.makeID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(assignStaff.getValue());
    request.setService(selectLaundryType.getValue());
    laundryDAO.addRecord(request);
    this.resetFields();
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
