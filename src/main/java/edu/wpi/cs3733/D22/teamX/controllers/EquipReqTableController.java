package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentServiceRequest;
import java.io.IOException;
import java.net.URL;
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
    equipType.setCellValueFactory(
        new PropertyValueFactory<EquipmentServiceRequest, String>("equipmentType"));
    amount.setCellValueFactory(
        new PropertyValueFactory<EquipmentServiceRequest, Integer>("quantity"));
    equipStatus.setCellValueFactory(
        new PropertyValueFactory<EquipmentServiceRequest, String>("status"));
    destination.setCellValueFactory(
        new PropertyValueFactory<EquipmentServiceRequest, Location>("locationNodeID"));
    requestID.setCellValueFactory(
        new PropertyValueFactory<EquipmentServiceRequest, String>("requestID"));
    table.setItems(list);
  }

  ObservableList<EquipmentServiceRequest> list =
      FXCollections.observableArrayList(
          new EquipmentServiceRequest(
              "EDR2034",
              new Location("xSTOR001L1", 32, 64, "L1", "Tower", "STOR", "Storage", "Store"),
              "Functional",
              "Bed",
              3));

  //  private ObservableList<EquipmentServiceRequest> equipDeliveryList(EquipmentServiceRequest ESR)
  // {
  //    ObservableList<EquipmentServiceRequest> list =
  //        FXCollections.observableArrayList(
  //            new EquipmentServiceRequest(
  //                "EDR2034",
  //                new Location("xSTOR001L1", 32, 64, "L1", "Tower", "STOR", "Storage", "Store"),
  //                "Functional",
  //                "Bed",
  //                3));
  //    return list;
  //  }

  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml"))));
  }
}
