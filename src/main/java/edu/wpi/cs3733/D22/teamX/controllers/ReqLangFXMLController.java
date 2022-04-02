package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javax.swing.*;

public class ReqLangFXMLController implements Initializable {
  @FXML private Button mainMenu, submitButton;
  @FXML private ChoiceBox<String> selectLang, roomNum, serviceStatus;
  @FXML private TextField assignStaff;

  ReqLangController controller = new ReqLangController();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    resetFields();
    submitButton.setDisable(true);
    selectLang.getItems().addAll(new String[] {"English", "Spanish", "French"});
    serviceStatus.getItems().addAll("", "PROC", "DONE");
    roomNum.setItems(controller.getLocationNames());
    selectLang.setOnAction((ActionEvent event) -> enableSubmitButton());
    roomNum.setOnAction((ActionEvent event) -> enableSubmitButton());
  }

  public void enableSubmitButton() {
    submitButton.setDisable(roomNum.getValue().equals("") || selectLang.getValue().equals(""));
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
    serviceStatus.setValue("");
    assignStaff.setText("");
  }

  @FXML
  public void submitRequest() {
    controller.submitRequest(
        roomNum.getSelectionModel().getSelectedIndex(),
        serviceStatus.getValue(),
        selectLang.getValue());
    this.resetFields();
  }
}
