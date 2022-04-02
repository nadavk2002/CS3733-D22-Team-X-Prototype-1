package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.entity.MealServiceRequest;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MealReqController {
  @FXML private ChoiceBox<String> patientNames;
  @FXML private ChoiceBox<String> drinkSel;
  @FXML private ChoiceBox<String> mainSel;
  @FXML private ChoiceBox<String> sideSel;
  @FXML private Label addedToQueue;
  @FXML private VBox queueBox;
  @FXML private TextField assignStaff, serviceStatus;

  @FXML
  void returnHomeButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  void resetFields() {
    patientNames.setValue("");
    drinkSel.setValue("");
    mainSel.setValue("");
    sideSel.setValue("");
    addedToQueue.setText("");
    assignStaff.setText("");
    serviceStatus.setText("");
  }

  @FXML
  public void initialize() {
    addedToQueue.setText("");
    // patient names choice box---------------------------------------
    patientNames.getItems().removeAll(patientNames.getItems());
    patientNames
        .getItems()
        .addAll(
            "Jane Doe", "Olivia Rasibeck", "Dylan Nguyen", "Nadav Konstantine", "Jordan Wecler");
    patientNames.getSelectionModel().select("Choose Patient");
    // drinks choice box---------------------------------------
    drinkSel.getItems().removeAll(drinkSel.getItems());
    drinkSel.getItems().addAll("Water", "Orange Juice", "Apple Juice", "Gatorade");
    drinkSel.getSelectionModel().select("Choose Drink");
    // sides choice box---------------------------------------
    sideSel.getItems().removeAll(sideSel.getItems());
    sideSel.getItems().addAll("Fruit Cup", "Mashed Potatoes", "Mixed Vegetables");
    sideSel.getSelectionModel().select("Choose Side");
    // main course choice box---------------------------------------
    mainSel.getItems().removeAll(mainSel.getItems());
    mainSel.getItems().addAll("Chicken", "Fish", "Vegeterian");
    mainSel.getSelectionModel().select("Choose Main Course");
  }

  @FXML
  void submitButton() {
    // addedToQueue = new Label();

    LocalTime time = LocalTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm:ss");
    if ((!patientNames.getValue().equals("Choose Patient"))
        && (!drinkSel.getValue().equals("Choose Drink"))
        && (!sideSel.getValue().equals("Choose Side"))
        && (!mainSel.getValue().equals("Choose Main Course"))) {
      MealServiceRequest request = new MealServiceRequest();
      request.setRequestID("SAMPLE12");
      request.setDestination(new Location());
      request.setStatus(serviceStatus.getText());

      addedToQueue.setText(
          patientNames.getValue()
              + " ("
              + time.format(format)
              + ")"
              + "\n"
              + drinkSel.getValue()
              + "\n"
              + sideSel.getValue()
              + "\n"
              + mainSel.getValue());
      queueBox.setMaxSize(queueBox.getMaxWidth(), queueBox.getMaxHeight() + 100.0);
      addedToQueue.setMaxSize(addedToQueue.getMaxWidth(), addedToQueue.getMaxHeight() + 100.0);
      this.resetFields();
    } else {
      addedToQueue.setText("ERROR: Please fill all boxes!");
    }
  }
}
