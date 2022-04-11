package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GraphicalMapEditorEquipmentTableOverlayController implements Initializable {
  @FXML private TableView<EquipmentUnit> table;
  @FXML private TableColumn<EquipmentUnit, String> unitID;
  @FXML private TableColumn<EquipmentUnit, String> type;
  @FXML private TableColumn<EquipmentUnit, String> availability;
  @FXML private TableColumn<EquipmentUnit, String> currLoc;

  private EquipmentUnitDAO equipmentUnitDAO;
  @FXML private TextField searchEquipmentField;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    equipmentUnitDAO = new EquipmentUnitDAOImpl();
    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    unitID.setCellValueFactory(new PropertyValueFactory<>("unitID"));
    type.setCellValueFactory(new PropertyValueFactory<>("type"));
    availability.setCellValueFactory(new PropertyValueFactory<>("isAvailableChar"));
    currLoc.setCellValueFactory(new PropertyValueFactory<>("currLocationShortName"));

    searchEqipment();
  }

  void searchEqipment() {
    FilteredList<EquipmentUnit> filteredData = new FilteredList<>(equipmentListFill(), b -> true);
    searchEquipmentField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredData.setPredicate(
                  equipmentUnit -> {
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (equipmentUnit.getUnitID().toLowerCase().contains(lowerCaseFilter)) {
                      return true;
                    } else if (equipmentUnit.getType().toLowerCase().contains(lowerCaseFilter)) {
                      return true;
                    } else if (equipmentUnit
                        .getCurrLocationShortName()
                        .toLowerCase()
                        .contains(lowerCaseFilter)) {
                      return true;
                    } else {
                      return false;
                    }
                  });
            });
    SortedList<EquipmentUnit> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(table.comparatorProperty());
    populateTable(sortedData);
  }

  private ObservableList<EquipmentUnit> equipmentListFill() {
    ObservableList<EquipmentUnit> tableList = FXCollections.observableArrayList();
    equipmentUnitDAO = new EquipmentUnitDAOImpl();
    List<EquipmentUnit> equipmentUnitList = equipmentUnitDAO.getAllEquipmentUnits();
    tableList.addAll(equipmentUnitList);
    return tableList;
  }

  private void populateTable(ObservableList<EquipmentUnit> equipmentUnits) {
    table.setItems(equipmentUnits);
  }
}
