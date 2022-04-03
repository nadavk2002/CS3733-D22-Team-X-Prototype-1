package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.*;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class GraphicalMapEditorController implements Initializable {

  @FXML private Button ToMainMenu;

  @FXML private HBox hBox1;

  @FXML private TabPane tabPane;

  @FXML private Tab tabLL2;

  @FXML private Tab tabLL1;

  @FXML private TableView<Location> table;

  @FXML private TableColumn<Location, String> nodeID;

  @FXML private TableColumn<Location, String> x;

  @FXML private TableColumn<Location, String> y;

  @FXML private TableColumn<Location, String> floor;

  @FXML private TableColumn<Location, String> building;

  @FXML private TableColumn<Location, String> nodeType;

  @FXML private TableColumn<Location, String> longName;

  @FXML private TableColumn<Location, String> shortName;

  @FXML
  private void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  private ObservableList<Location> locationListFill() {
    ObservableList<Location> tableList = FXCollections.observableArrayList();
    LocationDAO allLocations = new LocationDAOImpl();
    List<Location> locationList = allLocations.getAllLocations();
    for (Location loc : locationList) {
      tableList.add(loc);
    }
    return tableList;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // hBox1 = new HBox(90, tabPane, table);
    hBox1.getChildren().add(tabPane);
    hBox1.getChildren().add(table);
    hBox1.setSpacing(90);
    // Tab tabLL2 = new Tab("Lower Level 2", new ImageView(Crop_LL2_Floor.png));
    locationListFill();
    nodeID.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeID"));
    x.setCellValueFactory(new PropertyValueFactory<Location, String>("x"));
    y.setCellValueFactory(new PropertyValueFactory<Location, String>("y"));
    floor.setCellValueFactory(new PropertyValueFactory<Location, String>("floor"));
    building.setCellValueFactory(new PropertyValueFactory<Location, String>("building"));
    nodeType.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeType"));
    longName.setCellValueFactory(new PropertyValueFactory<Location, String>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<Location, String>("shortName"));
    table.setItems(locationListFill());
  }
}
