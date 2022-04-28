package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.Observer;
import edu.wpi.cs3733.D22.teamX.entity.*;
import edu.wpi.cs3733.D22.teamX.exceptions.Subject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardAlertsController extends Observer implements Initializable {
  @FXML
  private TableView<MedicalEquipmentServiceRequest> alertTable =
      new TableView<MedicalEquipmentServiceRequest>();

  @FXML private TableColumn<MedicalEquipmentServiceRequest, String> requestID;
  @FXML private TableColumn<MedicalEquipmentServiceRequest, String> type;
  @FXML private TableColumn<MedicalEquipmentServiceRequest, String> destination;
  @FXML private TableColumn<MedicalEquipmentServiceRequest, Integer> quantity;

  // @FXML private TableColumn<String, String> sendAlert;
  @FXML private JFXButton hideAlerts;
  private ServiceRequestDAO requestDAO = ServiceRequestDAO.getDAO();
  private LocationDAO locationDAO = LocationDAO.getDAO();
  private EquipmentUnitDAO equipmentUnitDAO = EquipmentUnitDAO.getDAO();
  private ObservableList<String> floors = FXCollections.observableArrayList();
  private MedicalEquipmentServiceRequestDAO MESRDAO = MedicalEquipmentServiceRequestDAO.getDAO();

  public DashboardAlertsController() {
    super(new Subject());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    alertTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    //    alertTable.setItems(MESRDAO.getAlerts());
    requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
    type.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
    destination.setCellValueFactory(new PropertyValueFactory<>("locationShortName"));
    quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    floors.addAll("5", "4", "3", "2", "1", "L2", "L1");
    //    update();
  }

  @Override
  public void update() {
    for (Location l : LocationDAO.getDAO().getAllRecords()) {
      sendBedRequest(sortByBeds(sortByDirty(getEquipmentAtLocation(l))));
    }
    for (String f : floors) {
      sendInfusionPumpRequest(
          sortByInfusionPumps(sortByDirty(sortEquipmentByFloor(f))),
          sortByClean(sortEquipmentByFloor(f)));
    }
    alertTable.setItems(MESRDAO.getAlerts());
  }

  private void sendBedRequest(ObservableList<EquipmentUnit> beds) {
    MedicalEquipmentServiceRequest MESR = new MedicalEquipmentServiceRequest();
    ObservableList<MedicalEquipmentServiceRequest> mesrList = FXCollections.observableArrayList();
    if (beds.size() >= 2) {
      MESR.setRequestID(ServiceRequestDAO.getDAO().makeMedicalEquipmentServiceRequestID());
      MESR.setDestination(locationDAO.getRecord("xSTOR001L1"));
      MESR.setStatus("PROC");
      MESR.setAssignee(EmployeeDAO.getDAO().getRecord("EMPL0001"));
      MESR.setEquipmentType("Bed");
      MESR.setQuantity(beds.size());
      requestDAO.addRecord(MESR);
      mesrList.add(requestDAO.getMedicalEquipmentServiceRequest(MESR.getRequestID()));
      //      alertTable.setItems(mesrList);
      //      for (MedicalEquipmentServiceRequest r : MESRDAO.getAlerts()) {
      //        if (r.equals(requestDAO.getMedicalEquipmentServiceRequest(MESR.getRequestID()))) {
      //
      //        }
      //      }

      MESRDAO.addAlert(requestDAO.getMedicalEquipmentServiceRequest(MESR.getRequestID()));
    }
  }

  private void sendInfusionPumpRequest(
      ObservableList<EquipmentUnit> infusionPumps, int cleanInfPumpNum) {
    MedicalEquipmentServiceRequest MESR = new MedicalEquipmentServiceRequest();
    ObservableList<MedicalEquipmentServiceRequest> mesrList = FXCollections.observableArrayList();

    if (infusionPumps.size() >= 4 || (cleanInfPumpNum < 5 && infusionPumps.size() > 0)) {
      MESR.setRequestID(ServiceRequestDAO.getDAO().makeMedicalEquipmentServiceRequestID());
      MESR.setDestination(locationDAO.getRecord("xSTOR00201"));
      MESR.setStatus("PROC");
      MESR.setAssignee(EmployeeDAO.getDAO().getRecord("EMPL0001"));
      MESR.setEquipmentType("Infusion Pump");
      MESR.setQuantity(infusionPumps.size());
      requestDAO.addRecord(MESR);
      mesrList.add(requestDAO.getMedicalEquipmentServiceRequest(MESR.getRequestID()));
      //      alertTable.setItems(mesrList);
      MESRDAO.addAlert(requestDAO.getMedicalEquipmentServiceRequest(MESR.getRequestID()));
    }
  }

  private ObservableList<EquipmentUnit> sortByInfusionPumps(
      ObservableList<EquipmentUnit> dirtyEquipmentOnFloor) {
    ObservableList<EquipmentUnit> dirtyInfusionPumps = FXCollections.observableArrayList();
    for (EquipmentUnit e : dirtyEquipmentOnFloor) {
      if (e.getType().getModel().toLowerCase().contains("infusion pump")) {
        dirtyInfusionPumps.add(e);
      }
    }
    return dirtyInfusionPumps;
  }

  private ObservableList<EquipmentUnit> sortByBeds(ObservableList<EquipmentUnit> dirtyEquipment) {
    ObservableList<EquipmentUnit> dirtyBeds = FXCollections.observableArrayList();
    for (EquipmentUnit e : dirtyEquipment) {
      if (e.getType().getModel().toLowerCase().contains("bed")) {
        System.out.println("sortByBeds");
        dirtyBeds.add(e);
      }
    }
    return dirtyBeds;
  }

  private ObservableList<EquipmentUnit> sortByDirty(ObservableList<EquipmentUnit> equipAtLocation) {
    ObservableList<EquipmentUnit> dirtyEquipment = FXCollections.observableArrayList();
    for (EquipmentUnit equipmentUnit : equipAtLocation) {
      if ((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("dirt")
              && equipmentUnit.getIsAvailableChar() == 'N')
          || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
              && equipmentUnit.getIsAvailableChar() == 'N')) {
        // System.out.println("dirtyShit");

        dirtyEquipment.add(equipmentUnit);
      }
    }
    return dirtyEquipment;
  }

  private ObservableList<EquipmentUnit> getEquipmentAtLocation(Location location) {
    ObservableList<EquipmentUnit> locationsEquipment = FXCollections.observableArrayList();
    locationsEquipment.addAll(location.getUnitsAtLocation());
    return locationsEquipment;
  }

  private ObservableList<EquipmentUnit> sortEquipmentByFloor(String floor) {
    ObservableList<EquipmentUnit> equipmentOnFloor = FXCollections.observableArrayList();
    List<EquipmentUnit> equipmentUnitList = equipmentUnitDAO.getAllRecords();
    for (EquipmentUnit e : equipmentUnitList) {
      if (e.getCurrLocation().getFloor().equals(floor)) {
        equipmentOnFloor.add(e);
      }
    }
    return equipmentOnFloor;
  }

  private int sortByClean(ObservableList<EquipmentUnit> equipOnFloor) {
    ObservableList<EquipmentUnit> cleanEquipment = FXCollections.observableArrayList();
    for (EquipmentUnit equipmentUnit : equipOnFloor) {
      if ((equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("stor")
              && equipmentUnit.getIsAvailableChar() == 'Y')
          || (equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("hall")
              && equipmentUnit.getIsAvailableChar() == 'Y')) {
        cleanEquipment.add(equipmentUnit);
      }
    }
    return cleanEquipment.size();
  }
}
