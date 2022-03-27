package edu.wpi.teamx.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamx.App;
import java.io.IOException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class BasicLayoutController {
  @FXML private JFXComboBox<String> ChoosePage;
  private HashMap<String, String> pages;

  @FXML
  public void initialize() {
    pages = new HashMap<String, String>();
    pages.put("Main Menu", "app.fxml");
    pages.put("Equipment Delivery", "equipmentDelivery.fxml");
    pages.put("Lab Request", "LabRequest.fxml");
    pages.put("Meal Request", "mealRequest.fxml");
    pages.put("Medicine Delivery", "Medicine_Delivery.fxml");
    pages.put("Request Transport", "ReqInTransport.fxml");
    pages.put("Request Language Interpreter", "ReqLang.fxml");
    pages.put("Request Laundry Services", "ReqLaundry.fxml");
    ChoosePage.setItems(
        FXCollections.observableArrayList(
            "Main Menu",
            "Equipment Delivery",
            "Lab Request",
            "Meal Request",
            "Medicine Delivery",
            "Request Transport",
            "Request Transport",
            "Request Language Interpreter",
            "Request Laundry Services"));
    ChoosePage.setValue("Main Menu");
  }

  @FXML
  public void SwitchPage() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/teamx/views/" + pages.get(ChoosePage.getValue()))));
  }
}
