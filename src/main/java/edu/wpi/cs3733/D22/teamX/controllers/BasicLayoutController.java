package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

/** This represents the blue bar at the top of the app */
public class BasicLayoutController implements Initializable {
  public Label userName;
  @FXML private JFXComboBox<String> ChoosePage;
  @FXML private Label timeLabel;
  private HashMap<String, String> pages;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // startTime();
    //    pages = new HashMap<String, String>();
    //    pages.put("Main Menu", "app.fxml");
    //    pages.put("Equipment Delivery", "equipmentDelivery.fxml");
    //    pages.put("Lab Request", "LabRequest.fxml");
    //    pages.put("Meal Request", "mealRequest.fxml");
    //    pages.put("Medicine Delivery", "Medicine_Delivery.fxml");
    //    pages.put("Request Transport", "ReqInTransport.fxml");
    //    pages.put("Request Language Interpreter", "ReqLang.fxml");
    //    pages.put("Request Laundry Services", "ReqLaundry.fxml");
    //    pages.put("Request Janitorial Services", "JanitorialRequest.fxml");
    //    pages.put("Request Gift Delivery", "GiftDelivery.fxml");
    //    pages.put("Graphical Map Editor", "GraphicalMapEditor.fxml");
    //    pages.put("Service Request Table", "ServiceRequestTable.fxml");
    initClock();
    userName.setText("Hello, " + LoginScreenController.currentUsername);
    //    ChoosePage.setItems(
    //        FXCollections.observableArrayList(
    //            "Choose a Page",
    //            "Main Menu",
    //            "Equipment Delivery",
    //            "Lab Request",
    //            "Meal Request",
    //            "Medicine Delivery",
    //            "Request Transport",
    //            "Request Language Interpreter",
    //            "Request Laundry Services",
    //            "Request Janitorial Services",
    //            "Request Gift Delivery",
    //            "Graphical Map Editor",
    //            "Service Request Table"));
    //    ChoosePage.setValue("Choose a Page");
  }

  //  @FXML
  //  public void SwitchPage() throws IOException {
  //    String newPage = ChoosePage.getValue();
  //    if (!newPage.equals("Choose a Page")) {
  //      App.switchScene(
  //          FXMLLoader.load(
  //              App.class.getResource("/edu/wpi/cs3733/D22/teamX/views/" + pages.get(newPage))));
  //      ChoosePage.setValue("Choose a Page");
  //      CSVFileSaverController.loaded = false;
  //    }
  //  }

  @FXML
  public void switchServiceRequestTable() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchGraphicalEditor() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchServiceRequestMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchOutstandingService() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass()
                .getResource("/edu/wpi/cs3733/D22/teamX/views/OutstandingServiceRequest.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchMapDashboard() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass()
                .getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditorDashboard.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchLoginScreen() throws IOException {
    App.startScreen();
    //    App.switchScene(
    //        FXMLLoader.load(
    //            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/LoginScreen.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchAPILandingPage() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/APILandingPage.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  void ExitApplication() throws IOException, loadSaveFromCSVException {
    if (CSVFileSaverController.loaded) {
      Platform.exit();
      DatabaseCreator.saveAllCSV("");
    } else {
      App.switchScene(
          FXMLLoader.load(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CSVFileSaver.fxml")));
      CSVFileSaverController.loaded = true;
    }
  }

  private void initClock() {
    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e -> {
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy  h:mm a");
                  timeLabel.setText(LocalDateTime.now().format(formatter));
                }),
            new KeyFrame(Duration.seconds(1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
  }
}