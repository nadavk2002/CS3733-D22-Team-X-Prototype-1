package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequestDAO;
import edu.wpi.cs3733.D22.teamX.entity.MedicalEquipmentServiceRequestDAOImpl;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class MedicalEquipmentDeliveryController {
  @FXML private Button ToMainMenu;
  @FXML private ChoiceBox<String> selectEquipmentType, selectDestination, selectStatus;
  @FXML private TextField amountField;
  @FXML private Button submitButton;

  private LocationDAO locationDAO;
  private List<Location> locations;

  @FXML
  public void initialize() {
    locationDAO = new LocationDAOImpl();
    locations = locationDAO.getAllLocations();
    submitButton.setDisable(true);
    selectStatus.getItems().addAll("", "PROC", "DONE");
    selectEquipmentType.getItems().addAll("Bed", "X-Ray", "Infusion Pump", "Recliner");
    selectDestination.setItems(this.getLocationNames());
  }

  private ObservableList<String> equipDeliveryList() {
    ObservableList<String> nodeID = FXCollections.observableArrayList();
    for (int i = 0; i < locations.size(); i++) {
      nodeID.add(locations.get(i).getNodeID());
    }
    return nodeID;
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
    request.setRequestID(request.makeRequestID());
    request.setDestination(locations.get(selectDestination.getSelectionModel().getSelectedIndex()));
    request.setStatus(selectStatus.getValue());
    request.setQuantity(Integer.parseInt(amountField.getText()));

    MedicalEquipmentServiceRequestDAO submit = new MedicalEquipmentServiceRequestDAOImpl();
    submit.addMedicalEquipmentServiceRequest(request);
    this.resetFields();
  }
}
