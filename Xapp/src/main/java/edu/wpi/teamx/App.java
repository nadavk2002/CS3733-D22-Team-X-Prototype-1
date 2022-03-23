package edu.wpi.teamx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  private static Stage primaryStage;

  public static Stage getPrimaryStage() {
    return primaryStage;
  }

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    App.primaryStage = primaryStage;
    Parent root = FXMLLoader.load(getClass().getResource("views/app.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
