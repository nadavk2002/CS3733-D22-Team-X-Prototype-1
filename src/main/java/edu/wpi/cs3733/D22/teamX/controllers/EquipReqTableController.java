package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EquipReqTableController {
  @FXML private TableView<Location> table;
  @FXML private TableColumn<Location, String> nodeID;
  // @FXML private TableColumn<Location, Integer> xCoord;
  // @FXML private TableColumn<Location, Integer> yCoord;
  @FXML private TableColumn<Location, String> floor;
  @FXML private TableColumn<Location, String> building;
  @FXML private TableColumn<Location, String> nodeType; // Add more columns or remove if need be

  @FXML
  private TableColumn<Location, String> longName; // Put an entity in these columns that makes sense

  @FXML private TableColumn<Location, String> shortName;

  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml"))));
  }
}
