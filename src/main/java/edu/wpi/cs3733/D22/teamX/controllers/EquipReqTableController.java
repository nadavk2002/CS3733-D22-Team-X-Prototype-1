package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EquipReqTableController implements Initializable {
  @FXML private TableView<ServiceRequest> table;
  // @FXML private TableColumn<MedicalEquipmentServiceRequest, String> requester;
  @FXML private TableColumn<ServiceRequest, String> assignee; // spike c, Quantity
  @FXML private TableColumn<ServiceRequest, String> requestStatus; // spike c, Status
  @FXML private TableColumn<ServiceRequest, Location> destination; // spike c destination
  @FXML private TableColumn<ServiceRequest, String> requestID; // spike c RequestID

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    requestStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
    requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    assignee.setCellValueFactory(new PropertyValueFactory<>("assignee"));
    table.setItems(FXCollections.observableList(listOfRequests()));
  }

  private List<ServiceRequest> listOfRequests() {
    List<? super ServiceRequest> requests = new ArrayList<>();
    requests.addAll(MedicalEquipmentServiceRequestDAO.getDAO().getAllRecords());
    requests.addAll(LabServiceRequestDAO.getDAO().getAllRecords());
    requests.addAll(GiftDeliveryRequestDAO.getDAO().getAllRecords());
    return (List<ServiceRequest>) requests;
  }

  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml"))));
  }
}
