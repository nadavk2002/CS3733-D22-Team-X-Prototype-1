package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequestDAO;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequestDAOImpl;
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
  @FXML private TableView<EquipmentServiceRequest> table;
  // @FXML private TableColumn<EquipmentServiceRequest, String> requester;
  @FXML private TableColumn<EquipmentServiceRequest, String> equipType; // spike c, equipmentType
  @FXML private TableColumn<EquipmentServiceRequest, Integer> amount; // spike c, Quantity
  @FXML private TableColumn<EquipmentServiceRequest, String> equipStatus; // spike c, Status
  // @FXML private TableColumn<EquipmentServiceRequest, String> prevLocation;
  @FXML private TableColumn<EquipmentServiceRequest, Location> destination; // spike c destination
  // @FXML private TableColumn<EquipmentServiceRequest, String> requestTime;
  // @FXML private TableColumn<EquipmentServiceRequest, String> fulfillRequest;
  @FXML private TableColumn<EquipmentServiceRequest, String> requestID; // spike c RequestID
  // @FXML private Button requestComplete;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    equipType.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
    amount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    equipStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    destination.setCellValueFactory(new PropertyValueFactory<>("locationNodeID"));
    requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    table.setItems(equipDeliveryList());
  }

  private ObservableList<EquipmentServiceRequest> equipDeliveryList() {
    ObservableList<EquipmentServiceRequest> equipList = FXCollections.observableArrayList();
    EquipmentServiceRequestDAO allEquip = new EquipmentServiceRequestDAOImpl();
    List<EquipmentServiceRequest> inpEquipList = allEquip.getAllEquipmentServiceRequests();
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
