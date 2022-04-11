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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import net.kurobako.gesturefx.GesturePane;

/**
 * Controller for the map editor page. This graphically displays equipment and location data on a
 * map of the tower at the hostpital.
 */
public class GraphicalMapEditorController implements Initializable {

  @FXML
  private Button ToMainMenu,
      deleteLocationButton,
      submitLocationButton,
      deleteEquipment,
      submitEquipment;
  @FXML private ChoiceBox<String> locationChoice, equipmentChoice, equipLocationChoice;

  @FXML private HBox hBox1;
  @FXML private VBox mapBox;

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

  @FXML private GesturePane pane;

  @FXML private TableColumn<EquipmentUnit, String> unitIdCol, typeCol, availableCol, curLocationCol;
  @FXML private JFXCheckBox availableCheck, showLocCheck, showEquipCheck;
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
  private GesturePane gesturePane;

  /**
   * Returns to the main menu of the JavaFX App
   *
   * @throws IOException
   */
  @FXML
  private void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  /** Loads the Lower Level 1 map */
  @FXML
  public void LL1Click() {
    loadLocation("L1");
  }

  /** Loads the Lower Level 2 map */
  @FXML
  public void LL2Click() {
    loadLocation("L2");
  }

  /** Loads the ground level map */
  @FXML
  public void groundClick() {
    loadLocation("G");
  }

  /** Loads the 1st floor map */
  @FXML
  public void firstClick() {
    loadLocation("1");
  }

  /** Loads the second floor map */
  @FXML
  public void secondClick() {
    loadLocation("2");
  }

  /** Loads the third floor map */
  @FXML
  public void thirdClick() {
    loadLocation("3");
  }

  /**
   * Returns an observable list of location objects. This list can then be used to easily fill a
   * table with the object data.
   *
   * @return ObservableList of all locations in the system
   */
  private ObservableList<Location> locationListFill() {
    ObservableList<Location> tableList = FXCollections.observableArrayList();
    locDAO = new LocationDAOImpl();
    List<Location> locationList = locDAO.getAllLocations();
    for (Location loc : locationList) {
      tableList.add(loc);
    }
    return tableList;
  }

  /**
   * Returns an observable list of equipment objects. This list can then be used to easily fill a
   * table with the object data.
   *
   * @return ObservableList of all the equipment in the system.
   */
  private ObservableList<EquipmentUnit> equipmentListFill() {
    ObservableList<EquipmentUnit> tableList = FXCollections.observableArrayList();
    List<EquipmentUnit> equipment = equipDAO.getAllEquipmentUnits();
    for (EquipmentUnit equip : equipment) {
      tableList.add(equip);
    }
    return tableList;
  }

  /**
   * Loads all the map data for the floor. (Location drop down + map, equipment drop down + map),
   * table data
   *
   * @param floor String of the floor number (L1, G, 1, etc.)
   */
  private void loadMap(String floor) {
    locationChoice.setValue("");
    locationChoice.getItems().clear();
    equipmentChoice.setValue("");
    equipmentChoice.getItems().clear();
    equipLocationChoice.setValue("");
    equipLocationChoice.getItems().clear();
    List<Location> allLocations = locationListFill();
    for (int i = 0; i < allLocations.size(); i++) {
      equipLocationChoice.getItems().add(allLocations.get(i).getNodeID());
    }
    drawCirclesSetLocationList(floor);
    drawCirclesSetEquipmentList(floor);
  }

