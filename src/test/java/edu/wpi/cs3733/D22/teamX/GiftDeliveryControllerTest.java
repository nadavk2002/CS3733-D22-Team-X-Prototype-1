package edu.wpi.cs3733.D22.teamX;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

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

public class GiftDeliveryControllerTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GiftDelivery.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testGiftTypeDropdown() {
    // checks if its populated
    clickOn("#selectGiftType").clickOn("Toy");
    verifyThat("#selectGiftType", (ChoiceBox<String> c) -> c.getValue().equals("Toy"));
  }

  @Test
  public void testGiftDestinationDropdown() {
    // checks if its populated
    clickOn("#selectGiftDestination").clickOn("CIM");
    verifyThat("#selectGiftDestination", (ChoiceBox<String> c) -> c.getValue().equals("CIM"));
  }

  @Test
  public void testAssignStaffDropdown() {
    // checks if its populated
    clickOn("#selectAssignStaff").clickOn("Staff1");
    verifyThat("#selectAssignStaff", (ChoiceBox<String> c) -> c.getValue().equals("Staff1"));
  }

  @Test
  public void testGiftNoteTextField() {
    clickOn("#giftNoteField");
    write("h");
    verifyThat("#giftNoteField", hasText("h"));
  }

  @Test
  public void testStatusDropdown() {
    // checks if its populated
    clickOn("#selectStatus").clickOn("PROC");
    verifyThat("#selectStatus", (ChoiceBox<String> c) -> c.getValue().equals("PROC"));
  }

  @Test
  public void testResetButton() {
    clickOn("#selectGiftType").clickOn("Toy");
    clickOn("#selectGiftDestination").clickOn("CIM");
    clickOn("#selectAssignStaff").clickOn("Staff1");
    clickOn("#giftNoteField");
    write("h");
    clickOn("#selectStatus").clickOn("PROC");
    verifyThat("#submitButton", (Button c) -> !c.isDisabled());

    clickOn("#resetFields");
    verifyThat("#selectStatus", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#selectGiftType", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#selectGiftDestination", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#selectAssignStaff", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#submitButton", (Button c) -> c.isDisabled());
  }
}
