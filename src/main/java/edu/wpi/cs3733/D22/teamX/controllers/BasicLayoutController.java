package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Xdb;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
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
    pages.put("Request Janitorial Services", "JanitorialRequest.fxml");
    pages.put("Request Gift Delivery", "GiftDelivery.fxml");
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
            "Request Janitorial Services",
            "Request Gift Delivery",
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
      CSVFileSaverController.loaded = false;
    }
  }

  @FXML
  void ExitApplication() throws IOException, loadSaveFromCSVException {
    if (CSVFileSaverController.loaded) {
      Platform.exit();
      if (!Xdb.saveLocationDataToCSV("")
          || !Xdb.saveMedEqDataToCSV("")
          || !Xdb.saveLabServiceReqDataToCSV("")) {
        throw new loadSaveFromCSVException("Error when writing to CSV file.");
      }
    } else {
      App.switchScene(
          FXMLLoader.load(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CSVFileSaver.fxml")));
      CSVFileSaverController.loaded = true;
    }
  }
}
