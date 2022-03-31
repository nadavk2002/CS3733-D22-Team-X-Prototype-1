package edu.wpi.cs3733.D22.teamX;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class MedicalEquipDeliveryControllerTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/equipmentDelivery.fxml")));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  public void testAmountTextField() {
    clickOn("#amountField");
    write("h");
    verifyThat("#amountField", hasText("h"));
  }

  @Test
  public void testResetButton() {
    clickOn("#resetFields");
    verifyThat("#amountField", hasText(""));
  }
}
