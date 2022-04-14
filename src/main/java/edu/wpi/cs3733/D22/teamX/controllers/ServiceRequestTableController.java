package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ServiceRequestTableController implements Initializable {
  @FXML private TableView<ServiceRequest> table;
  @FXML private TableColumn<ServiceRequest, String> assignee; // spike c, Quantity
  @FXML private TableColumn<ServiceRequest, String> requestStatus; // spike c, Status
  @FXML private TableColumn<ServiceRequest, String> destination; // spike c destination
  @FXML private TableColumn<ServiceRequest, String> requestID; // spike c RequestID
  @FXML private TableColumn<ServiceRequest, String> serviceType;
  @FXML private TableColumn<ServiceRequest, ChoiceBox<String>> modStatus;
  @FXML private TextField modifyID;
  @FXML private ChoiceBox<String> modifyStatus;

  @FXML
  private void submitStatusUpdate() throws IOException {
    String id = modifyID.getText();
    try {
      GiftDeliveryRequestDAO giftDAO = GiftDeliveryRequestDAO.getDAO();
      GiftDeliveryRequest req = giftDAO.getRecord(id);
      req.setStatus(modifyStatus.getValue());
      giftDAO.updateRecord(req);
    } catch (Exception e) {
    }
    try {
      JanitorServiceRequestDAO janDAO = JanitorServiceRequestDAO.getDAO();
      JanitorServiceRequest req = janDAO.getRecord(id);
      req.setStatus(modifyStatus.getValue());
      janDAO.updateRecord(req);
    } catch (Exception e) {
    }
    try {
      LabServiceRequestDAO labDAO = LabServiceRequestDAO.getDAO();
      LabServiceRequest req = labDAO.getRecord(id);
      req.setStatus(modifyStatus.getValue());
      labDAO.updateRecord(req);
    } catch (Exception e) {
    }
    try {
      LangServiceRequestDAO langDAO = LangServiceRequestDAO.getDAO();
      LangServiceRequest req = langDAO.getRecord(id);
      req.setStatus(modifyStatus.getValue());
      langDAO.updateRecord(req);
    } catch (Exception e) {
    }
    try {
      MealServiceRequestDAO mealDAO = MealServiceRequestDAO.getDAO();
      MealServiceRequest req = mealDAO.getRecord(id);
      req.setStatus(modifyStatus.getValue());
      mealDAO.updateRecord(req);
    } catch (Exception e) {
    }
    try {
      MedicalEquipmentServiceRequestDAO medDAO = MedicalEquipmentServiceRequestDAO.getDAO();
      MedicalEquipmentServiceRequest req = medDAO.getRecord(id);
      req.setStatus(modifyStatus.getValue());
      medDAO.updateRecord(req);
    } catch (Exception e) {
    }
    try {
      MedicineDeliveryServiceRequestDAO medDAO = MedicineDeliveryServiceRequestDAO.getDAO();
      MedicineDeliveryServiceRequest req = medDAO.getRecord(id);
      req.setStatus(modifyStatus.getValue());
      medDAO.updateRecord(req);
    } catch (Exception e) {
    }
    try {
      LaundryServiceRequestDAO laundryDAO = LaundryServiceRequestDAO.getDAO();
      LaundryServiceRequest req = laundryDAO.getRecord(id);
      req.setStatus(modifyStatus.getValue());
      laundryDAO.updateRecord(req);
    } catch (Exception e) {
    }
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    requestStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    modifyStatus.getItems().addAll("DONE", "PROC", "");

    destination.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<ServiceRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<ServiceRequest, String> param) {
            return new SimpleStringProperty(param.getValue().getDestination().getLongName());
          }
        });
    serviceType.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<ServiceRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<ServiceRequest, String> param) {
            return new SimpleStringProperty(param.getValue().getClass().getSimpleName());
          }
        });

    requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    assignee.setCellValueFactory(
        new Callback<
            TableColumn.CellDataFeatures<ServiceRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TableColumn.CellDataFeatures<ServiceRequest, String> param) {
            return new SimpleStringProperty(param.getValue().getAssigneeID());
          }
        });
    table.setItems(FXCollections.observableList(listOfRequests()));
  }

  private List<ServiceRequest> listOfRequests() {
    List<? super ServiceRequest> requests = new ArrayList<>();
    requests.addAll(MedicalEquipmentServiceRequestDAO.getDAO().getAllRecords());
    requests.addAll(LabServiceRequestDAO.getDAO().getAllRecords());
    requests.addAll(GiftDeliveryRequestDAO.getDAO().getAllRecords());
    requests.addAll(LangServiceRequestDAO.getDAO().getAllRecords());
    requests.addAll(MealServiceRequestDAO.getDAO().getAllRecords());
    requests.addAll(JanitorServiceRequestDAO.getDAO().getAllRecords());
    requests.addAll(LaundryServiceRequestDAO.getDAO().getAllRecords());
    requests.addAll(MedicineDeliveryServiceRequestDAO.getDAO().getAllRecords());
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
