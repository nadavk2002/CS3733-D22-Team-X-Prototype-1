package edu.wpi.cs3733.D22.teamX;

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
  private static int minWidth = 900;
  private static int maxWidth = 1920;
  private static int minHeight = 500;
  private static int maxHeight = 1080;

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
    mainMenu.setMinHeight(minHeight);
    mainMenu.setMaxHeight(maxHeight);
    mainMenu.setMinWidth(minWidth);
    mainMenu.setMaxWidth(maxWidth);
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
    primaryStage.setMinHeight(minHeight);
    primaryStage.setMaxHeight(maxHeight);
    primaryStage.setMinWidth(minWidth);
    primaryStage.setMaxWidth(maxWidth);
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
    mainMenu.setMinHeight(minHeight);
    mainMenu.setMaxHeight(maxHeight);
    mainMenu.setMinWidth(minWidth);
    mainMenu.setMaxWidth(maxWidth);
    mainMenu.show();
    root.requestFocus();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
