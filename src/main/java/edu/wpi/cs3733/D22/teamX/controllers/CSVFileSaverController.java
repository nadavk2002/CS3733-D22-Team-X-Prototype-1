package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.DatabaseCreator;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;

public class CSVFileSaverController implements Initializable {
  public JFXButton browser;
  @FXML public BorderPane csvSaverPane;
  public static boolean loaded = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // System.out.println("Hello");
  }

  public void getDirectoryForSaving(ActionEvent actionEvent)
      throws loadSaveFromCSVException,
          edu.wpi.cs3733.D22.teamX.api.exceptions.loadSaveFromCSVException {
    DirectoryChooser csvSaverDC = new DirectoryChooser();
    File csvSaverDir = csvSaverDC.showDialog(csvSaverPane.getScene().getWindow());
    if (csvSaverDir == null) {
      return;
    }
    String csvSaverDirStr = csvSaverDir.getPath() + "\\";
    DatabaseCreator.saveAllCSV(csvSaverDirStr);
    Platform.exit();
  }

  public void saveToDefault(ActionEvent actionEvent)
      throws loadSaveFromCSVException,
          edu.wpi.cs3733.D22.teamX.api.exceptions.loadSaveFromCSVException {
    DatabaseCreator.saveAllCSV("");
    Platform.exit();
  }

  @FXML
  public void mainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
    loaded = false;
  }
}
