package edu.wpi.cs3733.D22.teamX;

import static org.testfx.api.FxAssert.verifyThat;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class MapControllerTests extends ApplicationTest {

  @Override
  public void start(Stage stage) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testDeleteEquipment() {
    clickOn("#LL2Button");
    verifyThat("#equipmentChoice", (ChoiceBox<String> c) -> c.getItems().size() >= 0);
    clickOn("#equipmentChoice").clickOn("MEUN0003");
    verifyThat("#unitIdText", (TextField t) -> !t.getText().equals(""));
    clickOn("#deleteEquipmentButton");
    clickOn("#equipmentChoice");
    verifyThat("#equipmentChoice", (ChoiceBox<String> c) -> !c.getItems().contains("MEUN0003"));
  }

  @Test
  public void testSubmitEquipment() {
    clickOn("#LL2Button");
    verifyThat("#equipmentChoice", (ChoiceBox<String> c) -> c.getItems().size() >= 0);
    verifyThat("#equipmentChoice", (ChoiceBox<String> c) -> !c.getItems().contains("MEUN6969"));
    clickOn("#equipmentChoice").clickOn("MEUN0003");
    verifyThat("#unitIdText", (TextField t) -> !t.getText().equals(""));
    clickOn("#unitIdText");
    write("MEUN6969");
    clickOn("#submitEquipmentButton");
    verifyThat("#equipmentChoice", (ChoiceBox<String> c) -> c.getItems().contains("MEUN6969"));
  }

  @Test
  public void testSubmitLocation() {
    clickOn("#TwoButton");
    clickOn("#locationChoice");
    verifyThat("#locationChoice", (ChoiceBox<String> c) -> c.getItems().size() >= 0);
    verifyThat("#locationChoice", (ChoiceBox<String> c) -> !c.getItems().contains("HBATH69696"));
    clickOn("HBATH00103");
    clickOn("nodeIdText");
    write("HBATH69696");
    clickOn("#submitLocationButton");
    verifyThat("#locationChoice", (ChoiceBox<String> c) -> c.getItems().contains("HBATH69696"));
  }

  @Test
  public void testDeleteLocation() {
    clickOn("#TwoButton");
    clickOn("#locationChoice");
    verifyThat("#locationChoice", (ChoiceBox<String> c) -> c.getItems().size() >= 0);
    clickOn("HBATH00103");
    clickOn("#deleteLocationButton");
    verifyThat("#locationChoice", (ChoiceBox<String> c) -> !c.getItems().contains("HBATH00103"));
  }
}
