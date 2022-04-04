package edu.wpi.cs3733.D22.teamX.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
  public JFXTextField directoryDisplayer;
  public AnchorPane anchorCSVSaver;
  public JFXTextField sayExiting;

  //    DirectoryChooser csvSaverDC = new DirectoryChooser();
  //    File csvSaverDir = csvSaverDC.showDialog(anchorCSVSaver.getScene().getWindow());

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // System.out.println("Hello");
  }

  public void getDirectoryForSaving(ActionEvent actionEvent) throws InterruptedException {
    DirectoryChooser csvSaverDC = new DirectoryChooser();
    File csvSaverDir = csvSaverDC.showDialog(anchorCSVSaver.getScene().getWindow());
    if (csvSaverDir != null) {
      directoryDisplayer.setText(csvSaverDir.getAbsolutePath());
    } else {
      csvSaverDir = new File("");
    }
    sayExiting.setVisible(true);
    Platform.exit();
  }
}
