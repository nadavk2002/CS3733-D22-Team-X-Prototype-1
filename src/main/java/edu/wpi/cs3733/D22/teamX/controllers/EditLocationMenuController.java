package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.Location;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditLocationMenuController implements Initializable {
  private Location loc;
  private Stage stage;
  private List<String> floors = Arrays.asList("L1", "L2", "1", "2", "3", "4", "5");

  public EditLocationMenuController(Location loc, Stage stage) {
    this.loc = loc;
    this.stage = stage;
  }

  @FXML private TextField buildingText;

  @FXML private TextField floorText;

  @FXML private TextField longNameText;

  @FXML private TextField nodeTypeText;

  @FXML private TextField shortNameText;

  @FXML private TextField xCordText;

  @FXML private TextField yCordText;

  @FXML private Button submitButton;

  @FXML
  void updateLocation(ActionEvent event) {
    loc.setBuilding(buildingText.getText());
    loc.setFloor(floorText.getText());
    loc.setLongName(longNameText.getText());
    loc.setNodeType(nodeTypeText.getText());
    loc.setShortName(shortNameText.getText());
    loc.setxCoord(Integer.parseInt(xCordText.getText()));
    loc.setyCoord(Integer.parseInt(yCordText.getText()));
    if (loc.getNodeID() == null) {
      if (loc.getShortName().length() >= 5)
        loc.setNodeID(
            loc.getShortName().substring(0, 5) + LocationDAO.getDAO().getAllRecords().size() + 1);
      else
        loc.setNodeID(
            loc.getShortName().substring(0, loc.getShortName().length())
                + LocationDAO.getDAO().getAllRecords().size()
                + 1);
    }
    try {
      LocationDAO.getDAO().updateRecord(loc);
    } catch (Exception e) {
      LocationDAO.getDAO().addRecord(loc);
    }

    this.stage.close();
  }

  private void activateSubmit() {
    submitButton.setDisable(
        (xCordText.getText().equals("") || !xCordText.getText().matches("[0-9]+"))
            || (yCordText.getText().equals("") || !yCordText.getText().matches("[0-9]+"))
            || !floors.contains(floorText.getText())
            || buildingText.getText().equals("")
            || nodeTypeText.getText().equals("")
            || longNameText.getText().equals("")
            || shortNameText.getText().equals(""));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    buildingText.setText(loc.getBuilding());
    floorText.setText(loc.getFloor());
    longNameText.setText(loc.getLongName());
    nodeTypeText.setText(loc.getNodeType());
    shortNameText.setText(loc.getShortName());
    xCordText.setText(Integer.toString(loc.getxCoord()));
    yCordText.setText(Integer.toString(loc.getyCoord()));
    buildingText.setOnKeyTyped(event -> activateSubmit());
    floorText.setOnKeyTyped(event -> activateSubmit());
    longNameText.setOnKeyTyped(event -> activateSubmit());
    nodeTypeText.setOnKeyTyped(event -> activateSubmit());
    shortNameText.setOnKeyTyped(event -> activateSubmit());
    xCordText.setOnKeyTyped(event -> activateSubmit());
    yCordText.setOnKeyTyped(event -> activateSubmit());
    activateSubmit();
  }
}
