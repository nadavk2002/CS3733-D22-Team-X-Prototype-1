package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class EditLocationMenuController implements Initializable {
  private Rectangle rect;
  private Stage stage;

  public EditLocationMenuController(Rectangle rect, Stage stage) {
    this.rect = rect;
    this.stage = stage;
  }

  @FXML private TextField buildingText;

  @FXML private TextField floorText;

  @FXML private TextField longNameText;

  @FXML private TextField nodeTypeText;

  @FXML private TextField shortNameText;

  @FXML private TextField xCordText;

  @FXML private TextField yCordText;

  @FXML
  void updateLocation(ActionEvent event) {
    Location loc = (Location) rect.getUserData();
    loc.setBuilding(buildingText.getText());
    loc.setFloor(floorText.getText());
    loc.setLongName(longNameText.getText());
    loc.setNodeType(nodeTypeText.getText());
    loc.setShortName(shortNameText.getText());
    loc.setxCoord(Integer.parseInt(xCordText.getText()));
    loc.setyCoord(Integer.parseInt(yCordText.getText()));
    LocationDAO.getDAO().updateRecord(loc);
    this.stage.close();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Location loc = (Location) rect.getUserData();
    buildingText.setText(loc.getBuilding());
    floorText.setText(loc.getFloor());
    longNameText.setText(loc.getLongName());
    nodeTypeText.setText(loc.getNodeType());
    shortNameText.setText(loc.getShortName());
    xCordText.setText(Integer.toString(loc.getxCoord()));
    yCordText.setText(Integer.toString(loc.getyCoord()));
  }
}
