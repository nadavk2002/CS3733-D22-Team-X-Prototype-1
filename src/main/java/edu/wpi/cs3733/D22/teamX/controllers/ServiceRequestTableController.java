package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
  @FXML private JFXCheckBox filterTasks;
  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();

  public String previousID;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    requestStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

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

    filterTasks.setOnAction(
        event -> {
          table.getItems().clear();
          if (filterTasks.isSelected())
            table.setItems(FXCollections.observableList(getNotCompletedRequests()));
          else table.setItems(FXCollections.observableList(listOfRequests()));
        });
  }

  public String getID() {
    if (modifyID.getText() != "") {
      previousID = modifyID.getText();
    }
    return previousID;
  }

  @FXML
  private void goToUpdatePage() throws IOException {
    String id = modifyID.getText();
    try {
      GiftDeliveryRequest req = requestDAO.getGiftDeliveryRequest(id);
      UpdateGiftRequestController controller = new UpdateGiftRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateGiftRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Gift Delivery Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      JanitorServiceRequest req = requestDAO.getJanitorServiceRequest(id);
      UpdateJanitorRequestController controller = new UpdateJanitorRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateJanitorRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Janitor Service Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      LabServiceRequest req = requestDAO.getLabServiceRequest(id);
      UpdateLabRequestController controller = new UpdateLabRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateLabRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Lab Service Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      LangServiceRequest req = requestDAO.getLangServiceRequest(id);
      UpdateLangRequestController controller = new UpdateLangRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateLangRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Language Service Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      MealServiceRequest req = requestDAO.getMealServiceRequest(id);
      UpdateMealRequestController controller = new UpdateMealRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateMealRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Meal Service Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      MedicalEquipmentServiceRequest req = requestDAO.getMedicalEquipmentServiceRequest(id);
      UpdateEquipmentRequestController controller = new UpdateEquipmentRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass()
                  .getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateEquipmentRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Medical Equipment Delivery Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      MedicineServiceRequest req = requestDAO.getMedicineServiceRequest(id);
      UpdateMedicineDeliveryRequestController controller =
          new UpdateMedicineDeliveryRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass()
                  .getResource(
                      "/edu/wpi/cs3733/D22/teamX/views/UpdateMedicineDeliveryRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Medicine Delivery Service Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      LaundyServiceRequest req = requestDAO.getLaundryServiceRequest(id);
      UpdateLaundryRequestController controller = new UpdateLaundryRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateLaundryRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Laundry Service Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      MaintenanceServiceRequest req = requestDAO.getMaintenanceServiceRequest(id);
      UpdateMaintenanceRequestController controller = new UpdateMaintenanceRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass()
                  .getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateMaintenanceRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Maintenance Service Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      SharpsDisposalRequest req = requestDAO.getSharpsDisposalServiceRequest(id);
      UpdateSharpsDisposalRequestController controller =
          new UpdateSharpsDisposalRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass()
                  .getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateSharpsDisposalRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Sharps Disposal Service Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    try {
      InTransportServiceRequest req = requestDAO.getInTransportServiceRequest(id);
      UpdateInTransportRequestController controller = new UpdateInTransportRequestController(req);
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass()
                  .getResource("/edu/wpi/cs3733/D22/teamX/views/UpdateInTransportRequest.fxml"));
      fxmlLoader.setController(controller);
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("Edit Internal Patient Transport Service Request");
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
    }
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  }

  private List<ServiceRequest> listOfRequests() {
    //    List<? super ServiceRequest> requests = new ArrayList<>();
    //    requests.addAll(MedicalEquipmentServiceRequestDAO.getDAO().getAllRecords());
    //    requests.addAll(LabServiceRequestDAO.getDAO().getAllRecords());
    //    requests.addAll(GiftDeliveryRequestDAO.getDAO().getAllRecords());
    //    requests.addAll(LangServiceRequestDAO.getDAO().getAllRecords());
    //    requests.addAll(MealServiceRequestDAO.getDAO().getAllRecords());
    //    requests.addAll(JanitorServiceRequestDAO.getDAO().getAllRecords());
    //    requests.addAll(LaundryServiceRequestDAO.getDAO().getAllRecords());
    //    requests.addAll(MedicineDeliverServiceRequestDAO.getDAO().getAllRecords());
    return requestDAO.getServiceRequests();
  }

  private List<ServiceRequest> getNotCompletedRequests() {
    List<ServiceRequest> requests = ServiceRequestDAO.getDAO().getServiceRequests();
    for (int i = 0; i < requests.size(); i++) {
      if (requests.get(i).getStatus().equals("DONE")) {
        requests.remove(i);
        i--;
      }
    }
    return requests;
  }

  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml"))));
  }
}
