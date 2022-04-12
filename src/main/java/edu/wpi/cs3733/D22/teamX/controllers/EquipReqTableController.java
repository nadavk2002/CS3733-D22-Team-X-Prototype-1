package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipReqTableController implements Initializable {
  @FXML private TableView<MedicalEquipmentServiceRequest> table;
  // @FXML private TableColumn<MedicalEquipmentServiceRequest, String> requester;
  @FXML
  private TableColumn<MedicalEquipmentServiceRequest, String> equipType; // spike c, equipmentType

  @FXML private TableColumn<MedicalEquipmentServiceRequest, Integer> amount; // spike c, Quantity

  @FXML
  private TableColumn<MedicalEquipmentServiceRequest, String> requestStatus; // spike c, Status
  // @FXML private TableColumn<MedicalEquipmentServiceRequest, String> prevLocation;
  @FXML
  private TableColumn<MedicalEquipmentServiceRequest, Location> destination; // spike c destination
  // @FXML private TableColumn<MedicalEquipmentServiceRequest, String> requestTime;
  // @FXML private TableColumn<MedicalEquipmentServiceRequest, String> fulfillRequest;
  @FXML private TableColumn<MedicalEquipmentServiceRequest, String> requestID; // spike c RequestID
  // @FXML private Button requestComplete;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    equipType.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
    amount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    requestStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    destination.setCellValueFactory(new PropertyValueFactory<>("locationNodeID"));
    requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    table.setItems(equipDeliveryList());
  }

  private ObservableList<MedicalEquipmentServiceRequest> equipDeliveryList() {
    ObservableList<MedicalEquipmentServiceRequest> equipList = FXCollections.observableArrayList();
    MedicalEquipmentServiceRequestDAO allEquip = MedicalEquipmentServiceRequestDAO.getDAO();
    List<MedicalEquipmentServiceRequest> inpEquipList = allEquip.getAllRecords();
    equipList.addAll(inpEquipList);
    return equipList;
  }

  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml"))));
  }
}
