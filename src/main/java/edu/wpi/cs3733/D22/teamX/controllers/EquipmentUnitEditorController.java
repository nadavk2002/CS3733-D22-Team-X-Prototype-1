package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class EquipmentUnitEditorController implements Initializable {

  private EquipmentUnit equipment;
  private List<Location> locations;
  private List<EquipmentType> types;

  @FXML private JFXCheckBox availableCheck;

  @FXML private ChoiceBox<String> equipLocationChoice, typeChoice;

  public EquipmentUnitEditorController(EquipmentUnit equipment) {
    this.equipment = equipment;
    locations = LocationDAO.getDAO().getAllRecords();
    types = EquipmentTypeDAO.getDAO().getAllRecords();
  }

  @FXML
  public void submitEquipment() {
    EquipmentUnitDAO equipDAO = EquipmentUnitDAO.getDAO();
    EquipmentUnit newEquipment =
        new EquipmentUnit(
            equipment.getUnitID(),
            EquipmentTypeDAO.getDAO().getRecord(typeChoice.getValue()),
            availableCheck.isSelected(),
            LocationDAO.getDAO()
                .getAllRecords()
                .get(equipLocationChoice.getSelectionModel().getSelectedIndex()));
    try {
      equipDAO.updateRecord(newEquipment);
    } catch (Exception e) {
      equipDAO.addRecord(newEquipment);
    }
    ((Stage) availableCheck.getScene().getWindow()).close();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    for (Location l : locations) {
      equipLocationChoice.getItems().add(l.getShortName());
    }
    for (EquipmentType type : types) {
      typeChoice.getItems().add(type.getModel());
    }
    try {
      typeChoice.setValue(equipment.getTypeName());
    } catch (Exception e) {
      typeChoice.setValue("");
    }
    availableCheck.setSelected(equipment.isAvailable());
    equipLocationChoice.setValue(equipment.getCurrLocation().getShortName());
  }
}
