package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.entity.LangServiceRequest;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ReqLangController implements Initializable {
  @FXML private Button mainMenu;
  @FXML private ChoiceBox<String> selectLang;
  @FXML private TextField roomNum, serviceStatus, assignStaff;

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
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  public void resetFields() {
    selectLang.setValue("");
    roomNum.setText("");
    serviceStatus.setText("");
    assignStaff.setText("");
  }

  @FXML
  public void submitRequest() {
    LangServiceRequest request = new LangServiceRequest();
    request.setRequestID("SAMPLE12");
    request.setDestination(new Location());
    request.setStatus(serviceStatus.getText());
    this.resetFields();
  }
}
