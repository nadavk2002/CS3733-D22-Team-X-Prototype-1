package edu.wpi.cs3733.D22.teamX;

import edu.wpi.cs3733.D22.teamX.controllers.BasicLayoutController;
import edu.wpi.cs3733.D22.teamX.controllers.InvisibleMusicPlayerController;
import edu.wpi.cs3733.D22.teamX.controllers.LoginScreenController;
import edu.wpi.cs3733.D22.teamX.controllers.PreferencePageController;
import edu.wpi.cs3733.D22.teamX.entity.UserPreferenceDAO;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  private static Stage mainMenu;
  private static Stage loginScreen;
  private static int indexOfSceneReplacement;

  public static Stage getPrimaryStage() {
    return loginScreen;
  }

  /**
   * Switches the scene below the blue menu bar. Takes the new scene as a Pane.
   *
   * @param scene Scene to place below menu bar
   */
  public static void switchScene(Pane scene) {
    scene.setLayoutX(300);
    Parent root = mainMenu.getScene().getRoot();
    List<Node> children = ((Pane) root).getChildren();
    children.set(indexOfSceneReplacement, scene);
    scene.setPrefWidth(mainMenu.getWidth() - 300);
    scene.setPrefHeight(mainMenu.getHeight());
    mainMenu
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              scene.setPrefWidth(mainMenu.getWidth() - 300);
            });
    mainMenu
        .heightProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              scene.setPrefHeight(mainMenu.getHeight());
            });
  }

  public static void switchRoot() throws IOException {
    // Establish volume settings
    PreferencePageController.muteMusicToggleOn =
        UserPreferenceDAO.getDAO().getRecord(LoginScreenController.currentUsername).getMuteMusic();
    PreferencePageController.muteSoundsToggleOn =
        UserPreferenceDAO.getDAO().getRecord(LoginScreenController.currentUsername).getMuteSounds();
    InvisibleMusicPlayerController.mediaPlayer.setVolume(
        UserPreferenceDAO.getDAO().getRecord(LoginScreenController.currentUsername).getVolume());
    PreferencePageController.menuButtonPressSoundPlayer.setMute(
        UserPreferenceDAO.getDAO()
            .getRecord(LoginScreenController.currentUsername)
            .getMuteSounds());
    BasicLayoutController.buttonPressSoundPlayer.setMute(
        UserPreferenceDAO.getDAO()
            .getRecord(LoginScreenController.currentUsername)
            .getMuteSounds());

    Parent root = FXMLLoader.load(App.class.getResource("views/BasicLayout.fxml"));
    Scene scene = new Scene(root);
    Pane insertPage = (Pane) scene.lookup("#appContent");
    String css =
        App.class
            .getResource("/edu/wpi/cs3733/D22/teamX/stylesheets/application.css")
            .toExternalForm();
    scene.getStylesheets().add(css);
    Pane MainMenu = FXMLLoader.load(App.class.getResource("views/app.fxml"));
    MainMenu.setLayoutX(300);
    List<Node> children = ((Pane) root).getChildren();
    indexOfSceneReplacement = children.indexOf(insertPage);
    children.set(indexOfSceneReplacement, MainMenu);
    MainMenu.setPrefWidth(mainMenu.getWidth() - 300);
    MainMenu.setPrefHeight(mainMenu.getHeight());
    mainMenu
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              MainMenu.setPrefWidth(mainMenu.getWidth() - 300);
            });
    mainMenu
        .heightProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              MainMenu.setPrefHeight(mainMenu.getHeight());
            });
    mainMenu.setScene(scene);
    mainMenu.setFullScreen(true);
  }

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    /*App.mainMenu = primaryStage;
    Parent root = FXMLLoader.load(getClass().getResource("views/BasicLayout.fxml"));
    Scene scene = new Scene(root);
    Pane insertPage = (Pane) scene.lookup("#appContent");*/
    App.mainMenu = primaryStage;
    Parent root = FXMLLoader.load(getClass().getResource("views/LoginScreen.fxml"));
    Scene scene = new Scene(root);
    String css =
        this.getClass()
            .getResource("/edu/wpi/cs3733/D22/teamX/stylesheets/application.css")
            .toExternalForm();
    scene.getStylesheets().add(css);
    primaryStage.setScene(scene);
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreen(true);
    primaryStage.show();
    root.requestFocus();
  }

  public static void startScreen() throws IOException {
    Parent root = FXMLLoader.load(App.class.getResource("views/LoginScreen.fxml"));
    Scene scene = new Scene(root);
    String css =
        App.class
            .getResource("/edu/wpi/cs3733/D22/teamX/stylesheets/application.css")
            .toExternalForm();
    scene.getStylesheets().add(css);
    mainMenu.setScene(scene);
    mainMenu.setFullScreen(true);
    mainMenu.show();
    root.requestFocus();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }

  public static void changeColorStyle(String[] hexCodes) {
    for (int i = 0; i < hexCodes.length; i++) {
      String color = ("-fx-color-") + (i + 1) + (": #") + (hexCodes[i]) + (";");
      mainMenu.getScene().getRoot().setStyle(color);
    }
  }

  public static void applyColorChanges() {
    String css =
        App.class
            .getResource("/edu/wpi/cs3733/D22/teamX/stylesheets/application.css")
            .toExternalForm();
    mainMenu.getScene().getRoot().getStylesheets().add(css);
  }
}
