package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class CovidPageController implements Initializable {
  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  public void switchTesting() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CovidTestingPage.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchVaccine() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CovidVaccinePage.fxml")));
    CSVFileSaverController.loaded = false;
  }

  @FXML
  public void switchInfo() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CovidInfoPage.fxml")));
    CSVFileSaverController.loaded = false;
  }
}
