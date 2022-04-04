package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamX.App;
import java.io.IOException;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/** This represents the blue bar at the top of the app */
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
    pages.put("Graphical Map Editor", "GraphicalMapEditor.fxml");
    ChoosePage.setItems(
        FXCollections.observableArrayList(
            "Choose a Page",
            "Main Menu",
            "Equipment Delivery",
            "Lab Request",
            "Meal Request",
            "Medicine Delivery",
            "Request Transport",
            "Request Language Interpreter",
            "Request Laundry Services",
            "Graphical Map Editor"));
    ChoosePage.setValue("Choose a Page");
  }

  @FXML
  public void SwitchPage() throws IOException {
    String newPage = ChoosePage.getValue();
    if (!newPage.equals("Choose a Page")) {
      App.switchScene(
          FXMLLoader.load(
              App.class.getResource("/edu/wpi/cs3733/D22/teamX/views/" + pages.get(newPage))));
      ChoosePage.setValue("Choose a Page");
    }
  }

  @FXML
  void ExitApplication() throws IOException {
    //    App.switchScene(
    //        FXMLLoader.load(
    //            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CSVFileSaver.fxml")));
    Platform.exit();
  }
}
