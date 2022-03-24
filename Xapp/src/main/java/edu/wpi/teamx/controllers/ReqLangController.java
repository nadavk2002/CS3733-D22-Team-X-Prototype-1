package edu.wpi.teamx.controllers;

import edu.wpi.teamx.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class ReqLangController implements Initializable {
  @FXML private Button mainMenu;
  @FXML private ChoiceBox<String> selectLang;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    selectLang.getItems().addAll(new String[] {"English", "Spanish", "French"});
  }

  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException
   */
  @FXML
  public void mainMenu() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamx/views/app.fxml"));
    App.getPrimaryStage().getScene().setRoot(root);
  }
}
