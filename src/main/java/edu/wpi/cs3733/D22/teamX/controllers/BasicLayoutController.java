package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import edu.wpi.cs3733.D22.teamX.api.*;
import edu.wpi.cs3733.D22.teamX.api.entity.MealServiceRequest;
import edu.wpi.cs3733.D22.teamX.api.entity.MealServiceRequestDAO;
import edu.wpi.cs3733.D22.teamX.api.exceptions.*;
import edu.wpi.cs3733.D22.teamX.entity.EmployeeDAO;
import edu.wpi.cs3733.D22.teamX.entity.LocationDAO;
import edu.wpi.cs3733.D22.teamX.entity.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import edu.wpi.cs3733.c22.teamD.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/** This represents the blue bar at the top of the app */
public class BasicLayoutController implements Initializable {
  public Label userName;
  @FXML private JFXComboBox<String> ChoosePage;
  @FXML private Label timeLabel;
  private HashMap<String, String> pages;
  private static int mealApiIDIndex = 15;
  private static final Media buttonPressSound =
      new Media(
          App.class
              .getResource("/edu/wpi/cs3733/D22/teamX/sounds/Wii_sound_basic_button_press.mp3")
              .toExternalForm());
  public static final MediaPlayer buttonPressSoundPlayer = new MediaPlayer(buttonPressSound);

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
    try {
      playMusic();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    buttonPressSoundPlayer.setVolume(.50);
    initClock();
    userName.setText("Hello, " + LoginScreenController.currentUsername);
    checkAPIData();
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

  private void playMusic() throws IOException {
    Stage stage = new Stage();
    stage.setOpacity(0);
    Scene scene =
        new Scene(
            FXMLLoader.load(
                getClass()
                    .getResource("/edu/wpi/cs3733/D22/teamX/views/InvisibleMusicPlayer.fxml")));
    stage.setScene(scene);
  }

  @FXML
  public void switchServiceRequestTable() throws IOException {
    playButtonPressSound();
    checkAPIData();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchGraphicalEditor() throws IOException {
    playButtonPressSound();
    checkAPIData();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchServiceRequestMenu() throws IOException {
    playButtonPressSound();
    checkAPIData();
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchOutstandingService() throws IOException {
    playButtonPressSound();
    checkAPIData();
    App.switchScene(
        FXMLLoader.load(
            getClass()
                .getResource("/edu/wpi/cs3733/D22/teamX/views/OutstandingServiceRequest.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchMapDashboard() throws IOException {
    playButtonPressSound();
    checkAPIData();
    App.switchScene(
        FXMLLoader.load(
            getClass()
                .getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditorDashboard.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchLoginScreen() throws IOException {
    playButtonPressSound();
    checkAPIData();
    App.startScreen();
    //    App.switchScene(
    //        FXMLLoader.load(
    //            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/LoginScreen.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchEmployeeViewer() throws IOException {
    playButtonPressSound();
    checkAPIData();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/EmployeeViewer.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchAPILandingPage() throws IOException {
    playButtonPressSound();
    checkAPIData();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/APILandingPage.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  void ExitApplication() throws IOException, loadSaveFromCSVException {
    playButtonPressSound();
    if (CSVFileSaverController.loaded) {
      Platform.exit();
      if (!CSVFileSaverController.isSaved) {
        DatabaseCreator.saveAllCSV("");
      }
    } else {
      checkAPIData();
      App.switchScene(
          FXMLLoader.load(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CSVFileSaver.fxml")));
      CSVFileSaverController.loaded = true;
    }
  }

  @FXML
  public void switchPreferencePage() throws IOException {
    playButtonPressSound();
    checkAPIData();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/PreferencePage.fxml")));
    CSVFileSaverController.loaded = false;
  }

  private void initClock() {
    Timeline clock =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                e -> {
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
                  timeLabel.setText(LocalDateTime.now().format(formatter));
                }),
            new KeyFrame(Duration.seconds(1)));
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
  }

  private void playButtonPressSound() {
    buttonPressSoundPlayer.stop();
    buttonPressSoundPlayer.play();
  }

  private static void checkAPIData() {
    // Add new meal service request data
    List<MealServiceRequest> apiMeals = MealServiceRequestDAO.getDAO().getAllRecords();
    while (mealApiIDIndex < apiMeals.size()) {
      MealServiceRequest apiMeal = apiMeals.get(mealApiIDIndex);
      edu.wpi.cs3733.D22.teamX.entity.MealServiceRequest meal =
          new edu.wpi.cs3733.D22.teamX.entity.MealServiceRequest(
              ServiceRequestDAO.getDAO().makeMealServiceRequestID(),
              LocationDAO.getDAO().getRecord(apiMeal.getDestination().getNodeID()),
              apiMeal.getStatus(),
              EmployeeDAO.getDAO().getRecord(apiMeal.getAssignee().getEmployeeID()),
              apiMeal.getMainCourse(),
              apiMeal.getSide(),
              apiMeal.getDrink(),
              apiMeal.getPatientFor());
      ServiceRequestDAO.getDAO().addRecord(meal);
      mealApiIDIndex++;
    }
  }
}
