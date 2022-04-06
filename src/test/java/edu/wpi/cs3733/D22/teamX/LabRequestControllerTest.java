package edu.wpi.cs3733.D22.teamX;

import static org.testfx.api.FxAssert.verifyThat;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class LabRequestControllerTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/LabRequest.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testPatientNameDropDown() {
    clickOn("#patientName").clickOn("Patient 1");
    verifyThat("#patientName", (ChoiceBox<String> c) -> c.getValue().equals("Patient 1"));
  }

  @Test
  public void testSelectServiceDropDown() {
    clickOn("#selectLab").clickOn("Blood Work");
    verifyThat("#selectLab", (ChoiceBox<String> c) -> c.getValue().equals("Blood Work"));
  }

  @Test
  public void testAssignStaffDropDown() {
    clickOn("#assigneeDrop").clickOn("Doctor 1");
    verifyThat("#assigneeDrop", (ChoiceBox<String> c) -> c.getValue().equals("Doctor 1"));
  }

  @Test
  public void testServiceStatusDropDown() {
    clickOn("#serviceStatus").clickOn("PROC");
    verifyThat("#serviceStatus", (ChoiceBox<String> c) -> c.getValue().equals("PROC"));
  }

  @Test
  public void testDestinationDropDown() {
    clickOn("#selectDestination").clickOn("CIM");
    verifyThat("#selectDestination", (ChoiceBox<String> c) -> c.getValue().equals("CIM"));
  }

  @Test
  public void testResetButton() {
    clickOn("#resetFields");
    verifyThat("#patientName", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#selectLab", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#assigneeDrop", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#serviceStatus", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#selectDestination", (ChoiceBox<String> c) -> c.getValue().equals(""));
  }
}