  /**
   * Draws red dots for all locations that are on the provided floor
   *
   * @param floor String of the floor number (L1, G, 1, etc.)
   */
  private void drawCirclesSetLocationList(String floor) {
    List<Location> locationList = locDAO.getAllLocations();
    Image img = new Image("/edu/wpi/cs3733/D22/teamX/assets/mapLocationMarker.png");
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getFloor().equals(floor)) {
        Rectangle rect = new Rectangle();
        rect.setCursor(Cursor.HAND);
        rect.setUserData(locationList.get(i));
        rect.setHeight(24);
        rect.setWidth(24);
        rect.setX(locationList.get(i).getxCoord() - (rect.getWidth() / 2));
        rect.setY(locationList.get(i).getyCoord() - rect.getHeight());
        rect.setFill(new ImagePattern(img));
        rect.setOnMouseDragged(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {
                pane.setGestureEnabled(false);
                rect.setCursor(Cursor.CLOSED_HAND);
                rect.setFill(Paint.valueOf("LIGHTBLUE"));
                if (event.getX() - (rect.getWidth() / 2) > imageView.getX()
                    && event.getX() <= imageView.getX() + imageView.getBoundsInLocal().getWidth())
                  rect.setX(event.getX() - (rect.getWidth() / 2));
                if (event.getY() - (rect.getHeight() / 2) > imageView.getY()
                    && event.getY() <= imageView.getY() + imageView.getBoundsInLocal().getHeight())
                  rect.setY(event.getY() - rect.getHeight());
              }
            });
        rect.setOnMouseReleased(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {
                pane.setGestureEnabled(true);
                Location l = (Location) rect.getUserData();
                rect.setCursor(Cursor.HAND);
                rect.setFill(new ImagePattern(img));
                locationChoice.setValue(l.getNodeID());
                int x = (int) (rect.getX() + (rect.getWidth() / 2));
                int y = (int) (rect.getY() + rect.getHeight());
                if (x > 610) x = 610;
                if (x < 0) x = 0;
                if (y < 0) y = 0;
                if (y > 610) y = 610;
                xCordText.setText(String.valueOf(x));
                yCordText.setText(String.valueOf(y));
              }
            });
        rect.setVisible(showLocCheck.isSelected());
        imageGroup.getChildren().add(rect);
        locationChoice.getItems().add(locationList.get(i).getNodeID());
      }
    }
  }

  /**
   * Draws green dots for all equipment that are on the provided floor
   *
   * @param floor String of the floor number (L1, G, 1, etc.)
   */
  private void drawCirclesSetEquipmentList(String floor) {
    equipDAO = new EquipmentUnitDAOImpl();
    List<EquipmentUnit> equipment = equipDAO.getAllEquipmentUnits();
    for (int i = 0; i < equipment.size(); i++) {
      if (equipment.get(i).getCurrLocation().getFloor().equals(floor)) {
        Circle circle = new Circle();
        circle.setRadius(4);
        circle.setUserData(equipment.get(i));
        circle.setCenterX(equipment.get(i).getCurrLocation().getxCoord());
        circle.setCenterY(equipment.get(i).getCurrLocation().getyCoord());
        circle.setFill(Paint.valueOf("GREEN"));
        circle.setVisible(showEquipCheck.isSelected());
        imageGroup.getChildren().add(circle);
        equipmentChoice.getItems().add(equipment.get(i).getUnitID());
      }
    }
  }

  /** Hides or displays the dots based on the two checkboxes above the map */
  private void showDots() {
    ObservableList<Node> nodes = imageGroup.getChildren();
    for (Node node : nodes) {
      if (node instanceof Rectangle || node instanceof Circle) {
        if (node.getUserData() instanceof Location) node.setVisible(showLocCheck.isSelected());
        else if (node.getUserData() instanceof EquipmentUnit)
          node.setVisible(showEquipCheck.isSelected());
      }
    }
  }

  /** Deletes selected node id in the dropdown */
  public void deleteLocation() {
    String locationToDelete = locationChoice.getValue(); // Node ID
    String floor = locDAO.getLocation(locationToDelete).getFloor();
    locDAO.deleteLocation(locDAO.getLocation(locationToDelete));
    loadLocation(floor);
    loadTables();
  }

  /** Deletes selected node id in the dropdown */
  public void deleteEquipment() {
    String equipmentToDelete = equipmentChoice.getValue();
    String floor = equipDAO.getEquipmentUnit(equipmentToDelete).getCurrLocation().getFloor();
    equipDAO.deleteEquipmentUnit(equipDAO.getEquipmentUnit(equipmentToDelete));
    loadTables();
    loadLocation(floor);
  }

  /** Fills text boxes with equipment data when a piece of equipment is chosen in the dropdown. */
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

  /** Location data populates in their corresponding text fields */
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

  /**
   * Loads image for given floor, and then loads map data.
   *
   * @param location Floor level
   */
  public void loadLocation(String location) {
    imageGroup.getChildren().clear();
    Image img =
        new Image(
            getClass()
                .getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/" + location + ".png"));
    ImageView newImage = new ImageView(img);
    newImage.setFitHeight(610);
    newImage.setFitWidth(610);
    imageGroup.getChildren().add(newImage);
    loadMap(location);
  }

  /**
   * Submit new equipment to the database based on the filled in text data or updates equipment with
   * a matching ID.
   */
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
        loadTables();
        return;
      }
    }
    EquipmentUnit newEquipment = new EquipmentUnit();
    newEquipment.setAvailable(availableCheck.isSelected());
    newEquipment.setCurrLocation(locDAO.getLocation(equipLocationChoice.getValue()));
    newEquipment.setType(typeText.getText());
    newEquipment.setUnitID(unitIdText.getText());

    equipDAO.addEquipmentUnit(newEquipment);
    loadLocation(newEquipment.getCurrLocation().getFloor());
    equipTable.getItems().clear();
    equipTable.setItems(equipmentListFill());
  }

  /** Submits a new location with the given data or updates the location with the matching id. */
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
        loadTables();
        loadLocation(replaceLoc.getFloor());
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
    loadTables();
  }

  /** Fill tables with location and equipment data from the database */
  private void loadTables() {
    locationTable.getItems().clear();
    locationTable.setItems(locationListFill());
    equipTable.getItems().clear();
    equipTable.setItems(equipmentListFill());
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mapBox.getChildren().remove(imageGroup);
    pane = new GesturePane(imageGroup);
    mapBox.setMinWidth(610);
    mapBox.getChildren().add(pane);
    pane.setMinScale(1);
    pane.setMaxScale(4.5);
    pane.setScrollBarPolicy(GesturePane.ScrollBarPolicy.NEVER);
    pane.setScrollMode(GesturePane.ScrollMode.ZOOM);
    showLocCheck.setSelected(true);
    showEquipCheck.setSelected(true);
    hBox1.setSpacing(90);

    locDAO = new LocationDAOImpl();
    locationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    nodeID.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeID"));
    x.setCellValueFactory(new PropertyValueFactory<Location, String>("x"));
    y.setCellValueFactory(new PropertyValueFactory<Location, String>("y"));
    floor.setCellValueFactory(new PropertyValueFactory<Location, String>("floor"));
    building.setCellValueFactory(new PropertyValueFactory<Location, String>("building"));
    nodeType.setCellValueFactory(new PropertyValueFactory<Location, String>("nodeType"));
    longName.setCellValueFactory(new PropertyValueFactory<Location, String>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<Location, String>("shortName"));

    equipDAO = new EquipmentUnitDAOImpl();
    equipTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    unitIdCol.setCellValueFactory(new PropertyValueFactory<EquipmentUnit, String>("unitID"));
    typeCol.setCellValueFactory(new PropertyValueFactory<EquipmentUnit, String>("type"));
    availableCol.setCellValueFactory(
        new PropertyValueFactory<EquipmentUnit, String>("isAvailableChar"));
    curLocationCol.setCellValueFactory(
        new PropertyValueFactory<EquipmentUnit, String>("currLocationShortName"));

    showEquipCheck.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            showDots();
          }
        });
    showLocCheck.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            showDots();
          }
        });

    loadTables();
    loadLocation("G");
  }
}
