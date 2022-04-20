package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardAlertsController implements Initializable {
  @FXML private TableView<EquipmentUnit> alertTable;
  @FXML private TableColumn<EquipmentUnit, String> unitID;
  @FXML private TableColumn<EquipmentUnit, String> alert;
  @FXML private TableColumn<EquipmentUnit, String> timeOfAlert;
  // @FXML private TableColumn<String, String> sendAlert;
  @FXML private JFXButton hideAlerts;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    alertTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    unitID.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    unitID.setCellValueFactory(new PropertyValueFactory<>("requestD"));
    unitID.setCellValueFactory(new PropertyValueFactory<>("destination"));
  }

  @FXML
  private void fillTable(ObservableList<EquipmentUnit> dirty) {}


  private ObservableList<EquipmentUnit> sortByBeds(ObservableList<EquipmentUnit> dirtyEquipment){
    ObservableList<EquipmentUnit> dirtyBeds = FXCollections.observableArrayList();
    for(EquipmentUnit e : dirtyEquipment){
      //if(e.)
    }
    return dirtyBeds;
  }
  @FXML
  private ObservableList<EquipmentUnit> sortByDirty(ObservableList<EquipmentUnit> equipOnFloor) {
    ObservableList<EquipmentUnit> dirtyEquipment = FXCollections.observableArrayList();
    for (EquipmentUnit equipmentUnit : equipOnFloor) {
      if (!equipmentUnit.getCurrLocation().getNodeType().toLowerCase().contains("dirt")) {
        dirtyEquipment.add(equipmentUnit);
      }
    }
    return dirtyEquipment;
  }
}
