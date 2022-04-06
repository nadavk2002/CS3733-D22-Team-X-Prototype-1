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

public class MedicineDeliveryControllerTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/Medicine_Delivery.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
  //
  @Test
  public void testPatientNameDropdown() {
    clickOn("#patientName").clickOn("Patient 1");
    verifyThat("#patientName", (ChoiceBox<String> c) -> c.getValue().equals("Patient 1"));
  }

  @Test
  public void testRXNumTextField() {
    clickOn("#rxNum");
    write("h");
    verifyThat("#rxNum", hasText("h"));
  }

  @Test
  public void testRoomNumDropdown() {
    // checks if its populated
    clickOn("#roomNum").clickOn("CIM");
    verifyThat("#roomNum", (ChoiceBox<String> c) -> c.getValue().equals("CIM"));
  }

  @Test
  public void testAssignStaffDropdown() {
    clickOn("#assignStaff").clickOn("Staff 1");
    verifyThat("#assignStaff", (ChoiceBox<String> c) -> c.getValue().equals("Staff 1"));
  }

  @Test
  public void testServiceStatusDropdown() {
    clickOn("#serviceStatus").clickOn("PROC");
    verifyThat("#serviceStatus", (ChoiceBox<String> c) -> c.getValue().equals("PROC"));
  }

  @Test
  public void testResetButton() {
    clickOn("#patientName").clickOn("Patient 1");
    clickOn("#roomNum").clickOn("CIM");
    clickOn("#serviceStatus").clickOn("PROC");
    clickOn("#assignStaff").clickOn("Staff 1");
    clickOn("#rxNum");
    write("h");
    verifyThat("#submitRequest", (Button c) -> c.isDisabled());

    clickOn("#resetFields");
    verifyThat("#patientName", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#assignStaff", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#serviceStatus", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#rxNum", hasText(""));
    verifyThat("#roomNum", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#submitRequest", (Button c) -> c.isDisabled());
  }
}
