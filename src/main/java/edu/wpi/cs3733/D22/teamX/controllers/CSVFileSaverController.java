package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.Xdb;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

public class CSVFileSaverController implements Initializable {
  public JFXButton browser;
  public AnchorPane anchorCSVSaver;

  //    DirectoryChooser csvSaverDC = new DirectoryChooser();
  //    File csvSaverDir = csvSaverDC.showDialog(anchorCSVSaver.getScene().getWindow());

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // System.out.println("Hello");
  }

  public void getDirectoryForSaving(ActionEvent actionEvent) throws loadSaveFromCSVException {
    DirectoryChooser csvSaverDC = new DirectoryChooser();
    File csvSaverDir = csvSaverDC.showDialog(anchorCSVSaver.getScene().getWindow());
    if (csvSaverDir == null) {
      csvSaverDir = new File("");
    }
    if (!Xdb.saveLocationDataToCSV(csvSaverDir.getPath())
        || !Xdb.saveMedEqDataToCSV(csvSaverDir.getPath())
        || !Xdb.saveLabServiceReqDataToCSV(csvSaverDir.getPath())) {
      throw new loadSaveFromCSVException("Error when writing to CSV file.");
    }
    Platform.exit();
  }
}
