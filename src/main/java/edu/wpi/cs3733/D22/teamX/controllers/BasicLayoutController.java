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
  private static final Media buttonPressSound =
      new Media(
          App.class
              .getResource("/edu/wpi/cs3733/D22/teamX/sounds/Wii_sound_basic_button_press.mp3")
              .toExternalForm());
  private static final MediaPlayer buttonPressSoundPlayer = new MediaPlayer(buttonPressSound);

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
    try {
      playMusic();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchGraphicalEditor() throws IOException {
    playButtonPressSound();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchServiceRequestMenu() throws IOException {
    playButtonPressSound();
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchOutstandingService() throws IOException {
    playButtonPressSound();
    App.switchScene(
        FXMLLoader.load(
            getClass()
                .getResource("/edu/wpi/cs3733/D22/teamX/views/OutstandingServiceRequest.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchMapDashboard() throws IOException {
    playButtonPressSound();
    App.switchScene(
        FXMLLoader.load(
            getClass()
                .getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditorDashboard.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchLoginScreen() throws IOException {
    playButtonPressSound();
    App.startScreen();
    //    App.switchScene(
    //        FXMLLoader.load(
    //            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/LoginScreen.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchAPILandingPage() throws IOException {
    playButtonPressSound();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/APILandingPage.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  void ExitApplication()
      throws IOException, loadSaveFromCSVException, loadSaveFromCSVException,
          edu.wpi.cs3733.D22.teamX.api.exceptions.loadSaveFromCSVException {
    playButtonPressSound();
    if (CSVFileSaverController.loaded) {
      Platform.exit();
      DatabaseCreator.saveAllCSV("");
      edu.wpi.cs3733.D22.teamX.api.entity.MealServiceRequestDAO.getDAO().saveCSV("");
    } else {
      App.switchScene(
          FXMLLoader.load(
              getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CSVFileSaver.fxml")));
      CSVFileSaverController.loaded = true;
    }
  }

  @FXML
  public void goToEmployeeViewer() throws IOException {
    playButtonPressSound();
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/EmployeeViewer.fxml")));
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
}
