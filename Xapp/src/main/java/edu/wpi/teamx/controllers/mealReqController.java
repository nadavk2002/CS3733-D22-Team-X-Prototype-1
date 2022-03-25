package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class mealReqController {
  @FXML private ChoiceBox<String> patientNames;
  @FXML private ChoiceBox<String> drinkSel;
  @FXML private ChoiceBox<String> mainSel;
  @FXML private ChoiceBox<String> sideSel;
  @FXML private Label addedToQueue;
  @FXML private VBox queueBox;

  @FXML
  void returnHomeButton() throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/edu/wpi/teamx/views/app.fxml")));
    App.getPrimaryStage().getScene().setRoot(root);
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
    } else {
      addedToQueue.setText("ERROR: Please fill all boxes!");
    }
  }
}
