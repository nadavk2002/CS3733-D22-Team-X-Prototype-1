package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ReqLangFXMLController implements Initializable {
  @FXML private Button mainMenu;
  @FXML private ChoiceBox<String> selectLang, roomNum;
  @FXML private TextField serviceStatus, assignStaff;

  ReqLangController controller = new ReqLangController();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    selectLang.getItems().addAll(new String[] {"English", "Spanish", "French"});
    roomNum.setItems(controller.getLocationNames());
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
    roomNum.setValue("");
    serviceStatus.setText("");
    assignStaff.setText("");
  }

  @FXML
  public void submitRequest() {
    controller.submitRequest(roomNum.getValue(), serviceStatus.getText(), "SAMPLE12");
    this.resetFields();
  }
}
