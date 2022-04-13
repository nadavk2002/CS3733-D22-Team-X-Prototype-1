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

public class JanitorialRequestController implements Initializable {
  @FXML private Button mainMenu, submitButton;
  @FXML private ChoiceBox<String> roomNum, serviceStatus, assignStaff, serviceType;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private JanitorServiceRequestDAO janitorDAO = JanitorServiceRequestDAO.getDAO();
  private List<Location> locations;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    resetFields();
    submitButton.setDisable(true);
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    serviceType.getItems().addAll("Bodily Fluids", "Chemical Spills", "Disinfection");
    assignStaff.getItems().addAll("Janitor 1", "Janitor 2", "Janitor 3", "Janitor 4");
    roomNum.setItems(getLocationNames());
    roomNum.setOnAction((ActionEvent event) -> enableSubmitButton());
    serviceType.setOnAction((ActionEvent event) -> enableSubmitButton());
    assignStaff.setOnAction((ActionEvent event) -> enableSubmitButton());
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

  /** Checks if the submit button can be enabled depending on the inputs in fields on the page. */
  public void enableSubmitButton() {
    submitButton.setDisable(
        roomNum.getValue().equals("")
            || serviceType.getValue().equals("")
            || serviceStatus.getValue().equals("")
            || assignStaff.getValue().equals(""));
  }

  /** Resets all fields on the page. */
  @FXML
  public void resetFields() {
    serviceType.setValue("");
    roomNum.setValue("");
    serviceStatus.setValue("");
    assignStaff.setValue("");
  }

  /** Creates a service request from the fields on the javafx page */
  @FXML
  public void submitButton() {
    JanitorServiceRequest request = new JanitorServiceRequest();

    request.setRequestID(janitorDAO.makeID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setAssignee(assignStaff.getValue());
    request.setDescription(serviceType.getValue());
    janitorDAO.addRecord(request);
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
