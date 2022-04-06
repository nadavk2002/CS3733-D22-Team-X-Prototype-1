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

public class MealRequestControllerTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/mealRequest.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testPatientNamesDropDown() {
    clickOn("#patientNames").clickOn("Patient 1");
    verifyThat("#patientNames", (ChoiceBox<String> c) -> c.getValue().equals("Patient 1"));
  }

  @Test
  public void testAssignedStaffDropDown() {
    clickOn("#assignStaff").clickOn("Doctor 1");
    verifyThat("#assignStaff", (ChoiceBox<String> c) -> c.getValue().equals("Doctor 1"));
  }

  @Test
  public void testRequestStatusDropDown() {
    clickOn("#serviceStatus").clickOn("PROC");
    verifyThat("#serviceStatus", (ChoiceBox<String> c) -> c.getValue().equals("PROC"));
  }

  @Test
  public void testDrinkSelectionDropDown() {
    clickOn("#drinkSel").clickOn("Water");
    verifyThat("#drinkSel", (ChoiceBox<String> c) -> c.getValue().equals("Water"));
  }

  @Test
  public void testSideSelectionDropDown() {
    clickOn("#sideSel").clickOn("Fruit Cup");
    verifyThat("#sideSel", (ChoiceBox<String> c) -> c.getValue().equals("Fruit Cup"));
  }

  @Test
  public void testMainSelectionDropDown() {
    clickOn("#mainSel").clickOn("Chicken");
    verifyThat("#mainSel", (ChoiceBox<String> c) -> c.getValue().equals("Chicken"));
  }

  @Test
  public void testDestinationDropDown() {
    clickOn("#destinationDrop").clickOn("CIM");
    verifyThat("#destinationDrop", (ChoiceBox<String> c) -> c.getValue().equals("CIM"));
  }

  @Test
  public void testResetButton() {
    clickOn("#resetFields");
    verifyThat("#destinationDrop", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#mainSel", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#sideSel", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#drinkSel", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#serviceStatus", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#assignStaff", (ChoiceBox<String> c) -> c.getValue().equals(""));
    verifyThat("#patientNames", (ChoiceBox<String> c) -> c.getValue().equals(""));
  }
}
