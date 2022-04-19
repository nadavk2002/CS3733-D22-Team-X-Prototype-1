package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.cs3733.D22.teamX.*;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

/**
 * Controller for the map editor page. This graphically displays equipment and location data on a
 * map of the tower at the hospital.
 */
public class GraphicalMapEditorController implements Initializable {

  @FXML private JFXButton autofiller;
  @FXML
  private Button ToMainMenu,
      deleteLocationButton,
      submitLocationButton,
      deleteEquipment,
      submitEquipmentButton;
  @FXML private ChoiceBox<String> locationChoice, equipmentChoice, equipLocationChoice;

  @FXML private HBox hBox1;
  @FXML private VBox mapBox, infoVBox;

  @FXML private Group imageGroup;

  @FXML private ImageView imageView;

  @FXML private GesturePane pane;
  @FXML private JFXCheckBox availableCheck, showLocCheck, showEquipCheck, showRequestCheck;
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

  private LocationDAO locDAO = LocationDAO.getDAO();
  private EquipmentUnitDAO equipDAO = EquipmentUnitDAO.getDAO();
  private GesturePane gesturePane;
  @FXML private StackPane parentPage;
  @FXML private AnchorPane anchorRoot;

  /**
   * Returns to the main menu of the JavaFX App
   *
   * @throws IOException
   */
  @FXML
  private void ToDashboard() throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass()
                    .getResource(
                        "/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditorDashboard.fxml")));
    Scene scene = pane.getScene();
    root.translateXProperty().set(-scene.getHeight());
    parentPage.getChildren().add(root);
    Timeline timeline = new Timeline();
    KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
    KeyFrame kf = new KeyFrame(Duration.seconds(.75), kv);

    timeline.getKeyFrames().add(kf);
    timeline.setOnFinished(
        event -> {
          parentPage.getChildren().remove(anchorRoot);
        });
    timeline.play();
  }

  @FXML
  private void ToLocationTable() throws IOException {
    FXMLLoader fxmlLoader =
        new FXMLLoader(
            getClass()
                .getResource(
                    "/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditorLocationTableOverlay.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.DECORATED);
    stage.setTitle("Location Table");
    stage.setScene(new Scene(root1));
    stage.show();
  }

  @FXML
  private void ToEquipmentTable() throws IOException {
    FXMLLoader fxmlLoader =
        new FXMLLoader(
            getClass()
                .getResource(
                    "/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditorEquipmentTableOverlay.fxml"));
    Parent root1 = (Parent) fxmlLoader.load();
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initStyle(StageStyle.DECORATED);
    stage.setTitle("Equipment Table");
    stage.setScene(new Scene(root1));
    stage.show();
  }

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

  /** Loads the fourth floor map */
  @FXML
  public void fourthClick() {
    loadLocation("4");
  }

  /** Loads the fifth floor map */
  @FXML
  public void fifthClick() {
    loadLocation("5");
  }

  /**
   * Returns an observable list of location objects. This list can then be used to easily fill a
   * table with the object data.
   *
   * @return ObservableList of all locations in the system
   */
  private ObservableList<Location> locationListFill() {
    ObservableList<Location> tableList = FXCollections.observableArrayList();
    List<Location> locationList = locDAO.getAllRecords();
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
    List<EquipmentUnit> equipment = equipDAO.getAllRecords();
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
    submitEquipmentButton.setDisable(true);
    submitLocationButton.setDisable(true);
    deleteLocationButton.setDisable(true);
    List<Location> allLocations = locationListFill();
    for (int i = 0; i < allLocations.size(); i++) {
      equipLocationChoice.getItems().add(allLocations.get(i).getNodeID());
    }
    drawCirclesSetEquipmentList(floor);
    drawCirclesSetLocationList(floor);
  }

  /**
   * Draws red dots for all locations that are on the provided floor
   *
   * @param floor String of the floor number (L1, G, 1, etc.)
   */
  private void drawCirclesSetLocationList(String floor) {
    List<Location> locationList = locDAO.getAllRecords();
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
        this.drawRequests(locationList.get(i).getRequestsAtLocation());
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
    List<EquipmentUnit> equipment = equipDAO.getAllRecords();
    for (int i = 0; i < equipment.size(); i++) {
      if (equipment.get(i).getCurrLocation().getFloor().equals(floor)) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(24);
        rectangle.setHeight(24);
        rectangle.setUserData(equipment.get(i));
        rectangle.setX(equipment.get(i).getCurrLocation().getxCoord() - (rectangle.getWidth() / 2));
        rectangle.setY(
            equipment.get(i).getCurrLocation().getyCoord() - (rectangle.getHeight() / 2));
        rectangle.setStroke(Paint.valueOf("BLACK"));
        rectangle.setFill(
            new ImagePattern(
                new Image(
                    "/edu/wpi/cs3733/D22/teamX/assets/" + equipment.get(i).getType() + ".png")));
        ;
        rectangle.setVisible(showEquipCheck.isSelected());
        rectangle.setOnMouseReleased(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {
                EquipmentUnit e = (EquipmentUnit) rectangle.getUserData();
                equipmentChoice.setValue(e.getUnitID());
              }
            });
        imageGroup.getChildren().add(rectangle);
        equipmentChoice.getItems().add(equipment.get(i).getUnitID());
      }
    }
  }

  private void drawRequests(List<ServiceRequest> requests) {
    for (ServiceRequest s : requests) {
      Rectangle rect = new Rectangle();
      rect.setUserData(s);
      rect.setVisible(showRequestCheck.isSelected());
      rect.setWidth(25);
      rect.setHeight(25);
      rect.setFill(
          new ImagePattern(
              new Image(
                  "/edu/wpi/cs3733/D22/teamX/assets/" + s.getClass().getSimpleName() + ".png")));
      rect.setX(s.getDestination().getxCoord() - (rect.getWidth() / 2));
      rect.setY(s.getDestination().getyCoord() - (rect.getHeight() / 2));
      imageGroup.getChildren().add(rect);
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
        else if (node.getUserData() instanceof ServiceRequest) {
          node.setVisible(showRequestCheck.isSelected());
        }
      }
    }
  }

  /** Deletes selected node id in the dropdown */
  public void deleteLocation() {
    String locationToDelete = locationChoice.getValue(); // Node ID
    String floor = locDAO.getRecord(locationToDelete).getFloor();
    locDAO.deleteRecord(locDAO.getRecord(locationToDelete));
    loadLocation(floor);
  }

  /** Deletes selected node id in the dropdown */
  public void deleteEquipment() {
    String equipmentToDelete = equipmentChoice.getValue();
    String floor = equipDAO.getRecord(equipmentToDelete).getCurrLocation().getFloor();
    equipDAO.deleteRecord(equipDAO.getRecord(equipmentToDelete));
    loadLocation(floor);
  }

  /** Fills text boxes with equipment data when a piece of equipment is chosen in the dropdown. */
  @FXML
  public void equipmentSelected() {
    try {
      EquipmentUnit equipment = equipDAO.getRecord(equipmentChoice.getValue());
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

  private void loadLocationInfo(String nodeID) {
    Location location = locDAO.getRecord(nodeID);
    infoVBox.getChildren().clear();
    infoVBox.setSpacing(25);
    Label locationInfo = new Label();
    locationInfo.setFont(Font.font(16));
    String info = "Equipment:\n";
    for (int i = 0; i < location.getUnitsAtLocation().size(); i++) {
      info +=
          "- "
              + location.getUnitsAtLocation().get(i).getType()
              + ": "
              + location.getUnitsAtLocation().get(i).getUnitID()
              + "\n";
    }
    locationInfo.setText(info);
    if (location.getUnitsAtLocation().size() != 0) infoVBox.getChildren().add(locationInfo);
    Label requestsAtLoc = new Label();
    requestsAtLoc.setFont(Font.font(16));
    String requests = "Requests:\n";
    for (int i = 0; i < location.getRequestsAtLocation().size(); i++) {
      requests +=
          "- "
              + location.getRequestsAtLocation().get(i).getClass().getSimpleName()
              + ": "
              + location.getRequestsAtLocation().get(i).getRequestID()
              + "\n";
    }
    requestsAtLoc.setText(requests);
    if (location.getRequestsAtLocation().size() != 0) infoVBox.getChildren().add(requestsAtLoc);
  }

  /** Location data populates in their corresponding text fields */
  @FXML
  public void locationSelected() {
    try {
      Location selected = locDAO.getRecord(locationChoice.getValue());
      submitLocationButton.setDisable(false);
      nodeIdText.setText(selected.getNodeID());
      activateDeleteLocationButton();
      xCordText.setText(selected.getX());
      yCordText.setText(selected.getY());
      floorText.setText(selected.getFloor());
      buildingText.setText(selected.getBuilding());
      nodeTypeText.setText(selected.getNodeType());
      longNameText.setText(selected.getLongName());
      shortNameText.setText(selected.getShortName());
      loadLocationInfo(selected.getNodeID());
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

  private void activateSubmitLocationButton() {
    submitLocationButton.setDisable(
        nodeIdText.getText().equals("")
            || (xCordText.getText().equals("") || !xCordText.getText().matches("[0-9]+"))
            || (yCordText.getText().equals("") || !yCordText.getText().matches("[0-9]+"))
            || floorText.getText().equals("")
            || buildingText.getText().equals("")
            || nodeTypeText.getText().equals("")
            || longNameText.getText().equals("")
            || shortNameText.getText().equals(""));
  }

  private void activateDeleteLocationButton() {
    try {
      deleteLocationButton.setDisable(
          locDAO.getRecord(nodeIdText.getText()).getUnitsAtLocation().size() != 0
              || locDAO.getRecord(nodeIdText.getText()).getRequestsAtLocation().size() != 0);
    } catch (Exception e) {
      deleteLocationButton.setDisable(true);
    }
  }

  private void activateSubmitEquipmentButton() {
    submitEquipmentButton.setDisable(
        unitIdText.getText().equals("")
            || typeText.getText().equals("")
            || equipLocationChoice.getValue().equals(""));
  }

  /**
   * Submit new equipment to the database based on the filled in text data or updates equipment with
   * a matching ID.
   */
  @FXML
  public void submitEquipment() {
    List<EquipmentUnit> allEquipment = equipDAO.getAllRecords();
    for (int i = 0; i < allEquipment.size(); i++) {
      if (allEquipment.get(i).getUnitID().equals(unitIdText.getText())) {
        EquipmentUnit newEquip = new EquipmentUnit();
        newEquip.setUnitID(allEquipment.get(i).getUnitID());
        newEquip.setAvailable(availableCheck.isSelected());
        newEquip.setCurrLocation(locDAO.getRecord(equipLocationChoice.getValue()));
        newEquip.setType(typeText.getText());
        equipDAO.updateRecord(newEquip);
        loadLocation(newEquip.getCurrLocation().getFloor());
        loadLocationInfo(newEquip.getCurrLocation().getNodeID());
        return;
      }
    }
    EquipmentUnit newEquipment = new EquipmentUnit();
    newEquipment.setAvailable(availableCheck.isSelected());
    newEquipment.setCurrLocation(locDAO.getRecord(equipLocationChoice.getValue()));
    newEquipment.setType(typeText.getText());
    newEquipment.setUnitID(unitIdText.getText());

    equipDAO.addRecord(newEquipment);
    loadLocation(newEquipment.getCurrLocation().getFloor());
    loadLocationInfo(newEquipment.getCurrLocation().getNodeID());
  }

  /** Submits a new location with the given data or updates the location with the matching id. */
  @FXML
  public void submitLocation() {
    List<Location> allLocations = locDAO.getAllRecords();
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
        locDAO.updateRecord(replaceLoc);
        loadLocation(replaceLoc.getFloor());
        loadLocationInfo(replaceLoc.getNodeID());
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
    locDAO.addRecord(newLocation);
    loadLocation(newLocation.getFloor());
    loadLocationInfo(newLocation.getNodeID());
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
    showRequestCheck.setSelected(true);
    hBox1.setSpacing(90);

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
    showRequestCheck.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            showDots();
          }
        });
    submitEquipmentButton.setDisable(true);
    submitLocationButton.setDisable(true);
    deleteLocationButton.setDisable(true);
    nodeIdText.setOnKeyTyped(
        (KeyEvent e) -> {
          activateSubmitLocationButton();
          activateDeleteLocationButton();
        });
    xCordText.setOnKeyTyped((KeyEvent e) -> activateSubmitLocationButton());
    yCordText.setOnKeyTyped((KeyEvent e) -> activateSubmitLocationButton());
    floorText.setOnKeyTyped((KeyEvent e) -> activateSubmitLocationButton());
    buildingText.setOnKeyTyped((KeyEvent e) -> activateSubmitLocationButton());
    nodeTypeText.setOnKeyTyped((KeyEvent e) -> activateSubmitLocationButton());
    longNameText.setOnKeyTyped((KeyEvent e) -> activateSubmitLocationButton());
    shortNameText.setOnKeyTyped((KeyEvent e) -> activateSubmitLocationButton());

    unitIdText.setOnKeyTyped((KeyEvent e) -> activateSubmitEquipmentButton());
    typeText.setOnKeyTyped((KeyEvent e) -> activateSubmitEquipmentButton());
    equipLocationChoice.setOnAction((ActionEvent a) -> activateSubmitEquipmentButton());

    loadLocation("1");
  }

  public void autofillUnitIDBox() {
    unitIdText.setText(EquipmentUnitDAO.getDAO().makeID());
  }
}
