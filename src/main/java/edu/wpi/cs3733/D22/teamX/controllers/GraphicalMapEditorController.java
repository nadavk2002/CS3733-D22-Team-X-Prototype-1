package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.cs3733.D22.teamX.*;
import edu.wpi.cs3733.D22.teamX.entity.*;
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

  @FXML
  private Button ToMainMenu,
      deleteLocationButton,
      submitLocationButton,
      deleteEquipment,
      submitEquipment;
  @FXML private ChoiceBox<String> locationChoice, equipmentChoice, equipLocationChoice;

  @FXML private HBox hBox1;

  @FXML private Group imageGroup;

  @FXML private ImageView imageView;

  @FXML private TableView<Location> locationTable;
  @FXML private TableView<EquipmentUnit> equipTable;

  @FXML private TableColumn<Location, String> nodeID;

  @FXML private TableColumn<Location, String> x;

  @FXML private TableColumn<Location, String> y;

  @FXML private TableColumn<Location, String> floor;

  @FXML private TableColumn<Location, String> building;

  @FXML private TableColumn<Location, String> nodeType;

  @FXML private TableColumn<Location, String> longName;

  @FXML private TableColumn<Location, String> shortName;

  @FXML private TableColumn<EquipmentUnit, String> unitIdCol, typeCol, availableCol, curLocationCol;

  @FXML private JFXCheckBox availableCheck;

  @FXML
  private TextField nodeIdText,
      xCordText,
      yCordText,
      floorText,
      buildingText,
      nodeTypeText,
      longNameText,
      shortNameText,
      unitIdText,
      typeText;

  private LocationDAO locDAO;
  private EquipmentUnitDAO equipDAO;

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
    locDAO = new LocationDAOImpl();
    List<Location> locationList = locDAO.getAllLocations();
    for (Location loc : locationList) {
      tableList.add(loc);
    }
    return tableList;
  }

  private ObservableList<EquipmentUnit> equipmentListFill() {
    ObservableList<EquipmentUnit> tableList = FXCollections.observableArrayList();
    List<EquipmentUnit> equipment = equipDAO.getAllEquipmentUnits();
    for (EquipmentUnit equip : equipment) {
      tableList.add(equip);
    }
    return tableList;
  }

  private void loadMap(String floor) {
    locationChoice.setValue("");
    locationChoice.getItems().clear();
    equipmentChoice.setValue("");
    equipmentChoice.getItems().clear();
    drawCirclesSetLocationList(floor);
    drawCirclesSetEquipmentList(floor);
  }

  private void drawCirclesSetLocationList(String floor) {
    List<Location> locationList = locDAO.getAllLocations();
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

  private void drawCirclesSetEquipmentList(String floor) {
    List<EquipmentUnit> equipment = equipDAO.getAllEquipmentUnits();
    for (int i = 0; i < equipment.size(); i++) {
      if (equipment.get(i).getCurrLocation().getFloor().equals(floor)) {
        Circle circle = new Circle();
        circle.setRadius(2);
        circle.setCenterX(equipment.get(i).getCurrLocation().getxCoord());
        circle.setCenterY(equipment.get(i).getCurrLocation().getyCoord());
        circle.setFill(Paint.valueOf("GREEN"));
        imageGroup.getChildren().add(circle);
        equipmentChoice.getItems().add(equipment.get(i).getUnitID());
      }
    }
  }

  /** Deletes selected node id in the dropdown */
  public void deleteLocation() {
    String locationToDelete = locationChoice.getValue(); // Node ID
    String floor = locDAO.getLocation(locationToDelete).getFloor();
    System.out.println(locDAO.getLocation(locationToDelete));
    locDAO.deleteLocation(locDAO.getLocation(locationToDelete));
    loadLocation(floor);
    locationTable.getItems().clear();
    locationTable.setItems(locationListFill());
  }

  /** Deletes selected node id in the dropdown */
  public void deleteEquipment() {
    String equipmentToDelete = equipmentChoice.getValue();
    String floor = equipDAO.getEquipmentUnit(equipmentToDelete).getCurrLocation().getFloor();
    equipDAO.deleteEquipmentUnit(equipDAO.getEquipmentUnit(equipmentToDelete));
    loadLocation(floor);
    equipTable.getItems().clear();
    equipTable.setItems(equipmentListFill());
  }

  @FXML
  public void equipmentSelected() {
    try {
      EquipmentUnit equipment = equipDAO.getEquipmentUnit(equipmentChoice.getValue());
      unitIdText.setText(equipment.getUnitID());
      typeText.setText(equipment.getType());
      availableCheck.setSelected(equipment.isAvailable());
      equipLocationChoice.setValue(equipment.getCurrLocation().getNodeID());
    } catch (NoSuchElementException e) {
      unitIdText.setText("");
      typeText.setText("");
      availableCheck.setSelected(false);
      equipLocationChoice.setValue("");
    }
  }

  @FXML
  public void locationSelected() {
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
    loadMap(location);
  }

  @FXML
  public void submitEquipment() {
    List<EquipmentUnit> allEquipment = equipDAO.getAllEquipmentUnits();
    for (int i = 0; i < allEquipment.size(); i++) {
      if (allEquipment.get(i).getUnitID().equals(unitIdText.getText())) {
        EquipmentUnit replaceEquip = allEquipment.get(i);
        replaceEquip.setAvailable(availableCheck.isSelected());
        replaceEquip.setCurrLocation(locDAO.getLocation(equipLocationChoice.getValue()));
        replaceEquip.setType(typeText.getText());
        equipDAO.updateEquipmentUnit(replaceEquip);
        loadLocation(replaceEquip.getCurrLocation().getFloor());
        equipTable.getItems().clear();
        equipTable.setItems(equipmentListFill());
        return;
      }
    }
    EquipmentUnit newEquipment = new EquipmentUnit();
    newEquipment.setAvailable(availableCheck.isSelected());
    newEquipment.setCurrLocation(locDAO.getLocation(equipLocationChoice.getValue()));
    newEquipment.setType(typeText.getText());
    newEquipment.setUnitID(unitIdText.getText());

    equipDAO.addUpdateEquipmentUnit(newEquipment);
    loadLocation(newEquipment.getCurrLocation().getFloor());
    equipTable.getItems().clear();
    equipTable.setItems(equipmentListFill());
  }

  @FXML
  public void submitLocation() {
    List<Location> allLocations = locDAO.getAllLocations();
    for (int i = 0; i < allLocations.size(); i++) {
      if (allLocations.get(i).getNodeID().equals(nodeIdText.getText())) {
        Location replaceLoc = allLocations.get(i);
        replaceLoc.setxCoord(Integer.parseInt(xCordText.getText()));
        replaceLoc.setyCoord(Integer.parseInt(yCordText.getText()));
        replaceLoc.setFloor(floorText.getText());
        replaceLoc.setBuilding(buildingText.getText());
        replaceLoc.setLongName(longNameText.getText());
        replaceLoc.setShortName(shortNameText.getText());
        replaceLoc.setNodeType(nodeTypeText.getText());
        locDAO.updateLocation(replaceLoc);
        loadLocation(replaceLoc.getFloor());
        locationTable.getItems().clear();
        locationTable.setItems(locationListFill());
        return;
      }
    }
    Location newLocation = new Location();

    newLocation.setxCoord(Integer.parseInt(xCordText.getText()));
    newLocation.setyCoord(Integer.parseInt(yCordText.getText()));
    newLocation.setFloor(floorText.getText());
    newLocation.setBuilding(buildingText.getText());
    newLocation.setNodeID(nodeIdText.getText());
    newLocation.setNodeType(nodeTypeText.getText());
    newLocation.setLongName(longNameText.getText());
    newLocation.setShortName(shortNameText.getText());
    locDAO.addLocation(newLocation);
    loadLocation(newLocation.getFloor());
    locationTable.getItems().clear();
    locationTable.setItems(locationListFill());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // hBox1.getChildren().add(table);

    hBox1.setSpacing(90);
    locationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    nodeID.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeID"));
    x.setCellValueFactory(new PropertyValueFactory<Location, String>("x"));
    y.setCellValueFactory(new PropertyValueFactory<Location, String>("y"));
    floor.setCellValueFactory(new PropertyValueFactory<Location, String>("floor"));
    building.setCellValueFactory(new PropertyValueFactory<Location, String>("building"));
    nodeType.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeType"));
    longName.setCellValueFactory(new PropertyValueFactory<Location, String>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<Location, String>("shortName"));
    ObservableList<Location> locationList = locationListFill();
    locationTable.setItems(locationList);

    equipDAO = new EquipmentUnitDAOImpl();
    equipTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    unitIdCol.setCellValueFactory(new PropertyValueFactory<EquipmentUnit, String>("unitID"));
    typeCol.setCellValueFactory(new PropertyValueFactory<EquipmentUnit, String>("type"));
    availableCol.setCellValueFactory(
        new PropertyValueFactory<EquipmentUnit, String>("isAvailableChar"));
    curLocationCol.setCellValueFactory(
        new PropertyValueFactory<EquipmentUnit, String>("currLocationShortName"));
    ObservableList<EquipmentUnit> equipmentList = equipmentListFill();
    equipTable.setItems(equipmentList);

    List<Location> locs = locDAO.getAllLocations();
    for (Location newLocation : locs) {
      equipLocationChoice.getItems().add(newLocation.getNodeID());
    }
  }
}
