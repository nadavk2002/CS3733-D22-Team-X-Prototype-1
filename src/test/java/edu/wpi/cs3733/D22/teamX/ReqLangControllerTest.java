package edu.wpi.cs3733.D22.teamX;

import static org.testfx.api.FxAssert.verifyThat;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class ReqLangControllerTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ReqLang.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testRoomNumDropdown() {
    // checks if its populated
    clickOn("#roomNum").clickOn("Gift Shop");
    verifyThat("#roomNum", (ChoiceBox<String> c) -> c.getValue().equals("Gift Shop"));
  }

  @Test
  public void testAssignStaffDropdown() {
    // checks if its populated
    clickOn("#assignStaff").clickOn("Staff1");
    verifyThat("#assignStaff", (ChoiceBox<String> c) -> c.getValue().equals("Staff1"));
  }

  @Test
  public void testResetButton() {
    clickOn("#roomNum").clickOn("Gift Shop");
    clickOn("#selectLang").clickOn("English");
    clickOn("#serviceStatus").clickOn("PROC");
    clickOn("#assignStaff").clickOn("Staff1");
    verifyThat("#submitButton", (Button c) -> !c.isDisabled());

    clickOn("#resetFields");
    verifyThat("#assignStaff", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#serviceStatus", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#selectLang", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#roomNum", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#submitButton", (Button c) -> c.isDisabled());
  }
}
