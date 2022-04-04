package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.*;
import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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

  @FXML private Button ToMainMenu;

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
  private void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  public void LL1Click() {
    imageGroup.getChildren().clear();

    Image img =
        new Image(
            getClass().getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/Crop_LL1_Floor.png"));
    ImageView newImage = new ImageView(img);
    imageGroup.getChildren().add(newImage);
    drawCircles("L1");
  }

  @FXML
  public void LL2Click() {
    imageGroup.getChildren().clear();

    Image img =
        new Image(
            getClass().getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/Crop_LL2_Floor.png"));
    ImageView newImage = new ImageView(img);
    imageGroup.getChildren().add(newImage);
    drawCircles("L2");
  }

  @FXML
  public void groundClick() {
    imageGroup.getChildren().clear();

    Image img =
        new Image(
            getClass()
                .getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/Crop_Ground_Floor.png"));
    ImageView newImage = new ImageView(img);
    imageGroup.getChildren().add(newImage);
    drawCircles("G");
  }

  @FXML
  public void firstClick() {
    imageGroup.getChildren().clear();

    Image img =
        new Image(
            getClass()
                .getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/Crop_First_Floor.png"));
    ImageView newImage = new ImageView(img);
    imageGroup.getChildren().add(newImage);
    drawCircles("1");
  }

  @FXML
  public void secondClick() {
    imageGroup.getChildren().clear();

    Image img =
        new Image(
            getClass()
                .getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/Crop_Second_Floor.png"));
    ImageView newImage = new ImageView(img);
    imageGroup.getChildren().add(newImage);
    drawCircles("2");
  }

  @FXML
  public void thirdClick() {
    imageGroup.getChildren().clear();
    Image img =
        new Image(
            getClass()
                .getResourceAsStream("/edu/wpi/cs3733/D22/teamX/assets/Crop_Third_Floor.png"));
    ImageView newImage = new ImageView(img);
    imageGroup.getChildren().add(newImage);
    drawCircles("3");
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

  private void drawCircles(String floor) {
    LocationDAO allLocations = new LocationDAOImpl();
    List<Location> locationList = allLocations.getAllLocations();
    for (int i = 0; i < locationList.size(); i++) {
      if (locationList.get(i).getFloor().equals(floor)) {
        Circle circle = new Circle();
        circle.setRadius(3);
        circle.setCenterX(locationList.get(i).getxCoord());
        circle.setCenterY(locationList.get(i).getyCoord());
        circle.setFill(Paint.valueOf("RED"));
        imageGroup.getChildren().add(circle);
      }
    }
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
    table.setItems(locationListFill());
  }
}
