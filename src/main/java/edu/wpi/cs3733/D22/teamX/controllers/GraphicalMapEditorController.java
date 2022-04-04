package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.*;
import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class GraphicalMapEditorController implements Initializable {

  @FXML private Button ToMainMenu, deleteLocationButton;

  @FXML private ChoiceBox<String> locationChoice;

  @FXML private HBox hBox1;

  @FXML private Group imageGroup;

  @FXML private ImageView imageView;

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
  private TextField nodeIdText,
      xCordText,
      yCordText,
      floorText,
      buildingText,
      nodeTypeText,
      longNameText,
      shortNameText;

  @FXML
  private void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  public void LL1Click() {
    loadLocation("L1");
  }

  @FXML
  public void LL2Click() {
    loadLocation("L2");
  }

  @FXML
  public void groundClick() {
    loadLocation("G");
  }

  @FXML
  public void firstClick() {
    loadLocation("1");
  }

  @FXML
  public void secondClick() {
    loadLocation("2");
  }

  @FXML
  public void thirdClick() {
    loadLocation("3");
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

  private void drawCirclesSetList(String floor) {
    LocationDAO allLocations = new LocationDAOImpl();
    List<Location> locationList = allLocations.getAllLocations();
    locationChoice.setValue("");
    locationChoice.getItems().clear();
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getFloor().equals(floor)) {
        Circle circle = new Circle();
        circle.setRadius(3);
        circle.setCenterX(locationList.get(i).getxCoord());
        circle.setCenterY(locationList.get(i).getyCoord());
        circle.setFill(Paint.valueOf("RED"));
        imageGroup.getChildren().add(circle);
        locationChoice.getItems().add(locationList.get(i).getNodeID());
      }
    }
  }

  /** Deletes selected node id in the dropdown */
  public void deleteLocation() {
    String locationToDelete = locationChoice.getValue(); // Node ID
    LocationDAO locDAO = new LocationDAOImpl();
    locDAO.deleteLocation(locDAO.getLocation(locationToDelete));
    drawCirclesSetList(locDAO.getLocation(locationToDelete).getFloor());
  }

  @FXML
  public void locationSelected() {
    LocationDAO locDAO = new LocationDAOImpl();
    try {
      Location selected = locDAO.getLocation(locationChoice.getValue());
      nodeIdText.setText(selected.getNodeID());
      xCordText.setText(selected.getX());
      yCordText.setText(selected.getY());
      floorText.setText(selected.getFloor());
      buildingText.setText(selected.getBuilding());
      nodeTypeText.setText(selected.getNodeType());
      longNameText.setText(selected.getLongName());
      shortNameText.setText(selected.getShortName());
    } catch (NoSuchElementException e) {
      nodeIdText.setText("");
      xCordText.setText("");
      yCordText.setText("");
      floorText.setText("");
      buildingText.setText("");
      nodeTypeText.setText("");
      longNameText.setText("");
      shortNameText.setText("");
    }
  }

  public void loadLocation(String location) {
    imageGroup.getChildren().clear();
    Image img =
        new Image(
            getClass()
                .getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/" + location + ".png"));
    ImageView newImage = new ImageView(img);
    imageGroup.getChildren().add(newImage);
    drawCirclesSetList(location);
  }

  @FXML
  public void submitLocation() {
    LocationDAO locDAO = new LocationDAOImpl();
    List<Location> allLocations = locDAO.getAllLocations();
    for (int i = 0; i < allLocations.size(); i++) {
      if (allLocations.get(i).getNodeID().equals(nodeIdText.getText())) {
        Location replaceLoc = allLocations.get(i);
        replaceLoc.setxCoord(Integer.parseInt(xCordText.getText()));
        replaceLoc.setyCoord(Integer.parseInt(yCordText.getText()));
        replaceLoc.setFloor(floorText.getText());
        replaceLoc.setBuilding(buildingText.getText());
        replaceLoc.setNodeType(nodeTypeText.getText());
        replaceLoc.setLongName(longNameText.getText());
        replaceLoc.setShortName(shortNameText.getText());
        locDAO.updateLocation(replaceLoc);
        loadLocation(replaceLoc.getFloor());
        table.getItems().clear();
        table.setItems(locationListFill());

        return;
      }
    }
    // adding a new location will go here when implemented.

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    hBox1.getChildren().add(table);
    hBox1.setSpacing(90);
    locationListFill();
    nodeID.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeID"));
    x.setCellValueFactory(new PropertyValueFactory<Location, String>("x"));
    y.setCellValueFactory(new PropertyValueFactory<Location, String>("y"));
    floor.setCellValueFactory(new PropertyValueFactory<Location, String>("floor"));
    building.setCellValueFactory(new PropertyValueFactory<Location, String>("building"));
    nodeType.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeType"));
    longName.setCellValueFactory(new PropertyValueFactory<Location, String>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<Location, String>("shortName"));
    ObservableList<Location> locationList = locationListFill();
    table.setItems(locationList);
  }
}
