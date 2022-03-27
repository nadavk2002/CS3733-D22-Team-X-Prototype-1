package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class ReqInTransportController implements Initializable {
  @FXML private Button AppButton;
  @FXML private ChoiceBox<String> transportLocation;
  @FXML private ChoiceBox<String> addAccommodations;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    transportLocation.getItems().addAll(new String[] {"X-Ray Lab", "ICU", "Lobby", "Morgue"});
    addAccommodations.getItems().addAll(new String[] {"Wheelchair", "Bed", "Ventilation", "Nurse"});
  }

  @FXML
  void AppButton() throws IOException {
    App.switchScene(FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/app.fxml")));
  }
}
