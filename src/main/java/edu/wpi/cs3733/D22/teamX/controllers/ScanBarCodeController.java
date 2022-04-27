package edu.wpi.cs3733.D22.teamX.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class ScanBarCodeController implements Initializable {
  @FXML private BorderPane pane;
  private final char ENTER = 0x000d;
  private String scannedID = "";

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    pane.setOnKeyTyped(
        event -> {
          if (event.getCharacter().charAt(0) != ENTER) scannedID += event.getText();
        });
  }
}
