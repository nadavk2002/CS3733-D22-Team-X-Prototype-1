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

public class ReqLangController implements Initializable {
  @FXML private Button mainMenu, submitButton;
  @FXML private ChoiceBox<String> selectLang, roomNum, serviceStatus, assignStaff;

  private LocationDAO locationDAO = LocationDAO.getDAO();
  private LangServiceRequestDAO langDAO = LangServiceRequestDAO.getDAO();
  private List<Location> locations;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    locations = locationDAO.getAllRecords();
    resetFields();
    submitButton.setDisable(true);
    selectLang.getItems().addAll(new String[] {"English", "Spanish", "French"});
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    assignStaff.getItems().addAll("Staff1", "Staff2", "Staff3");
    roomNum.setItems(getLocationNames());
    selectLang.setOnAction((ActionEvent event) -> enableSubmitButton());
    roomNum.setOnAction((ActionEvent event) -> enableSubmitButton());
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
            || selectLang.getValue().equals("")
            || assignStaff.getValue().equals(""));
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

  /** Resets all fields on the page. */
  @FXML
  public void resetFields() {
    selectLang.setValue("");
    roomNum.setValue("");
    serviceStatus.setValue("");
    assignStaff.setValue("");
  }

  /** Creates a service request from the fields on the javafx page */
  @FXML
  public void submitRequest() {
    LangServiceRequest request = new LangServiceRequest();

    request.setRequestID(request.makeRequestID());
    request.setDestination(locations.get(roomNum.getSelectionModel().getSelectedIndex()));
    request.setStatus(serviceStatus.getValue());
    request.setLanguage(selectLang.getValue());
    request.setAssignee(assignStaff.getValue());
    langDAO.addRecord(request);
    this.resetFields();
  }
}
