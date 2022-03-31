package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.LocationDAO;
import edu.wpi.cs3733.D22.teamX.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequestDAO;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequestDAOImpl;
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
  private EquipmentServiceRequest request;

  @FXML
  public void initialize() {
    submitButton.setDisable(true);
    selectStatus.getItems().addAll("", "PROC", "DONE");
    request = new EquipmentServiceRequest();
    selectEquipmentType.getItems().addAll("Bed", "X-Ray", "Pump", "Recliner");
    selectDestination.setItems(equipDeliveryList());
  }

  private ObservableList<String> equipDeliveryList() {
    EquipmentServiceRequestDAO allEquip = new EquipmentServiceRequestDAOImpl();
    List<EquipmentServiceRequest> inpEquipList = allEquip.getAllEquipmentServiceRequests();
    ObservableList<String> nodeID = FXCollections.observableArrayList();
    for (int i = 0; i < inpEquipList.size(); i++) {
      nodeID.add(inpEquipList.get(i).getLocationNodeID());
    }
    return nodeID;
  }
  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException
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

  @FXML
  public void submitRequest() {
    LocationDAO setLocation = new LocationDAOImpl();
    request.setEquipmentType(selectEquipmentType.getValue());
    request.setRequestID(request.makeRequestID());
    request.setDestination(setLocation.getLocation(selectDestination.getValue()));
    request.setStatus(selectStatus.getValue());
    request.setQuantity(Integer.parseInt(amountField.getText()));
    EquipmentServiceRequestDAO submit = new EquipmentServiceRequestDAOImpl();
    submit.addEquipmentServiceRequest(request);
    this.resetFields();
  }
}
