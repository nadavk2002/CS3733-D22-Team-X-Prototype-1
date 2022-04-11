package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAOImpl;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class GraphicalMapEditorTableOverlayController implements Initializable {
  @FXML private JFXTreeTableView<Location> table;
  @FXML private TreeTableColumn<Location, String> nodeID;
  @FXML private TreeTableColumn<Location, String> xCoord;
  @FXML private TreeTableColumn<Location, String> yCoord;
  @FXML private TreeTableColumn<Location, String> floor;
  @FXML private TreeTableColumn<Location, String> building;
  @FXML private TreeTableColumn<Location, String> nodeType;
  @FXML private TreeTableColumn<Location, String> longName;
  @FXML private TreeTableColumn<Location, String> shortName;
  private LocationDAO locDAO;
  //  @FXML TextField searchLocations;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    locDAO = new LocationDAOImpl();
    table.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);
    nodeID.setCellValueFactory(new TreeItemPropertyValueFactory<>("nodeID"));
    xCoord.setCellValueFactory(new TreeItemPropertyValueFactory<>("x"));
    yCoord.setCellValueFactory(new TreeItemPropertyValueFactory<>("y"));
    floor.setCellValueFactory(new TreeItemPropertyValueFactory<>("floor"));
    building.setCellValueFactory(new TreeItemPropertyValueFactory<>("building"));
    nodeType.setCellValueFactory(new TreeItemPropertyValueFactory<>("nodeType"));
    longName.setCellValueFactory(new TreeItemPropertyValueFactory<>("longName"));
    shortName.setCellValueFactory(new TreeItemPropertyValueFactory<>("shortName"));
    locationListFill();
  }

  private void locationListFill() {
    ObservableList<Location> tableList = FXCollections.observableArrayList();
    locDAO = new LocationDAOImpl();
    List<Location> locationList = locDAO.getAllLocations();
    tableList.addAll(locationList);
    TreeItem<Location> root = new RecursiveTreeItem<>(tableList, RecursiveTreeObject::getChildren);
    table.setRoot(root);
  }
}
