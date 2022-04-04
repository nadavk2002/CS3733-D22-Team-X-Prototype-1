package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.LocationDAO;
import edu.wpi.cs3733.D22.teamX.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamX.entity.LaundyServiceRequest;
import edu.wpi.cs3733.D22.teamX.entity.Location;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReqLaundryController implements Initializable {
  @FXML private VBox selectionMenuColumn;
  @FXML private VBox labelColumn;
  @FXML private VBox submitColumn;
  @FXML private HBox buttonRow;
  @FXML private Button ToMainMenu;
  @FXML private ChoiceBox<String> selectLaundryType, roomNum;
  @FXML private TextField assignStaff, serviceStatus;
  @FXML private TableView table;
  @FXML private TableColumn c1, c2;

  private LocationDAO locationDAO;
  private List<Location> locations;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    locationDAO = new LocationDAOImpl();
    locations = locationDAO.getAllLocations();
    resetFields();
    selectionMenuColumn.setSpacing(20);
    labelColumn.setSpacing(28);
    submitColumn.setSpacing(20);
    buttonRow.setSpacing(20);
    roomNum.setItems(getLocationNames());
    selectLaundryType
        .getItems()
        .addAll(new String[] {"Linens", "Gowns", "Bedding", "Scrubs", "Coats"});
  }

  /**
   * Creates a list of all locations with their short names.
   *
   * @return List of short names of all destinations
   */
  public ObservableList<String> getLocationNames() {
    ObservableList<String> locationNames = FXCollections.observableArrayList();
    for (int i = 0; i < locations.size(); i++) {
      locationNames.add(locations.get(i).getShortName());
    }
    return locationNames;
  }

  @FXML
  void submitRequest() {
    LaundyServiceRequest request = new LaundyServiceRequest();
    request.setRequestID("SAMPLE12");
    request.setDestination(new Location());
    request.setStatus(serviceStatus.getText());
    this.resetFields();
  }

  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException if main menu not switched to
   */
  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  void resetFields() {
    selectLaundryType.setValue("");
    roomNum.setValue("");
    assignStaff.setText("");
    serviceStatus.setText("");
  }
}
