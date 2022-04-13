package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GraphicalMapEditorLocationTableOverlayController implements Initializable {
  @FXML private TableView<Location> table;
  @FXML private TableColumn<Location, String> nodeID;
  @FXML private TableColumn<Location, String> xCoord;
  @FXML private TableColumn<Location, String> yCoord;
  @FXML private TableColumn<Location, String> floor;
  @FXML private TableColumn<Location, String> building;
  @FXML private TableColumn<Location, String> nodeType;
  @FXML private TableColumn<Location, String> longName;
  @FXML private TableColumn<Location, String> shortName;
  private LocationDAO locDAO = LocationDAO.getDAO();
  @FXML private TextField searchLocationsField;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    searchLocationsField.setPromptText("Search Here");

    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    nodeID.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
    xCoord.setCellValueFactory(new PropertyValueFactory<>("x"));
    yCoord.setCellValueFactory(new PropertyValueFactory<>("y"));
    floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
    building.setCellValueFactory(new PropertyValueFactory<>("building"));
    nodeType.setCellValueFactory(new PropertyValueFactory<>("nodeType"));
    longName.setCellValueFactory(new PropertyValueFactory<>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<>("shortName"));
    searchLocations();
  }

  void searchLocations() {
    FilteredList<Location> filteredData = new FilteredList<>(locationListFill(), b -> true);
    searchLocationsField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredData.setPredicate(
                  location -> {
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (location.getNodeID().toLowerCase().contains(lowerCaseFilter)) {
                      return true;
                    } else if (location.getBuilding().toLowerCase().contains(lowerCaseFilter)) {
                      return true;
                    } else if (location.getNodeType().toLowerCase().contains(lowerCaseFilter)) {
                      return true;
                    } else if (location.getLongName().toLowerCase().contains(lowerCaseFilter)) {
                      return true;
                    } else if (location.getShortName().toLowerCase().contains(lowerCaseFilter)) {
                      return true;
                    } else {
                      return false;
                    }
                  });
            });
    SortedList<Location> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(table.comparatorProperty());
    populateTable(sortedData);
  }

  private ObservableList<Location> locationListFill() {
    ObservableList<Location> tableList = FXCollections.observableArrayList();
    List<Location> locationList = locDAO.getAllRecords();
    tableList.addAll(locationList);
    return tableList;
  }

  private void populateTable(ObservableList<Location> locations) {
    table.setItems(locations);
  }
}
