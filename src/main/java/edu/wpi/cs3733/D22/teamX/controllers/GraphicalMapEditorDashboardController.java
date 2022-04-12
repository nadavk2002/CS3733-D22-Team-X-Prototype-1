package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnit;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAO;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAOImpl;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Rectangle;

public class GraphicalMapEditorDashboardController implements Initializable {
  @FXML private Rectangle levFiveDirty;
  @FXML private Rectangle levFiveClean;
  @FXML private Rectangle levFourDirty;
  @FXML private Rectangle levFourClean;
  @FXML private Rectangle levThreeDirty;
  @FXML private Rectangle levThreeClean;
  @FXML private Rectangle levTwoDirty;
  @FXML private Rectangle levTwoClean;
  @FXML private Rectangle levOneDirty;
  @FXML private Rectangle levOneClean;
  @FXML private Rectangle llOneDirty;
  @FXML private Rectangle llOneClean;
  @FXML private Rectangle llTwoDirty;
  @FXML private Rectangle llTwoClean;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    dynamicSizeRectangles(levThreeDirty, levThreeClean, sortEquipmentByFloor("3"));
  }

  private void dynamicSizeRectangles(
      Rectangle dirty, Rectangle clean, ObservableList<EquipmentUnit> equipList) {
    int dirtyAmount = 0;
    int cleanAmount = 0;
    for (EquipmentUnit equipmentUnit : equipList) {
      if (equipmentUnit.getCurrLocation().getShortName().toLowerCase().contains("clean")) {
        cleanAmount++;
      } else {
        dirtyAmount++;
      }
    }
    System.out.println(equipList.size());
    System.out.println((int) ((double) (dirtyAmount) / (double) (equipList.size()) * 400.0));
    System.out.println((int) ((float) (cleanAmount / equipList.size()) * 400));
    dirty.setWidth((int) ((double) (dirtyAmount) / (double) (equipList.size()) * 400.0));
    clean.setWidth((int) ((double) (cleanAmount) / (double) (equipList.size()) * 400.0));
  }

  private ObservableList<EquipmentUnit> sortEquipmentByFloor(String floor) {
    ObservableList<EquipmentUnit> equipmentOnFloor = FXCollections.observableArrayList();
    EquipmentUnitDAO equipmentUnitDAO = new EquipmentUnitDAOImpl();
    List<EquipmentUnit> equipmentUnitList = equipmentUnitDAO.getAllEquipmentUnits();
    for (EquipmentUnit e : equipmentUnitList) {
      if (e.getCurrLocation().getFloor().equals(floor)) {
        equipmentOnFloor.add(e);
      }
    }
    return equipmentOnFloor;
  }
}
