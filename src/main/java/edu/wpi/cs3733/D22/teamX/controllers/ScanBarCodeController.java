package edu.wpi.cs3733.D22.teamX.controllers;

import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnit;
import edu.wpi.cs3733.D22.teamX.entity.EquipmentUnitDAO;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScanBarCodeController implements Initializable {
  @FXML private BorderPane pane;
  @FXML private Label scanLabel;
  private final char ENTER = 0x000d;
  private String scannedID = "";
  private long lastKey = 0;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    scanLabel.setFocusTraversable(true);
    pane.setOnKeyTyped(
        event -> {
          long currentEpoch = Instant.now().toEpochMilli();
          if (currentEpoch - lastKey > 25) {
            scannedID = "";
          }
          lastKey = currentEpoch;
          if (event.getCharacter().charAt(0) != ENTER) scannedID += event.getCharacter();
          else {
            try {
              FXMLLoader fxmlLoader =
                  new FXMLLoader(
                      getClass()
                          .getResource("/edu/wpi/cs3733/D22/teamX/views/EquipmentUnitEditor.fxml"));
              Stage popup = (Stage) pane.getScene().getWindow();
              EquipmentUnit newEquipment = EquipmentUnitDAO.getDAO().getRecord(scannedID);
              popup.setTitle(newEquipment.getUnitID());
              EquipmentUnitEditorController eq = new EquipmentUnitEditorController(newEquipment);
              fxmlLoader.setController(eq);
              Pane editEquipPane = null;
              try {
                editEquipPane = fxmlLoader.load();
              } catch (IOException e) {
                e.printStackTrace();
              }
              Scene scene = new Scene(editEquipPane);
              popup.setScene(scene);
              BasicLayoutController.buttonPressSoundPlayer.stop();
              BasicLayoutController.buttonPressSoundPlayer.play();
              popup.show();
            } catch (Exception e) {
              scanLabel.setText("Error reading, please try again");
            }
          }
        });
  }
}
