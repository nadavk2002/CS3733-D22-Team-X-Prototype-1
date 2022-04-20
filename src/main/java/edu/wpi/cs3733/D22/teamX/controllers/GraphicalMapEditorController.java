package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.cs3733.D22.teamX.*;
import edu.wpi.cs3733.D22.teamX.entity.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
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
import javafx.scene.input.*;
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

  private HashMap<String, Image> mapImages;
  private String floor;

  @FXML private JFXButton autofiller;
  @FXML private Button ToMainMenu, submitLocationButton, submitEquipmentButton;
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
  private EquipmentTypeDAO equipmentTypeDAO = EquipmentTypeDAO.getDAO();
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
    root.translateXProperty().set(-scene.getWidth());
    parentPage.getChildren().add(root);
    Timeline timeline = new Timeline();
    KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_BOTH);
    KeyFrame kf = new KeyFrame(Duration.seconds(.5), kv);

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
    drawCirclesSetLocationList(floor);
  }

  private ContextMenu locRightClickMenu(Rectangle rect) {
    ContextMenu menu = new ContextMenu();
    MenuItem deleteButton = new MenuItem("Delete Location");
    deleteButton.setDisable(
        ((Location) rect.getUserData()).getUnitsAtLocation().size() != 0
            || ((Location) rect.getUserData()).getRequestsAtLocation().size() != 0);
    deleteButton.setOnAction(
        delEvent -> {
          locationChoice.getItems().remove(((Location) rect.getUserData()).getNodeID());
          equipLocationChoice.getItems().remove(((Location) rect.getUserData()).getNodeID());
          imageGroup.getChildren().remove(menu.getOwnerNode());
          locDAO.deleteRecord((Location) rect.getUserData());
        });
    menu.getItems().add(deleteButton);
    MenuItem editButton = new MenuItem("Edit Location");
    editButton.setOnAction(
        event -> {
          FXMLLoader fxmlLoader =
              new FXMLLoader(
                  getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/editLocationMenu.fxml"));
          Stage popup = new Stage();
          popup.setTitle(((Location) rect.getUserData()).getNodeID());
          popup.setOnHidden(
              close -> {
                loadLocation(((Location) rect.getUserData()).getFloor());
              });
          EditLocationMenuController controller =
              new EditLocationMenuController((Location) rect.getUserData(), popup);
          fxmlLoader.setController(controller);
          AnchorPane pane = null;
          try {
            pane = fxmlLoader.load();
          } catch (IOException e) {
          }
          Scene scene = new Scene(pane);
          popup.setScene(scene);
          popup.show();
        });
    menu.getItems().add(editButton);
    return menu;
  }

  private ContextMenu equipRightClickMenu(Rectangle rect) {
    ContextMenu menu = new ContextMenu();
    MenuItem deleteButton = new MenuItem("Delete Equipment");
    deleteButton.setOnAction(
        event -> {
          equipmentChoice.getItems().remove(((EquipmentUnit) rect.getUserData()).getUnitID());
          imageGroup.getChildren().remove(rect);
          String locNode = ((EquipmentUnit) rect.getUserData()).getCurrLocation().getNodeID();
          equipDAO.deleteRecord((EquipmentUnit) rect.getUserData());
          loadLocationInfo(locNode);
        });
    menu.getItems().add(deleteButton);
    return menu;
  }

  /**
   * Draws red dots for all locations that are on the provided floor
   *
   * @param floor String of the floor number (L1, G, 1, etc.)
   */
  private void drawCirclesSetLocationList(String floor) {
    List<Location> locationList = locDAO.getAllRecords();
    ImagePattern img =
        new ImagePattern(new Image("/edu/wpi/cs3733/D22/teamX/assets/mapLocationMarker.png"));
    for (int i = 0; i < locationList.size(); i++) {
      equipLocationChoice.getItems().add(locationList.get(i).getNodeID());
      if (locationList.get(i).getFloor().equals(floor)) {
        Rectangle rect = new Rectangle(24, 24);
        rect.setCursor(Cursor.HAND);
        rect.setUserData(locationList.get(i));
        rect.setX(locationList.get(i).getxCoord() - (rect.getWidth() / 2));
        rect.setY(locationList.get(i).getyCoord() - rect.getHeight());
        rect.setFill(img);

        rect.setOnContextMenuRequested(
            event -> {
              ContextMenu menu = locRightClickMenu(rect);
              menu.show(rect, event.getScreenX(), event.getScreenY());
              // event.consume();
            });
        rect.setOnMouseDragged(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {
                if (event.getButton() != MouseButton.PRIMARY) return;
                pane.setGestureEnabled(false);
                imageGroup.setDisable(true);
                rect.setCursor(Cursor.CLOSED_HAND);
                rect.setFill(Paint.valueOf("LIGHTBLUE"));
                if (event.getX() - (rect.getWidth() / 2) > imageView.getX()
                    && event.getX() <= imageView.getX() + imageView.getBoundsInLocal().getWidth())
                  rect.setX(event.getX() - (rect.getWidth() / 2));
                if (event.getY() - (rect.getHeight() / 2) > imageView.getY()
                    && event.getY() <= imageView.getY() + imageView.getBoundsInLocal().getHeight())
                  rect.setY(event.getY() - rect.getHeight());
                // event.consume();
              }
            });
        rect.setOnMouseReleased(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {
                if (event.getButton() != MouseButton.PRIMARY) return;
                pane.setGestureEnabled(true);
                imageGroup.setDisable(false);
                Location l = (Location) rect.getUserData();
                rect.setCursor(Cursor.HAND);
                rect.setFill(img);
                locationChoice.setValue(l.getNodeID());
                int x = (int) (rect.getX() + (rect.getWidth() / 2));
                int y = (int) (rect.getY() + rect.getHeight());
                if (x > 610) x = 610;
                if (x < 0) x = 0;
                if (y < 0) y = 0;
                if (y > 610) y = 610;
                xCordText.setText(String.valueOf(x));
                yCordText.setText(String.valueOf(y));
                // event.consume();
              }
            });
        rect.setVisible(showLocCheck.isSelected());
        this.drawRequests(locationList.get(i).getRequestsAtLocation());
        this.drawEquipment(locationList.get(i).getUnitsAtLocation());
        imageGroup.getChildren().add(rect);
        locationChoice.getItems().add(locationList.get(i).getNodeID());
      }
    }
  }

  /**
   * Draws green dots for all equipment that are on the provided floor
   *
   * @param equipmentList Equipment to place on the map
   */
  private void drawEquipment(List<EquipmentUnit> equipmentList) {
    for (EquipmentUnit e : equipmentList) {
      Rectangle rectangle = new Rectangle(24, 24);
      rectangle.setUserData(e);
      rectangle.setX(e.getCurrLocation().getxCoord() - (rectangle.getWidth() / 2));
      rectangle.setY(e.getCurrLocation().getyCoord() - (rectangle.getHeight() / 2));
      rectangle.setStroke(Paint.valueOf("BLACK"));
      rectangle.setFill(
          new ImagePattern(
              new Image("/edu/wpi/cs3733/D22/teamX/assets/" + e.getType().getModel() + ".png")));
      rectangle.setVisible(showEquipCheck.isSelected());

      rectangle.setOnMouseDragged(
          new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              if (event.getButton() != MouseButton.PRIMARY) return;
              pane.setGestureEnabled(false);
              imageGroup.setDisable(true);
              rectangle.setCursor(Cursor.CLOSED_HAND);
              rectangle.setFill(Paint.valueOf("LIGHTBLUE"));
              if (event.getX() - (rectangle.getWidth() / 2) > imageView.getX()
                  && event.getX() <= imageView.getX() + imageView.getBoundsInLocal().getWidth())
                rectangle.setX(event.getX() - (rectangle.getWidth() / 2));
              if (event.getY() - (rectangle.getHeight() / 2) > imageView.getY()
                  && event.getY() <= imageView.getY() + imageView.getBoundsInLocal().getHeight())
                rectangle.setY(event.getY() - (rectangle.getHeight() / 2));
              // event.consume();
            }
          });

      rectangle.setOnContextMenuRequested(
          event -> {
            ContextMenu menu = equipRightClickMenu(rectangle);
            menu.show(rectangle, event.getScreenX(), event.getScreenY());
            // event.consume();
          });
      rectangle.setFill(
          new ImagePattern(
              new Image("/edu/wpi/cs3733/D22/teamX/assets/" + e.getType().getModel() + ".png")));
      rectangle.setVisible(showEquipCheck.isSelected());
      rectangle.setOnMouseReleased(
          new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              if (event.getButton() != MouseButton.PRIMARY) return;
              pane.setGestureEnabled(true);
              imageGroup.setDisable(false);
              equipmentChoice.setValue(((EquipmentUnit) rectangle.getUserData()).getUnitID());
              EquipmentUnit l = (EquipmentUnit) rectangle.getUserData();
              rectangle.setCursor(Cursor.HAND);
              rectangle.setFill(
                  new ImagePattern(
                      new Image(
                          "/edu/wpi/cs3733/D22/teamX/assets/" + e.getType().getModel() + ".png")));
              Rectangle newLocRect =
                  findClosestLocation((int) rectangle.getX(), (int) rectangle.getY());
              if (newLocRect != null) {
                Location newLoc = (Location) newLocRect.getUserData();
                ((EquipmentUnit) rectangle.getUserData())
                    .getCurrLocation()
                    .removeUnit((EquipmentUnit) rectangle.getUserData());
                newLoc.addUnit((EquipmentUnit) rectangle.getUserData());
                ((EquipmentUnit) rectangle.getUserData()).setCurrLocation(newLoc);
              }
              loadLocation(l.getCurrLocation().getFloor());
            }
          });

      imageGroup.getChildren().add(rectangle);
      equipmentChoice.getItems().add(e.getUnitID());
    }
  }

  private Rectangle findClosestLocation(int x, int y) {
    Rectangle closest = null;
    double distance = Integer.MAX_VALUE;
    for (Node n : imageGroup.getChildren()) {
      if (n instanceof Rectangle && n.getUserData() instanceof Location) {
        Location curLocation = (Location) n.getUserData();
        double curDistance =
            Math.pow((x - curLocation.getxCoord()), 2) + Math.pow((y - curLocation.getyCoord()), 2);
        curDistance = Math.abs(Math.sqrt(curDistance));
        if (curDistance < distance && curDistance < 50) {
          distance = curDistance;
          closest = (Rectangle) n;
        }
      }
    }
    return closest;
  }

  private void drawRequests(List<ServiceRequest> requests) {
    for (ServiceRequest s : requests) {
      Rectangle rect = new Rectangle();
      rect.setUserData(s);
      rect.setVisible(showRequestCheck.isSelected());
      rect.setWidth(25);
      rect.setHeight(25);
      String string = s.getSimpleName();
      rect.setFill(
          new ImagePattern(
              new Image("/edu/wpi/cs3733/D22/teamX/assets/" + s.getSimpleName() + ".png")));
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

  /** Fills text boxes with equipment data when a piece of equipment is chosen in the dropdown. */
  @FXML
  public void equipmentSelected() {
    try {
      EquipmentUnit equipment = equipDAO.getRecord(equipmentChoice.getValue());
      unitIdText.setText(equipment.getUnitID());
      typeText.setText(equipment.getType().getModel());
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
              + location.getUnitsAtLocation().get(i).getType().getModel()
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
    this.floor = location;
    imageGroup.setOnMouseClicked(
        event -> {
          if (event.getButton() != MouseButton.MIDDLE) return;
          Location loc = new Location();
          loc.setxCoord((int) event.getX());
          loc.setyCoord((int) event.getY());
          loc.setFloor(this.floor);
          loc.setNodeID(null);
          FXMLLoader fxmlLoader =
              new FXMLLoader(
                  getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/editLocationMenu.fxml"));
          Stage popup = new Stage();
          popup.setTitle("New Location");
          popup.setOnHidden(
              close -> {
                loadLocation(loc.getFloor());
              });
          EditLocationMenuController controller = new EditLocationMenuController(loc, popup);
          fxmlLoader.setController(controller);
          AnchorPane pane = null;
          try {
            pane = fxmlLoader.load();
          } catch (IOException e) {
          }
          Scene scene = new Scene(pane);
          popup.setScene(scene);
          popup.show();
        });
    imageGroup.getChildren().clear();
    ImageView newImage = new ImageView(mapImages.get(location));
    newImage.setFitHeight(610);
    newImage.setFitWidth(610);
    imageGroup.getChildren().add(newImage);
    newImage.setOnDragDropped(
        event -> {
          System.out.println("ondragdropped");
          Dragboard db = event.getDragboard();
          boolean completed = false;
          if (db.hasString()) {
            EquipmentUnit movedUnit = equipDAO.getRecord(db.getString());
            movedUnit.setCurrLocation((Location) newImage.getUserData());
            completed = true;
          }
          event.setDropCompleted(completed);
          // event.consume();
        });
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
        newEquip.setType(equipmentTypeDAO.getRecord(typeText.getText()));
        equipDAO.updateRecord(newEquip);
        loadLocation(newEquip.getCurrLocation().getFloor());
        loadLocationInfo(newEquip.getCurrLocation().getNodeID());
        return;
      }
    }
    EquipmentUnit newEquipment = new EquipmentUnit();
    newEquipment.setAvailable(availableCheck.isSelected());
    newEquipment.setCurrLocation(locDAO.getRecord(equipLocationChoice.getValue()));
    newEquipment.setType(equipmentTypeDAO.getRecord(typeText.getText()));
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
    mapImages = new HashMap<>();
    mapImages.put(
        "L1", new Image(getClass().getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/L1.png")));
    mapImages.put(
        "L2", new Image(getClass().getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/L2.png")));
    mapImages.put(
        "1", new Image(getClass().getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/1.png")));
    mapImages.put(
        "2", new Image(getClass().getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/2.png")));
    mapImages.put(
        "3", new Image(getClass().getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/3.png")));
    mapImages.put(
        "4", new Image(getClass().getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/4.png")));
    mapImages.put(
        "5", new Image(getClass().getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/5.png")));

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
    nodeIdText.setOnKeyTyped(
        (KeyEvent e) -> {
          activateSubmitLocationButton();
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
