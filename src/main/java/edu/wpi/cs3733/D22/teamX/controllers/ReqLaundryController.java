package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.App;
import edu.wpi.cs3733.D22.teamX.Location;
import edu.wpi.cs3733.D22.teamX.entity.LaundyServiceRequest;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReqLaundryController implements Initializable {
  @FXML private VBox selectionMenuColumn;
  @FXML private VBox labelColumn;
  @FXML private VBox submitColumn;
  @FXML private HBox buttonRow;
  @FXML private Button ToMainMenu;
  @FXML private ChoiceBox<String> selectLaundryType;
  @FXML private TextField roomField, assignStaff, serviceStatus;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    selectionMenuColumn.setSpacing(20);
    labelColumn.setSpacing(28);
    submitColumn.setSpacing(20);
    buttonRow.setSpacing(20);
    selectLaundryType
        .getItems()
        .addAll(new String[] {"Linens", "Gowns", "Bedding", "Scrubs", "Coats"});
  }

  @FXML
  void submitRequest() {
    LaundyServiceRequest request = new LaundyServiceRequest();
    request.setRequestID("SAMPLE12");
    request.setDestination(new Location());
    request.setStatus(serviceStatus.getText());
    this.resetFields();
  }

  /**
   * When "Main Menu" button is pressed, the app.fxml scene is loaded on the window.
   *
   * @throws IOException if main menu not switched to
   */
  @FXML
  void ToMainMenu() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/app.fxml")));
  }

  @FXML
  void resetFields() {
    roomField.setText("");
    selectLaundryType.setValue("");
    assignStaff.setText("");
    serviceStatus.setText("");
  }
}
